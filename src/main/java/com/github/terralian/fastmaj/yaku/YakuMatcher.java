package com.github.terralian.fastmaj.yaku;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.IHai;
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
import com.github.terralian.fastmaj.yaku.meta.NonJihaiYaku;
import com.github.terralian.fastmaj.yaku.meta.RequestContextYaku;
import com.github.terralian.fastmaj.yaku.meta.RonYaku;
import com.github.terralian.fastmaj.yaku.meta.ShunzuYaku;

/**
 * 简易和了役种匹配器，返回匹配到的所有役种。
 * <p/>
 * 该匹配器支持自定义需匹配的役种，或者根据规则快速替换要匹配的役种集合。
 *
 * @author terra.lian
 * @since 2023-04-17
 */
public class YakuMatcher implements IYakuMatcher {
    /**
     * 无字牌役
     */
    private List<IYaku> nonJihaiYakus;
    /**
     * 含字牌的役，字牌大于1枚以上判定
     * <p/>
     * 如混老头，混一色等
     */
    private List<IYaku> jihaiYakus;
    /**
     * 刻子役，含3个对子以上的
     */
    private List<IYaku> kozuYakus;
    /**
     * 顺子役，含3个顺子以上的
     */
    private List<IYaku> shunzuYakus;
    /**
     * 门清役种
     */
    private List<IYaku> menchanYakus;
    /**
     * 荣和役
     */
    private List<IYaku> ronYakus;
    /**
     * 需要上下文才能判定的役种
     */
    private List<IYaku> requestContextYakus;
    /**
     * 无剪枝的通常役，所有情况下都会进行匹配。
     * <p/>
     * 这里的役种越少性能越好
     */
    private List<IYaku> normalYakus;

    /**
     * 初始化役种解析器
     */
    public YakuMatcher() {
        mapYakuMeta(buildDefaultYakuList());
    }

    @Override
    public List<IYaku> match(ITehai tehai, DivideInfo divideInfo, PlayerGameContext context) {
        List<IYaku> yakus = new ArrayList<>();
        if (tehai.getAll().stream().anyMatch(IHai::isJiHai)) {
            matchCollectYaku(yakus, jihaiYakus, tehai, divideInfo, context);
        } else {
            matchCollectYaku(yakus, nonJihaiYakus, tehai, divideInfo, context);
        }
        if (divideInfo != null) {
            if (divideInfo.getAllKanKozuFirst().size() >= 3) {
                matchCollectYaku(yakus, kozuYakus, tehai, divideInfo, context);
            }
            if (divideInfo.getAllShunzuFirst().size() >= 3) {
                matchCollectYaku(yakus, shunzuYakus, tehai, divideInfo, context);
            }
        }
        if (!tehai.isNaki()) {
            matchCollectYaku(yakus, menchanYakus, tehai, divideInfo, context);
        }
        if (context != null) {
            matchCollectYaku(yakus, requestContextYakus, tehai, divideInfo, context);
            if (context.isRon()) {
                matchCollectYaku(yakus, ronYakus, tehai, divideInfo, context);
            }
        }
        matchCollectYaku(yakus, normalYakus, tehai, divideInfo, context);

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

    /**
     * 将役种信息解析并根据名称映射为Map，用于后续快速解析
     *
     * @param yakus 役种
     */
    private void mapYakuMeta(List<IYaku> yakus) {
        nonJihaiYakus = new ArrayList<>();
        jihaiYakus = new ArrayList<>();
        kozuYakus = new ArrayList<>();
        shunzuYakus = new ArrayList<>();
        menchanYakus = new ArrayList<>();
        ronYakus = new ArrayList<>();
        requestContextYakus = new ArrayList<>();
        normalYakus = new ArrayList<>();

        for (IYaku yaku : yakus) {
            Annotation[] annotations = yaku.getClass().getAnnotations();
            Annotation yakuMeta = annotations.length == 0 ? null : annotations[0];
            if (yakuMeta == null) {
                normalYakus.add(yaku);
            } else if (yakuMeta instanceof NonJihaiYaku) {
                nonJihaiYakus.add(yaku);
            } else if (yakuMeta instanceof JihaiYaku) {
                jihaiYakus.add(yaku);
            } else if (yakuMeta instanceof KozuYaku) {
                kozuYakus.add(yaku);
            } else if (yakuMeta instanceof ShunzuYaku) {
                shunzuYakus.add(yaku);
            } else if (yakuMeta instanceof MenchanYaku) {
                menchanYakus.add(yaku);
            } else if (yakuMeta instanceof RonYaku) {
                ronYakus.add(yaku);
            } else if (yakuMeta instanceof RequestContextYaku) {
                requestContextYakus.add(yaku);
            } else {
                normalYakus.add(yaku);
            }
        }
    }

    /**
     * 进行匹配并收集所有匹配到的役种
     *
     * @param container 收集的容器
     * @param currentYaku 当前待匹配的役种
     * @param tehai 手牌
     * @param divideInfo 牌谱分割
     * @param context 游戏上下文
     */
    private void matchCollectYaku(List<IYaku> container, List<IYaku> currentYaku, ITehai tehai, DivideInfo divideInfo
            , PlayerGameContext context) {
        for (IYaku yaku : currentYaku) {
            if (yaku.match(tehai, divideInfo, context)) {
                container.add(yaku);
            }
        }
    }
}
