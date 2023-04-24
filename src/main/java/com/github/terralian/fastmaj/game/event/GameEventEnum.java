package com.github.terralian.fastmaj.game.event;

/**
 * 游戏事件的枚举
 *
 * @author terra.lian
 * @since 2023-04-24
 */
public enum GameEventEnum {

    // -----------------------------------
    // 牌河事件
    // -----------------------------------

    /**
     * 吃
     */
    CHII,

    /**
     * 明杠
     */
    MINKAN,

    /**
     * 碰
     */
    PON,

    /**
     * 荣和
     */
    RON,

    // -----------------------------------
    // 手牌事件
    // -----------------------------------

    /**
     * 暗杠
     */
    ANNKAN,

    /**
     * 加杠
     */
    KAKAN,

    /**
     * 切牌
     */
    KIRI,

    /**
     * 拔北
     */
    KITA,

    /**
     * 立直
     */
    REACH,

    /**
     * 九种九牌
     */
    RYUUKYOKU99,

    /**
     * 自摸
     */
    TSUMO,

    // -----------------------------------
    // 系统事件
    // -----------------------------------

    /**
     * 摸牌
     */
    DRAW;

    /**
     * 是否是牌河事件
     *
     * @param eventType 牌河事件
     */
    public static boolean isRiverEvent(GameEventEnum eventType) {
        return eventType == CHII //
                || eventType == MINKAN //
                || eventType == PON //
                || eventType == RON;
    }

    /**
     * 是否是手牌事件
     *
     * @param eventType 牌河事件
     */
    public static boolean isTehaiEvent(GameEventEnum eventType) {
        return eventType == ANNKAN //
                || eventType == KAKAN//
                || eventType == KIRI//
                || eventType == KITA//
                || eventType == REACH//
                || eventType == RYUUKYOKU99//
                || eventType == TSUMO;
    }
}