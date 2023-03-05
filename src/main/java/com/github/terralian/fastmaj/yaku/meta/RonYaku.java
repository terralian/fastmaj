package com.github.terralian.fastmaj.yaku.meta;

/**
 * 仅在荣和情况下成立或荣和下一定不成立的役种
 */
public @interface RonYaku {

    /**
     * 若该值为false，则表示该役种在荣和时一定不成立
     */
    boolean value() default true;
}
