package com.github.terralian.fastmaj.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * 字符串，数组，字符串判空工具类，防止抛出空指针异常
 * 
 * @author terra.lian 
 */
public abstract class EmptyHelper {

    /**
     * 判断字符串是否为空
     * 
     * @param value 字符串
     */
    public static boolean isEmpty(String value) {
        return value == null || value.length() < 1;
    }

    /**
     * 判断字符串是否非空
     * 
     * @param value 字符串
     */
    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    /**
     * 判断字符串是否为空或者为空白行
     * 
     * @param value 字符串
     */
    public static boolean isTrimEmpty(String value) {
        return value == null || value.trim().length() < 1;
    }

    /**
     * 判断一个字符串不是非空且非空白行
     * 
     * @param value 字符串
     */
    public static boolean isNotBlank(String value) {
        return !isTrimEmpty(value);
    }

    /**
     * 判断数组是否全为空
     * 
     * @param array 字符串数组
     */
    public static boolean isAllEmpty(String[] array) {
        if (array == null)
            return true;
        for (String s : array) {
            if (!isEmpty(s))
                return false;
        }
        return true;
    }

    /**
     * 判断数组是否存在至少一个为空
     * 
     * @param array 字符串数组
     */
    public static boolean isAnyEmpty(String[] array) {
        if (array == null)
            return true;
        for (String s : array) {
            if (isEmpty(s))
                return true;
        }
        return false;
    }

    /**
     * 判断一个数组是否为空
     * 
     * @param array 数组
     */
    public static boolean isEmpty(Object[] array) {
        return array == null || array.length < 1;
    }

    /**
     * 判断一个数组是否不为空
     * 
     * @param array 数组
     */
    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }

    /**
     * 判断一个集合是否为空
     * 
     * @param collection 集合
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断一个集合是否不为空
     * 
     * @param collection 集合
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断一个Map是否为空
     * 
     * @param map Map
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断一个Map是否不为空
     * 
     * @param map Map
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 判断一个迭代器是否为空
     * 
     * @param iterator 迭代器
     */
    public static boolean isEmpty(Iterator<?> iterator) {
        return iterator == null || !iterator.hasNext();
    }

    /**
     * 判断一个迭代器是否不为空
     * 
     * @param iterator 迭代器
     */
    public static boolean isNotEmpty(Iterator<?> iterator) {
        return !isEmpty(iterator);
    }
}
