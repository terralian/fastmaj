package com.github.terralian.fastmaj.yama;

/**
 * 牌山数组实例
 *
 * @author terra.lian
 */
public class YamaArray implements IYamaArray {

    /**
     * 牌山值
     */
    private int[] value;

    /**
     * 版本，用于兼容一个平台在不同时期的牌谱差异
     */
    private String version;

    /**
     * 构建牌山数组
     *
     * @param value 牌山值
     */
    public YamaArray(int[] value) {
        this.value = value;
    }

    public YamaArray(int[] value, String version) {
        this.value = value;
        this.version = version;
    }

    @Override
    public int[] value() {
        return value;
    }

    @Override
    public String version() {
        return version;
    }

    public void setValue(int[] value) {
        this.value = value;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
