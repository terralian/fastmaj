package com.github.terralian.fastmaj.game.option;

/**
 * 立直规则
 * 
 * @author terra.lian 
 */
public enum ReachRule {

    /**
     * 牌山需要剩余玩家人数以上的牌，才可以立直。4麻4枚，3麻3枚
     */
    YAMA4,

    /**
     * 只要还有可摸牌即可立直
     */
    YAMA1,
}
