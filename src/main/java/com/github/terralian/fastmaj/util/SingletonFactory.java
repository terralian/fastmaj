package com.github.terralian.fastmaj.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 简易单例工厂
 * <p/>
 * 该类用于缓存需要使用单例模式创建的实例
 * 
 * @author terra.lian 
 */
public abstract class SingletonFactory {

    /**
     * 缓存单例的Map
     */
    private static final Map<String, Object> singletonMap = new HashMap<>();

    /**
     * 二次校验的单例实例获取
     * <p/>
     * 若接口的实例不存在，会构建一个对应的实例并缓存。若接口的实例存在，返回对应的实例
     * <p/>
     * <b>该方法的实例类需要可以简单进行实例化（提供一个无参数的构造函数）</b>
     *
     * @param <T> 接口类型
     * @param interfaceClass 接口类
     * @param actualClass 实例类
     * @throws IllegalArgumentException 若实例类不能简单创建，会报该异常
     */
    @SuppressWarnings("unchecked")
    public static <T> T doGetBean(Class<T> interfaceClass, Class<? extends T> actualClass) {
        String className = interfaceClass.getName();
        Object obj = singletonMap.get(className);
        if (obj == null) {
            synchronized (SingletonFactory.class) {
                obj = singletonMap.get(className);
                if (obj == null) {
                    try {
                        obj = actualClass.newInstance();
                    } catch (Exception e) {
                        throw new IllegalArgumentException(e);
                    }
                    singletonMap.put(className, obj);
                }
            }
        }
        return (T) obj;
    }

    /**
     * 仅获取接口对应的实例，配合{@link #registerBean}来缓存复杂构建的实例
     *
     * @param <T>
     * @param interfaceClass
     */
    @SuppressWarnings("unchecked")
    public static <T> T onlyGet(Class<T> interfaceClass) {
        String className = interfaceClass.getName();
        Object obj = singletonMap.get(className);
        return obj == null ? null : (T) obj;
    }

    /**
     * 强制注册实例
     * <p/>
     * 若实例已存在，会覆盖对应的实例。若实例不存在直接缓存
     *
     * @param <T> 接口类型
     * @param interfaceClass 接口类
     * @param obj 实例
     */
    public static <T> void registerBean(Class<T> interfaceClass, Object obj) {
        synchronized (SingletonFactory.class) {
            String className = interfaceClass.getName();
            singletonMap.put(className, obj);
        }
    }
}
