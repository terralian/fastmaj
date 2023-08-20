package com.github.terralian.fastmaj.game.action.tehai;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 手牌动作类型枚举
 *
 * @author terra.lian
 */
@Getter
@AllArgsConstructor
public enum TehaiActionType {

    /**
     * 切牌
     */
    KIRI(200),

    /**
     * 立直
     */
    REACH(201),

    /**
     * 暗杠
     */
    ANNKAN(202),

    /**
     * 加杠
     */
    KAKAN(203),

    /**
     * 拔北
     */
    KITA(204),

    /**
     * 自摸
     */
    TSUMO(205),

    /**
     * 流局（九种九牌）
     */
    RYUUKYOKU99(206);

    /**
     * 编码
     */
    private final int code;
}
