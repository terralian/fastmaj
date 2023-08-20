package com.github.terralian.fastmaj.game.event.river;

/**
 * 牌河动作事件码
 *
 * @author Terra.Lian
 */
public interface RiverEventCode {

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
}
