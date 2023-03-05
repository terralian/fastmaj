package com.github.terralian.fastmaj.yaku.meta;

/**
 * 刻子役，包含一定刻子组成的役，需要一定数量的刻子才能达成
 */
public @interface KozuYaku {

    /**
     * 最小刻子数，一个刻子由>=3枚的相同牌组成
     */
    int minKozuSize() default 1;
}
