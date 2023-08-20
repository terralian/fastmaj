package com.github.terralian.fastmaj.game.event.system;

/**
 * 系统事件码
 *
 * @author Terra.Lian
 */
public interface SystemEventCode {

    /**
     * 游戏开始
     */
    int GAME_START = 100;

    /**
     * 游戏结束判定事件
     */
    int GAME_END_CHECK = 101;

    /**
     * 游戏结束
     */
    int GAME_END = 102;

    /**
     * 对局开始
     */
    int KYOKU_START = 103;

    /**
     * 对局结束判定
     */
    int KYOKU_END_CHECK = 104;

    /**
     * 对局结束
     */
    int KYOKU_END = 105;

    /**
     * 流局
     */
    int RYUUKYOKU = 107;

    /**
     * 设置立直棒（扣减1000点）
     */
    int REACH_SET = 108;

    /**
     * 下一枚宝牌
     */
    int NEXT_DORA_EVENT = 109;

    /**
     * 三家和了校验
     */
    int RON3_RYUUKYOKU_CHECK = 110;

    /**
     * 移除同巡标识
     */
    int BREAK_SAME_JUN = 111;

    /**
     * 是否是系统事件
     *
     * @param eventCode 类型
     */
    static boolean isSystemEvent(byte eventCode) {
        return eventCode >= GAME_START && eventCode <= REACH_SET;
    }
}
