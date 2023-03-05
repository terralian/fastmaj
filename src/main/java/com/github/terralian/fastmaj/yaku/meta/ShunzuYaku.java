package com.github.terralian.fastmaj.yaku.meta;

/**
 * 顺子役，包含顺子组成的役种类，需要一定程度的顺子才能达成
 */
public @interface ShunzuYaku {

    /**
     * 最小顺子数，一个顺子由相邻的3枚牌组成
     */
    int minShunzuSize() default 1;
}
