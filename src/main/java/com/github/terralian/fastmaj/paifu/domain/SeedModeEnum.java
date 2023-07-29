package com.github.terralian.fastmaj.paifu.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Terra.Lian
 */
@AllArgsConstructor
@Getter
public enum SeedModeEnum {
    /**
     * 整个游戏使用同一个种子生成牌山
     */
    SINGLE,
    /**
     * 每个对局使用不同的种子生成牌山
     */
    MULTI,
    /**
     * 种子未知，则可能仅有牌山信息
     */
    UNKNOWN,
}
