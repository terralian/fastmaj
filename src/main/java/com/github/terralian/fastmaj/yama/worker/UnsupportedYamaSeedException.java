package com.github.terralian.fastmaj.yama.worker;

/**
 * 不支持的牌山种子类型异常
 *
 * @author terra.lian
 */
public class UnsupportedYamaSeedException extends RuntimeException {

    /**
     * 构建一个不支持的牌山种子类型异常
     *
     * @param message 消息
     */
    public UnsupportedYamaSeedException(String message) {
        super(message);
    }
}
