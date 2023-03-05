package com.github.terralian.fastmaj.yaku.meta;

/**
 * 字牌役
 */
public @interface JihaiYaku {

    /**
     * 最小字牌数量，一般需要3枚及以上
     */
    int minJihaiSize() default 3;
}
