package com.github.terralian.fastmaj.game.option;

/**
 * 新宝牌指示牌翻开的规则
 * 
 * @author terra.lian 
 */
public enum DoraAddRule {

    /**
     * 在玩家切牌之前就会翻开新的宝牌
     * <p/>
     * 这种规则下，新宝牌会即刻加入玩家的和了计算
     */
    BEFORE_KIRI,

    /**
     * 在玩家切牌之后才会翻开新的宝牌
     * <p/>
     * 这种规则下，玩家岭上和了，或者被荣和新的宝牌都不会参与计算
     */
    AFTER_KIRI,
}
