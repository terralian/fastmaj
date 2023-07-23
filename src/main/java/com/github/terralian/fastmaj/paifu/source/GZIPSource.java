package com.github.terralian.fastmaj.paifu.source;

import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * 经过GZIP压缩的牌谱，如天凤牌谱默认使用了GZIP压缩传输。
 *
 * @author Terra.Lian
 */
public class GZIPSource implements IPaifuSource {

    private final IPaifuSource ps;

    /**
     * 装饰一个牌谱源，为其增加解析GZIP的能力
     *
     * @param ps
     */
    public GZIPSource(IPaifuSource ps) {
        this.ps = ps;
    }

    @Override
    public InputStream getInputStream() throws Exception {
        return new GZIPInputStream(ps.getInputStream());
    }
}
