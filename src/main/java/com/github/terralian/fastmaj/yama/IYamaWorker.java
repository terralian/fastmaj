package com.github.terralian.fastmaj.yama;

/**
 * 牌山获取器
 * <p/>
 * 该接口用于根据某些规则来生成牌山，并根据当前场次获取对应的牌山，该接口的实现类一般通过一个种子来实现可复现的牌山随机生成，其生成算法多种多样
 * <p/>
 * 获取到的牌山一般使用136编码，即每种牌占4个编号，从0编码到135
 * <p/>
 * 获取到的数组和实际的牌山，如何使用需要根据实际平台决定
 * 
 * @author terra.lian 
 */
public interface IYamaWorker {

    /**
     * 获取下一个136编码方式的牌山，根据参数循环多次获取，直到获取到对应次数的牌山
     * 
     * @param nextSize 接下来获取的次数[1 ~ N]
     */
    int[] getNextYama(int nextSize);

    /**
     * 获取种子
     */
    String getSeed();
}
