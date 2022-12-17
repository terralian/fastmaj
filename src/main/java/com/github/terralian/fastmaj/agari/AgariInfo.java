package com.github.terralian.fastmaj.agari;

import java.util.List;

import com.github.terralian.fastmaj.yaku.IYaku;

import lombok.Data;

/**
 * 和了信息
 *
 * @author terra.lian
 */
@Data
public class AgariInfo {

    /**
     * 得点
     */
    private Integer score;

    /**
     * 符数
     */
    private Integer fu;

    /**
     * 番数
     */
    private Integer ban;

    /**
     * 和了役
     */
    private List<IYaku> yakus;

    /**
     * 手牌的分割
     */
    private DivideInfo divideInfo;

    /**
     * 增减后的玩家分数
     */
    private int[] increaseAndDecrease;
}
