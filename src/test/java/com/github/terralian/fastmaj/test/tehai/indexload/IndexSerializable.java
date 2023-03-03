package com.github.terralian.fastmaj.test.tehai.indexload;

import java.io.Serializable;

/**
 * 可以克隆的序列化的向听索引文件
 *
 * @author terra.lian
 * @since 2023-03-03
 */
public class IndexSerializable implements Serializable {
    private int[][] mp1;
    private int[][] mp2;

    public int[][] getMp1() {
        return mp1;
    }

    public void setMp1(int[][] mp1) {
        this.mp1 = mp1;
    }

    public int[][] getMp2() {
        return mp2;
    }

    public void setMp2(int[][] mp2) {
        this.mp2 = mp2;
    }
}
