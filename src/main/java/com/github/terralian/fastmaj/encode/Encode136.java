package com.github.terralian.fastmaj.encode;

import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.hai.IHai;

/**
 * 136编码法的相关方法
 * <p/>
 * 关于136编码，是将每一枚牌都设置一个唯一的ID，从0开始1万[0,3], 1万[4, 7]...。
 * 由于每种类的牌各有4枚，可以想象为34编码法({@link Encode34})x4，使用这种编码法的好处是每一枚牌都唯一，可以非常好的进行洗牌.
 * 并且只要规定4枚中的哪一枚为红宝牌,则不会丢失信息.
 *
 * @author terra.lian
 */
public abstract class Encode136 {

    /**
     * 将值为136编码的手牌数组转为{@link IHai}
     * 
     * @param hais 每个元素为136编码的数组
     * @param useRed 是否使用红宝牌
     */
    public static IHai[] toHais(int[] hais, boolean useRed) {
        IHai[] result = new IHai[hais.length];
        for (int i = 0; i < hais.length; i++) {
            result[i] = HaiPool.getById(hais[i], useRed);
        }
        return result;
    }

    /**
     * 返回编码的总长度
     */
    public static int length() {
        return 136;
    }
}
