package com.github.terralian.fastmaj.yaku.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 仅在荣和情况下成立或荣和下一定不成立的役种
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RonYaku {

    /**
     * 若该值为false，则表示该役种在荣和时一定不成立
     */
    boolean value() default true;
}
