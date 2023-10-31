package com.github.terralian.fastmaj.paifu.factory;

import com.github.terralian.fastmaj.paifu.IPaifuParser;
import com.github.terralian.fastmaj.paifu.IPaifuParserFactory;
import com.github.terralian.fastmaj.paifu.domain.PaifuGame;
import com.github.terralian.fastmaj.paifu.parser.tenhou.TenhouPaifuGameParseHandler;
import com.github.terralian.fastmaj.paifu.parser.tenhou.TenhouPaifuParser;

/**
 * 天凤平台的{@link IPaifuParser}牌谱解析
 */
public class TenhouPaifuParserFactory implements IPaifuParserFactory {

    @Override
    public IPaifuParser<PaifuGame> createParser() {
        return new TenhouPaifuParser(new TenhouPaifuGameParseHandler());
    }
}
