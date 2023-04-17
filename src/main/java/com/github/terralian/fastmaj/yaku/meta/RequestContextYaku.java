package com.github.terralian.fastmaj.yaku.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 必须使用游戏上下文才能进行判定的役种
 *
 * @author terra.lian
 * @since 2023-04-17
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestContextYaku {
}
