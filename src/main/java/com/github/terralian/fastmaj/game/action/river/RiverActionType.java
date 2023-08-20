package com.github.terralian.fastmaj.game.action.river;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 牌河动作类型
 *
 * @author terra.lian
 */
@Getter
@AllArgsConstructor
public enum RiverActionType {
    /**
     * 吃
     */
    CHII(301),

    /**
     * 碰
     */
    PON(302),

    /**
     * 明杠
     */
    MINKAN(303),

    /**
     * 荣和
     */
    RON(304),

    /**
     * 跳过
     */
    SKIP(300);

    /**
     * 编码
     */
    private final int code;
}
