package com.github.terralian.fastmaj.paifu.source;

import java.io.InputStream;

/**
 * 输入流的牌谱源
 *
 * @author Terra.Lian
 */
public class InputSource implements IPaifuSource {

    private final InputStream is;

    /**
     * 根据输入流创建牌谱源
     *
     * @param is 输入流
     */
    public InputSource(InputStream is) {
        this.is = is;
    }

    @Override
    public InputStream getInputStream() throws Exception {
        return is;
    }
}
