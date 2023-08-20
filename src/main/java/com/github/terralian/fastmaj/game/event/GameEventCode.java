package com.github.terralian.fastmaj.game.event;

/**
 * 游戏事件类型，包含系统事件、手牌事件、牌河事件
 *
 * @author Terra.Lian
 */
public interface GameEventCode {

    /**
     * 摸牌事件
     */
    int DRAW = 1;

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
     * 切牌
     */
    int KIRI = 200;

    /**
     * 暗杠
     */
    int ANNKAN = 201;

    /**
     * 加杠
     */
    int KAKAN = 202;

    /**
     * 立直
     */
    int REACH = 203;

    /**
     * 拔北
     */
    int KITA = 204;

    /**
     * 自摸
     */
    int TSUMO = 205;

    /**
     * 九种九牌
     */
    int RYUUKYOKU99 = 206;

    /**
     * 跳过
     */
    int SKIP = 300;

    /**
     * 吃
     */
    int CHI = 301;

    /**
     * 碰
     */
    int PON = 302;

    /**
     * 明杠
     */
    int MINKAN = 303;

    /**
     * 暗杠
     */
    int RON = 304;

    /**
     * 请求玩家手牌动作
     */
    int REQUEST_TEHAI_ACTION = 400;

    /**
     * 请求玩家牌河动作
     */
    int REQUEST_RIVER_ACTION = 401;
}
