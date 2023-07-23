package com.github.terralian.fastmaj.paifu.tenhou;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import com.github.terralian.fastmaj.paifu.IPaifuParser;
import com.github.terralian.fastmaj.paifu.domain.PaifuGame;
import com.github.terralian.fastmaj.paifu.source.IPaifuSource;
import org.xml.sax.InputSource;

/**
 * 默认的天凤牌谱解析器，天凤牌谱下载下来时是通过GZIP进行压缩的压缩包（无后缀），该类
 * 的部分方法针对压缩进行了特别处理，使用时需要注意。
 *
 * @author terra.lian
 */
public class TenhouPaifuParser implements IPaifuParser<PaifuGame> {

    /**
     * 牌谱分析器，用于实际处理牌谱解析的信息，转换为所需的实体
     */
    private final TenhouPaifuDecodeHandler decodeHandler;

    private final ITenhouPaifuParseHandler parseHandler;

    /**
     * 根据牌谱分析器构建一个{@link TenhouPaifuParser}，{@link ITenhouPaifuParseHandler}为牌谱的解析处理器实际业务核心
     *
     * @param parseHandler
     */
    public TenhouPaifuParser(ITenhouPaifuParseHandler parseHandler) {
        this.parseHandler = parseHandler;
        this.decodeHandler = new TenhouPaifuDecodeHandler(parseHandler);
    }

    /**
     * 解析牌谱流，该流应当是单个牌谱的GZIP形式压缩文件，比如天凤位下载下来的牌谱为一个牌谱压缩包，内部含多个牌谱压缩文件。
     * 这里能够处理的为压缩包里内的单个压缩文件流
     */
    @Override
    public PaifuGame parse(IPaifuSource paifuSource) throws Exception {
        try (InputStreamReader ir = new InputStreamReader(paifuSource.getInputStream());
                BufferedReader br = new BufferedReader(ir)) {
            String data = br.lines().collect(Collectors.joining());
            return parseContent(data);
        }
    }

    /**
     * 根据牌谱内容解析牌谱，该内容是通过解压缩后的牌谱内容，是一个可读取的XML文件
     *
     * @param content XML内容
     */
    private PaifuGame parseContent(String content) throws Exception {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        InputSource inputSource = new InputSource(new ByteArrayInputStream(content.getBytes()));
        saxParser.parse(inputSource, decodeHandler);
        PaifuGame paifuGame = parseHandler.getParseData();
        paifuGame.setOrigin(content);
        return paifuGame;
    }
}
