package com.github.terralian.fastmaj.util;

import java.util.Collection;

/**
 * 断言工具类
 *
 * @author terra.lian
 */
public class Assert {


    /**
     * 断言一个对象不为空，若为空则抛出IllegalArgumentException
     *
     * @param object 对象
     * @throws IllegalArgumentException
     */
    public static void notNull(Object object) {
        if (object == null)
            throw new IllegalArgumentException();
    }

    /**
     * 断言所有对象中都不能为空，若为空则抛出IllegalArgumentException
     *
     * @param objects 对象
     * @throws IllegalArgumentException
     */
    public static void notNull(Object... objects) {
        Assert.notEmpty(objects);
        for (Object object : objects) {
            notNull(object);
        }
    }

    /**
     * 断言一个对象不为空，若为空则抛出IllegalArgumentException，其message等于message参数
     *
     * @param object 对象
     * @param message 异常情况下抛出的异常信息
     * @throws IllegalArgumentException
     */
    public static void notNull(Object object, String message) {
        if (object == null)
            throw new IllegalArgumentException(message);
    }

    /**
     * 断言一个数组不为空，若为空则抛出IllegalArgumentException
     *
     * @param array 数组
     * @throws IllegalArgumentException
     */
    public static void notEmpty(Object[] array) {
        if (EmptyUtil.isEmpty(array))
            throw new IllegalArgumentException();

    }

    /**
     * 断言一个数组不为空，若为空则抛出IllegalArgumentException，其message等于message参数
     *
     * @param array 数组
     * @param message 异常情况下抛出的异常信息
     * @throws IllegalArgumentException
     */
    public static void notEmpty(Object[] array, String message) {
        if (EmptyUtil.isEmpty(array))
            throw new IllegalArgumentException(message);
    }

    /**
     * 断言一个集合不为空，若为空则抛出IllegalArgumentException
     *
     * @param collection 集合
     * @throws IllegalArgumentException
     */
    public static void notEmpty(Collection<?> collection) {
        if (EmptyUtil.isEmpty(collection))
            throw new IllegalArgumentException();
    }

    /**
     * 断言一个集合不为空，若为空则抛出IllegalArgumentException，其message等于message参数
     *
     * @param collection 集合
     * @param message 异常情况下抛出的异常信息
     * @throws IllegalArgumentException
     */
    public static void notEmpty(Collection<?> collection, String message) {
        if (EmptyUtil.isEmpty(collection))
            throw new IllegalArgumentException(message);
    }

    /**
     * 断言一个字符串是否为空或者会空白行，若为空则抛出IllegalArgumentException
     *
     * @param string 字符串
     * @throws IllegalArgumentException
     */
    public static void notTrimEmpty(String string) {
        if (EmptyUtil.isTrimEmpty(string))
            throw new IllegalArgumentException();
    }

    /**
     * 断言一个字符串是否为空或者会空白行，若为空则抛出IllegalArgumentException
     *
     * @param string 字符串
     * @param message 异常情况下抛出的异常信息
     */
    public static void notTrimEmpty(String string, String message) {
        if (EmptyUtil.isTrimEmpty(string))
            throw new IllegalArgumentException(message);
    }

    /**
     * 断言是否为TRUE，不为TRUE则抛出IllegalArgumentException
     *
     * @param value 值
     * @throws IllegalArgumentException
     */
    public static void isTrue(boolean value) {
        isTrue(value, null);
    }

    /**
     * 断言是否为TRUE，不为TRUE则抛出IllegalArgumentException，其message等于message参数
     *
     * @param value 值
     * @param message 异常情况下抛出的异常信息
     * @throws IllegalArgumentException
     */
    public static void isTrue(boolean value, String message) {
        if (!value) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言是否为FALSE，不为FALSE则抛出IllegalArgumentException，其message等于message参数
     *
     * @param value 值
     * @param message 异常情况下抛出的异常信息
     */
    public static void isFalse(boolean value, String message) {
        if (value) {
            throw new IllegalArgumentException(message);
        }
    }
}
