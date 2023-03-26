package com.github.terralian.fastmaj.yaku.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 刻子役，包含一定刻子组成的役，需要一定数量的刻子才能达成
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface KozuYaku {

    /**
     * 最小刻子数，一个刻子由>=3枚的相同牌组成
     */
    int minKozuSize() default 1;
}
