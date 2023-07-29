package com.github.terralian.fastmaj.yama.worker.common;

import com.github.terralian.fastmaj.yama.IYamaWorker;
import com.github.terralian.fastmaj.yama.YamaArray;

/**
 * 虚拟牌山生成器，该类并不是真正的牌山生成器，只是一个牌山生成器的模拟，其内部牌山和种子都是由外部设置而来。
 * 该类的作用的在回放或者牌谱解析时，由于牌山已定义，此时游戏核心不再需要二次进行牌山生成器解析，则此时可以使用该类来直接兼容。
 *
 * @author terra.lian
 */
public class VirtualYamaWorker implements IYamaWorker {

    /**
     * 种子
     */
    private String seed;

    /**
     * 牌山
     */
    private int[] yama;

    /**
     * 初始化构建{@link VirtualYamaWorker}
     */
    public VirtualYamaWorker() {
    }

    /**
     * 根据种子初始化构建{@link VirtualYamaWorker}
     *
     * @param seed 种子
     */
    public VirtualYamaWorker(String seed) {
        this.seed = seed;
    }

    @Override
    public YamaArray getNextYama(int nextSize) {
        return new YamaArray(yama);
    }

    /**
     * 设置牌山
     *
     * @param yama 牌山
     */
    public void setYama(int[] yama) {
        this.yama = yama;
    }

    @Override
    public String getSeed() {
        return seed;
    }

    /**
     * 设置种子
     *
     * @param seed 种子
     */
    public void setSeed(String seed) {
        this.seed = seed;
    }
}
