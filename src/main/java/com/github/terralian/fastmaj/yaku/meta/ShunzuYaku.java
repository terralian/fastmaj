package com.github.terralian.fastmaj.yaku.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 顺子役，包含顺子组成的役种类，需要一定程度的顺子才能达成
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ShunzuYaku {

    /**
     * 最小顺子数，一个顺子由相邻的3枚牌组成
     */
    int minShunzuSize() default 1;
}
