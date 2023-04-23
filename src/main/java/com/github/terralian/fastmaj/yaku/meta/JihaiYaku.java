package com.github.terralian.fastmaj.yaku.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字牌役，包含一枚以上的字牌
 * <p/>
 * 该注解为役种匹配剪枝使用，役种上标记的meta注解集合，仅第一个会起效
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface JihaiYaku {
}
