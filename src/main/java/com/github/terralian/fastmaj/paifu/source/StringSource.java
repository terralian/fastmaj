package com.github.terralian.fastmaj.paifu.source;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 字符串的牌谱数据源
 *
 * @author Terra.Lian
 */
public class StringSource implements IPaifuSource {

    private final String data;

    /**
     * 根据字符串创建的牌谱源
     *
     * @param data 字符串
     */
    public StringSource(String data) {
        this.data = data;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(data.getBytes());
    }
}
