package com.github.terralian.fastmaj.yaku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.h1.Bakaze;
import com.github.terralian.fastmaj.yaku.h1.Haitei;
import com.github.terralian.fastmaj.yaku.h1.Haku;
import com.github.terralian.fastmaj.yaku.h1.Hatu;
import com.github.terralian.fastmaj.yaku.h1.Houtei;
import com.github.terralian.fastmaj.yaku.h1.Iipatu;
import com.github.terralian.fastmaj.yaku.h1.Iipeikou;
import com.github.terralian.fastmaj.yaku.h1.Jikaze;
import com.github.terralian.fastmaj.yaku.h1.Pinfu;
import com.github.terralian.fastmaj.yaku.h1.Reach;
import com.github.terralian.fastmaj.yaku.h1.Rinsyan;
import com.github.terralian.fastmaj.yaku.h1.Tanyaotyu;
import com.github.terralian.fastmaj.yaku.h1.Tsumo;
import com.github.terralian.fastmaj.yaku.h1.TyanKan;
import com.github.terralian.fastmaj.yaku.h1.Tyun;
import com.github.terralian.fastmaj.yaku.h13.Daisangen;
import com.github.terralian.fastmaj.yaku.h13.Daisuusii;
import com.github.terralian.fastmaj.yaku.h13.Kokusi;
import com.github.terralian.fastmaj.yaku.h13.Kokusi13;
import com.github.terralian.fastmaj.yaku.h13.Ryuuiisou;
import com.github.terralian.fastmaj.yaku.h13.Suuankou;
import com.github.terralian.fastmaj.yaku.h13.SuuankouTanki;
import com.github.terralian.fastmaj.yaku.h13.Suukanzu;
import com.github.terralian.fastmaj.yaku.h13.Syousuusii;
import com.github.terralian.fastmaj.yaku.h13.Tenho;
import com.github.terralian.fastmaj.yaku.h13.TiiHo;
import com.github.terralian.fastmaj.yaku.h13.Tinroutou;
import com.github.terralian.fastmaj.yaku.h13.Tuuiisou;
import com.github.terralian.fastmaj.yaku.h13.Tyuuren;
import com.github.terralian.fastmaj.yaku.h13.Tyuuren9;
import com.github.terralian.fastmaj.yaku.h2.DoubleReach;
import com.github.terralian.fastmaj.yaku.h2.HonTyanta;
import com.github.terralian.fastmaj.yaku.h2.Honroutou;
import com.github.terralian.fastmaj.yaku.h2.Ittuu;
import com.github.terralian.fastmaj.yaku.h2.Sanankou;
import com.github.terralian.fastmaj.yaku.h2.Sankanzu;
import com.github.terralian.fastmaj.yaku.h2.SansyokuDoujyun;
import com.github.terralian.fastmaj.yaku.h2.SansyokuDoupon;
import com.github.terralian.fastmaj.yaku.h2.Syousangen;
import com.github.terralian.fastmaj.yaku.h2.Tiitoitu;
import com.github.terralian.fastmaj.yaku.h2.Toitoi;
import com.github.terralian.fastmaj.yaku.h3.Honitu;
import com.github.terralian.fastmaj.yaku.h3.JyunTyanta;
import com.github.terralian.fastmaj.yaku.h3.Ryanpeikou;
import com.github.terralian.fastmaj.yaku.h6.Tinizu;
import com.github.terralian.fastmaj.yaku.meta.JihaiYaku;
import com.github.terralian.fastmaj.yaku.meta.KozuYaku;
import com.github.terralian.fastmaj.yaku.meta.MenchanYaku;
import com.github.terralian.fastmaj.yaku.meta.MenzuYaku;
import com.github.terralian.fastmaj.yaku.meta.NonJihaiYaku;
import com.github.terralian.fastmaj.yaku.meta.RequestContextYaku;
import com.github.terralian.fastmaj.yaku.meta.RonYaku;
import com.github.terralian.fastmaj.yaku.meta.ShunzuYaku;
import com.github.terralian.fastmaj.yaku.meta.YakuMeta;

/**
 * @author terra.lian
 * @since 2023-04-17
 */
public class SimpleYakuMatcher implements IYakuMatcher {

    private final Map<String, YakuMeta> nameMetaMap = new HashMap<>();

    /**
     * 初始化役种解析器
     */
    public SimpleYakuMatcher() {
        mapYakuMeta(buildDefaultYakuList());
    }

    @Override
    public List<IYaku> match(ITehai tehai, DivideInfo divideInfo, PlayerGameContext context) {
        Stream<YakuMeta> metaStream = nameMetaMap.values().stream();

        // 循环遍历所有役种，根据役种的Meta信息进行快速匹配
        int jihaiSize = analyseJihaiSize(tehai);
        // 没有字牌时，不匹配字牌役
        if (jihaiSize == 0) {
            metaStream = metaStream.filter(k -> k.getJihaiYaku() == null);
        }
        // 有字牌时，不匹配非字牌役
        // 若是字牌役最小字牌数量需要小于当前字牌数量
        else if (jihaiSize > 0) {
            metaStream = metaStream.filter(k -> k.getNonJihaiYaku() == null) //
                    .filter(k -> k.getJihaiYaku() == null  //
                            || k.getJihaiYaku().minJihaiSize() <= jihaiSize);
        }

        // 有手牌分割时
        if (divideInfo != null) {
            // 若存在顺子，顺子役的最小顺子数需要小于手牌的顺子数
            if (divideInfo.getAllShunzuFirst().size() > 0) {
                metaStream = metaStream.filter(k -> k.getShunzuYaku() == null //
                        || k.getShunzuYaku().minShunzuSize() <= divideInfo.getAllShunzuFirst().size());
            }
            // 若存在对子 对子役的最小对子需要小于手牌对子数
            if (divideInfo.getAllKanKozuFirst().size() > 0) {
                metaStream = metaStream.filter(k -> k.getKozuYaku() == null //
                        || k.getKozuYaku().minKozuSize() <= divideInfo.getAllKanKozuFirst().size());
            }
        }
        // 没有手牌分割，则不匹配顺子役和刻子役
        else {
            metaStream = metaStream.filter(k -> k.getShunzuYaku() == null && k.getKozuYaku() == null);
        }

        // 若没有游戏上下文，不匹配场况役
        if (context == null) {
            metaStream = metaStream.filter(k -> k.getRequestContextYaku() == null);
        }

        List<IYaku> yakus = metaStream
                // 若非门清，则不匹配门清役
                .filter(k -> !(tehai.isNaki() && k.getMenchanYaku() != null))
                // 若非荣和，则不匹配荣和役
                .filter(k -> k.getRonYaku() == null || context == null || context.isRon() == k.getRonYaku().value())
                // 匹配
                .filter(k -> k.match(tehai, divideInfo, context))
                // 收集结果
                .map(YakuMeta::getTarget).collect(Collectors.toList());

        // 若匹配结果中含役满，则过滤非役满部分
        if (yakus.stream().anyMatch(IYaku::isYakuman)) {
            yakus = yakus.stream() //
                    .filter(IYaku::isYakuman) //
                    .collect(Collectors.toList());
        }
        return yakus;
    }

    /**
     * 组装默认的役种集合，包含天凤规则下的所有通常役种
     */
    public List<IYaku> buildDefaultYakuList() {
        List<IYaku> defaultYakus = new ArrayList<>();
        // 1番
        defaultYakus.add(new Bakaze());
        defaultYakus.add(new Haitei());
        defaultYakus.add(new Haku());
        defaultYakus.add(new Hatu());
        defaultYakus.add(new Houtei());
        defaultYakus.add(new Iipatu());
        defaultYakus.add(new Iipeikou());
        defaultYakus.add(new Jikaze());
        defaultYakus.add(new Pinfu());
        defaultYakus.add(new Reach());
        defaultYakus.add(new Rinsyan());
        defaultYakus.add(new Tanyaotyu());
        defaultYakus.add(new Tsumo());
        defaultYakus.add(new TyanKan());
        defaultYakus.add(new Tyun());
        // 2番
        defaultYakus.add(new DoubleReach());
        defaultYakus.add(new Honroutou());
        defaultYakus.add(new HonTyanta());
        defaultYakus.add(new Ittuu());
        defaultYakus.add(new Sanankou());
        defaultYakus.add(new Sankanzu());
        defaultYakus.add(new SansyokuDoujyun());
        defaultYakus.add(new SansyokuDoupon());
        defaultYakus.add(new Syousangen());
        defaultYakus.add(new Tiitoitu());
        defaultYakus.add(new Toitoi());
        // 3番
        defaultYakus.add(new Honitu());
        defaultYakus.add(new JyunTyanta());
        defaultYakus.add(new Ryanpeikou());
        // 6番
        defaultYakus.add(new Tinizu());
        // 役满
        defaultYakus.add(new Daisangen());
        defaultYakus.add(new Daisuusii());
        defaultYakus.add(new Kokusi());
        defaultYakus.add(new Kokusi13());
        defaultYakus.add(new Ryuuiisou());
        defaultYakus.add(new Suuankou());
        defaultYakus.add(new SuuankouTanki());
        defaultYakus.add(new Suukanzu());
        defaultYakus.add(new Syousuusii());
        defaultYakus.add(new Tenho());
        defaultYakus.add(new TiiHo());
        defaultYakus.add(new Tinroutou());
        defaultYakus.add(new Tuuiisou());
        defaultYakus.add(new Tyuuren());
        defaultYakus.add(new Tyuuren9());

        return defaultYakus;
    }

    // ----------------------------------------------------------
    // 役种映射为Map
    // ----------------------------------------------------------

    /**
     * 使用役种的Meta信息包装原始役种信息.
     * <p/>
     * 该方法会读取役种类上的注解信息，并将所需的注解放入Meta中缓存。
     *
     * @param yaku 役种
     */
    public YakuMeta toYakuMeta(IYaku yaku) {
        YakuMeta yakuMeta = new YakuMeta();
        yakuMeta.setTarget(yaku);
        Class<?> clazz = yaku.getClass();
        yakuMeta.setJihaiYaku(clazz.getAnnotation(JihaiYaku.class));
        yakuMeta.setKozuYaku(clazz.getAnnotation(KozuYaku.class));
        yakuMeta.setMenchanYaku(clazz.getAnnotation(MenchanYaku.class));
        yakuMeta.setMenzuYaku(clazz.getAnnotation(MenzuYaku.class));
        yakuMeta.setNonJihaiYaku(clazz.getAnnotation(NonJihaiYaku.class));
        yakuMeta.setRonYaku(clazz.getAnnotation(RonYaku.class));
        yakuMeta.setShunzuYaku(clazz.getAnnotation(ShunzuYaku.class));
        yakuMeta.setRequestContextYaku(clazz.getAnnotation(RequestContextYaku.class));
        return yakuMeta;
    }

    /**
     * 将役种信息解析并根据名称映射为Map，用于后续快速解析
     *
     * @param yakus 役种
     */
    private void mapYakuMeta(List<IYaku> yakus) {
        for (IYaku yaku : yakus) {
            YakuMeta yakuMeta = toYakuMeta(yaku);
            nameMetaMap.put(yakuMeta.getYakuName(), yakuMeta);
        }
    }

    /**
     * 判断字牌的数量
     *
     * @param tehai 手牌
     */
    private int analyseJihaiSize(ITehai tehai) {
        int[] value34 = Encode34.toEncode34(tehai.getAll());

        int jihaiSize = 0;
        // 东到中
        for (int i = Encode34.DONG; i <= Encode34.ZHONG; i++) {
            if (value34[i] == 4) {
                jihaiSize += 3;
            } else if (value34[i] > 0) {
                jihaiSize += value34[i];
            }
        }
        return jihaiSize;
    }

}
