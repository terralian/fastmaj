package com.github.terralian.fastmaj;

import com.github.terralian.fastmaj.agari.AgariCalculator;
import com.github.terralian.fastmaj.agari.FuritenChecker;
import com.github.terralian.fastmaj.agari.IAgariCalculator;
import com.github.terralian.fastmaj.agari.IFuritenChecker;
import com.github.terralian.fastmaj.tehai.FastSyantenCalculator;
import com.github.terralian.fastmaj.tehai.ISyantenCalculator;
import com.github.terralian.fastmaj.tehai.IYuukouhaiCalculator;
import com.github.terralian.fastmaj.tehai.YuukouhaiCalculator;
import com.github.terralian.fastmaj.util.SingletonFactory;

/**
 * 快速麻将
 * <p/>
 * 该类用于快速使用当前项目下的热点功能，相当于首页的快捷访问
 * 
 * @author terra.lian
 */
public abstract class FastMajong {

    /**
     * 单例获取默认的向听计算器实例
     */
    public static ISyantenCalculator doGetSyantenCalculator() {
        return SingletonFactory.doGetBean(ISyantenCalculator.class, FastSyantenCalculator.class);
    }

    /**
     * 单例获取振听判定器实例
     */
    public static IFuritenChecker doGetFuritenChecker() {
        return SingletonFactory.doGetBean(IFuritenChecker.class, FuritenChecker.class);
    }

    /**
     * 单例获取和了计算器实例
     */
    public static IAgariCalculator doGetAgariCalculator() {
        return SingletonFactory.doGetBean(IAgariCalculator.class, AgariCalculator.class);
    }

    /**
     * 单例获取有效牌计算器实例
     */
    public static IYuukouhaiCalculator doGetYuukouhaiCalculator() {
        return SingletonFactory.doGetBean(IYuukouhaiCalculator.class, YuukouhaiCalculator.class);
    }
}
