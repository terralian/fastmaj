package com.github.terralian.fastmaj.paifu.tenhou;

/**
 * 天凤牌谱相关的常量池
 * 
 * @author terra.lian
 */
public final class TenhouPaifuStringPool {
    
    /**
     * 天凤的役种（顺序特定为牌谱的定义值）
     */
    public static final String[] YAKUS = {
            // 一番
            "门前清自摸和", "立直", "一发", "抢杠", "岭上开花",
            "海底摸月", "河底捞鱼", "平和", "断幺九", "一杯口",
            "自风东", "自风南", "自风西", "自风北",
            "场风東", "场风南", "场风西", "场风北",
            "役牌白", "役牌发", "役牌中",
            // 二番
            "两立直", "七对子", "混全带幺九", "一气通贯", "三色同顺",
            "三色同刻", "三杠子", "对对和", "三暗刻", "小三元", "混老头",
            // 三番
            "二杯口", "纯全带幺九", "混一色",
            // 六番
            "清一色",
            // 满贯
            "人和",
            // 役满
            "天和", "地和", "大三元", "四暗刻", "四暗刻单骑", "字一色",
            "绿一色", "清老头", "九莲宝灯", "纯正九莲宝灯", "国士无双",
            "国士无双１３面", "大四喜", "小四喜", "四杠子",
            // 悬赏役
            "宝牌", "里宝牌", "红宝牌"
    };

    /**
     * 段位场
     */
    public static final String[] TAKU = {"一般", "上级", "特上", "凤凰"};

    /**
     * 整场/半场
     */
    public static final String[] KAZE_RULE = {"东风战", "东南战"};

    /**
     * 天凤段位
     */
    public static final String[] DANS = {"新人", "９级", "８级", "７级", "６级", "５级", "４级", "３级", "２级", "１级", //
            "初段", "二段", "三段", "四段", "五段", "六段", "七段", "八段", "九段", "十段", "天凤"};

    /**
     * 九种九牌
     */
    public static final String RYUUKYOKU_TYPE_99 = "yao9";
    /**
     * 四家立直
     */
    public static final String RYUUKYOKU_TYPE_REACH4 = "reach4";
    /**
     * 三家和了
     */
    public static final String RYUUKYOKU_TYPE_RON_3 = "ron3";
    /**
     * 四杠散了
     */
    public static final String RYUUKYOKU_TYPE_KAN4 = "kan4";
    /**
     * 四風連打
     */
    public static final String RYUUKYOKU_TYPE_KAZE4 = "kaze4";
    /**
     * 流局满贯
     */
    public static final String RYUUKYOKU_TYPE_NM = "nm";
}
