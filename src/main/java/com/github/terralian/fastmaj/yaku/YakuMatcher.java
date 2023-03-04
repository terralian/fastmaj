package com.github.terralian.fastmaj.yaku;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.util.CollectionUtil;
import com.github.terralian.fastmaj.util.EmptyUtil;
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

/**
 * {@link IYakuMatcher}的默认实现
 * <p/>
 * 该匹配器下的役种
 * <ul>
 * <li>计 通用一般役种，如三暗刻，一气等现代经常使用的役。
 * <li>计 累计役满，如MLG规则是无累计役满的规则。
 * <li>不计 人和
 * </ul>
 * 
 * @author terra.lian 
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
    private List<IYaku> hasJihaiYakus;
    /**
     * 字牌相关役，字牌大于3枚以上判定
     */
    private List<IYaku> jihaiYakus;

    // 与字牌无关
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
     * 通用役种
     */
    private List<IYaku> commonYakus;

    /**
     * 构建{@link YakuMatcher} 实例
     */
    public YakuMatcher() {
        this.classifyCreateYaku();
    }

    @Override
    public List<IYaku> match(ITehai tehai, List<DivideInfo> divideInfos, PlayerGameContext context) {
        // 单个分割的情况直接进行匹配
        if (EmptyUtil.isEmpty(divideInfos)) {
            divideInfos.add(null);
        } else if (divideInfos.size() == 1) {
            return match(tehai, divideInfos.get(0), context);
        }

        // 多种分割结果需要取最好的一种
        List<IYaku> lastYakus = new ArrayList<>();
        int lastBans = 0;

        for (DivideInfo divideInfo : divideInfos) {
            List<IYaku> tmpYakus = match(tehai, divideInfo, context);

            // 匹配到了役满，役满下基本没有多种分割结果
            if (tmpYakus.size() > 0 && tmpYakus.get(0).isYakuman()) {
                lastYakus = tmpYakus;
                break;
            }

            // 按番数匹配最大
            int tmpBans = tmpYakus.stream().mapToInt(k -> k.getHan(tehai.isNaki())).sum();
            if (lastBans < tmpBans) {
                lastYakus = tmpYakus;
                lastBans = tmpBans;
            }
        }

        return lastYakus;
    }

    /**
     * 进行单个分割的役匹配
     * 
     * @param tehai 手牌
     * @param divideInfo 分割结果
     * @param context 用户信息
     */
    @Override
    public List<IYaku> match(ITehai tehai, DivideInfo divideInfo, PlayerGameContext context) {
        Predicate<IYaku> yakuPredicate = k -> k.match(tehai, divideInfo, context);
        List<IYaku> yakus = new ArrayList<>();

        // 字牌数量
        int jihaiSize = analyseJihaiSize(tehai);

        // 无字牌
        if (jihaiSize == 0) {
            yakus.addAll(CollectionUtil.filterToList(nonJihaiYakus, yakuPredicate));
        }
        // 含字牌
        else if (jihaiSize > 0) {
            yakus.addAll(CollectionUtil.filterToList(hasJihaiYakus, yakuPredicate));
            // 字牌
            if (jihaiSize >= 3) {
                yakus.addAll(CollectionUtil.filterToList(jihaiYakus, yakuPredicate));
            }
        }
        if (divideInfo != null) {
            // 对子役
            if (divideInfo.getAllKanKozuFirst().size() >= 3) {
                yakus.addAll(CollectionUtil.filterToList(kozuYakus, yakuPredicate));
            }
            // 顺子役
            if (divideInfo.getAllShunzuFirst().size() >= 3) {
                yakus.addAll(CollectionUtil.filterToList(shunzuYakus, yakuPredicate));
            }
        }
        // 门清役
        yakus.addAll(CollectionUtil.filterToList(menchanYakus, yakuPredicate));
        // 荣和役
        if (context != null && context.isRon()) {
            yakus.addAll(CollectionUtil.filterToList(ronYakus, yakuPredicate));
        }
        // 通常役
        yakus.addAll(CollectionUtil.filterToList(commonYakus, yakuPredicate));

        // 含有役满，则去除其他非役满役
        for (IYaku yaku : yakus) {
            if (yaku instanceof IYakuman) {
                yakus = CollectionUtil.filterToList(yakus, k -> k.isYakuman());
                break;
            }
        }
        return yakus;
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

    // -----------------------------------------
    // 役种初始化分组索引构建
    // -----------------------------------------

    /**
     * 分类创建役
     */
    private void classifyCreateYaku() {
        // 无字牌役
        this.setNonJihaiYakus();
        // 含字牌役
        this.setHasJihaiYakus();
        // 字牌役
        this.setJihaiYakus();
        // 对子役
        this.setKozuYakus();
        // 顺子役
        this.setShunzuYakus();
        // 门清役
        this.setMenchanYakus();
        // 荣和役
        this.setRonYakus();
        // 无剪枝的役
        this.setCommonYakus();
    }

    /**
     * 设置无字牌的役种
     * <p/>
     * 该部分役种用无字牌判断会提升剪枝效率
     */
    private void setNonJihaiYakus() {
        nonJihaiYakus = new ArrayList<>();

        // 断幺九
        nonJihaiYakus.add(new Tanyaotyu());
        // 纯全带
        nonJihaiYakus.add(new JyunTyanta());
        // 清一色
        nonJihaiYakus.add(new Tinizu());
    }

    /**
     * 设置含字牌的一种
     * <p/>
     * 该部分役种用含字牌判断会提升剪枝效率
     */
    private void setHasJihaiYakus() {
        hasJihaiYakus = new ArrayList<>();

        // 混老头
        hasJihaiYakus.add(new Honroutou());
        // 混带
        hasJihaiYakus.add(new HonTyanta());
        // 混一色
        hasJihaiYakus.add(new Honitu());
    }

    /**
     * 设置字牌的役种，字牌需要大于等于3枚
     * <p/>
     * 该部分役种用字牌判断会提升剪枝效率
     */
    private void setJihaiYakus() {
        jihaiYakus = new ArrayList<>();

        // 场风
        jihaiYakus.add(new Bakaze());
        // 自风
        jihaiYakus.add(new Jikaze());
        // 白
        jihaiYakus.add(new Haku());
        // 发
        jihaiYakus.add(new Hatu());
        // 中
        jihaiYakus.add(new Tyun());
        // 小三元
        jihaiYakus.add(new Syousangen());
        // 字一色
        jihaiYakus.add(new Tuuiisou());
    }

    /**
     * 设置对子役，对子需要大于等于3个
     * <p/>
     * 该部分役种用对子判断会提升剪枝效率
     */
    private void setKozuYakus() {
        kozuYakus = new ArrayList<>();

        // 三暗刻
        kozuYakus.add(new Sanankou());
        // 三杠子
        kozuYakus.add(new Sankanzu());
        // 三色同刻
        kozuYakus.add(new SansyokuDoupon());
        // 对对和
        kozuYakus.add(new Toitoi());
        // 四暗刻
        kozuYakus.add(new Suuankou());
        // 四暗刻单骑
        kozuYakus.add(new SuuankouTanki());
        // 四杠子
        kozuYakus.add(new Syousuusii());
        // 大三元
        kozuYakus.add(new Daisangen());
        // 大四喜
        kozuYakus.add(new Daisuusii());
        // 清老头
        kozuYakus.add(new Tinroutou());
    }

    /**
     * 设置顺子役，顺子需要大于3个
     * <p/>
     * 该部分役种用顺子判断会提升剪枝效率
     */
    private void setShunzuYakus() {
        shunzuYakus = new ArrayList<>();

        // 平和
        shunzuYakus.add(new Pinfu());
        // 一气
        shunzuYakus.add(new Ittuu());
        // 三色同顺
        shunzuYakus.add(new SansyokuDoujyun());
        // 九莲
        shunzuYakus.add(new Tyuuren());
        // 纯九莲
        shunzuYakus.add(new Tyuuren9());
    }

    /**
     * 设置剩余的门清役
     * <p/>
     * 该部分役种仅有门清可以进行提升剪枝效率
     */
    private void setMenchanYakus() {
        menchanYakus = new ArrayList<>();

        // 立直
        menchanYakus.add(new Reach());
        // 两立直
        menchanYakus.add(new DoubleReach());
        // 一发
        menchanYakus.add(new Iipatu());
        // 自摸
        menchanYakus.add(new Tsumo());
        // 一杯口
        menchanYakus.add(new Iipeikou());
        // 七对子
        menchanYakus.add(new Tiitoitu());
        // 国士无双
        menchanYakus.add(new Kokusi());
        // 国士13面
        menchanYakus.add(new Kokusi13());
        // 二杯口
        menchanYakus.add(new Ryanpeikou());
    }

    /**
     * 设置荣和役
     * <p/>
     * 该部分役种仅在荣和生效
     */
    private void setRonYakus() {
        ronYakus = new ArrayList<>();

        // 河底
        ronYakus.add(new Houtei());
        // 抢杠
        ronYakus.add(new TyanKan());
    }

    /**
     * 设置通常役
     * <p/>
     * 这部分的役种没有剪枝条件
     */
    private void setCommonYakus() {
        commonYakus = new ArrayList<>();

        // 海底
        commonYakus.add(new Haitei());
        // 岭上
        commonYakus.add(new Rinsyan());
        // 绿一色
        commonYakus.add(new Ryuuiisou());
        // 天和
        commonYakus.add(new Tenho());
        // 地和
        commonYakus.add(new TiiHo());
    }
}
