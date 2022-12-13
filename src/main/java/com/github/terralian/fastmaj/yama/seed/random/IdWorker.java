package com.github.terralian.fastmaj.yama.seed.random;

/**
 * 获取唯一ID作为种子
 * 
 * @author terra.lian
 */
public class IdWorker {

    private static Sequence sequence = new Sequence();

    /**
     * 获取唯一ID
     *
     * @return id
     */
    public static long getId() {
        return sequence.nextId();
    }
}
