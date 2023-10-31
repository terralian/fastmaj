package com.github.terralian.fastmaj.game.event.system;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统事件类型
 *
 * @author terra.lian
 */
@Getter
@AllArgsConstructor
public enum SystemEventType {

    /**
     * 摸牌事件
     */
    DRAW(1),

    /**
     * 游戏开始
     */
    GAME_START(100),

    /**
     * 游戏结束判定事件
     */
    GAME_END_CHECK(101),

    /**
     * 游戏结束
     */
    GAME_END(102),

    /**
     * 对局开始
     */
    KYOKU_START(103),

    /**
     * 对局结束判定
     */
    KYOKU_END_CHECK(104),

    /**
     * 对局结束
     */
    KYOKU_END(105),

    /**
     * 流局
     */
    RYUUKYOKU(106),

    /**
     * 三家和了校验
     */
    RON3_RYUUKYOKU_CHECK(107),

    /**
     * 设置立直棒（扣减1000点）
     */
    REACH_SET(108),

    /**
     * 下一枚宝牌
     */
    NEXT_DORA(109),

    /**
     * 移除同巡标识
     */
    BREAK_SAME_JUN(110),

    /**
     * 请求玩家手牌动作
     */
    TEHAI_ACTION_REQUEST(111),

    /**
     * 请求玩家牌河动作
     */
    RIVER_ACTION_REQUEST(112);

    /**
     * 事件编码
     */
    private final int code;
}
