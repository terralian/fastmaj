package com.github.terralian.fastmaj.paifu.tenhou;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;

import com.github.terralian.fastmaj.paifu.IPaifuParser;
import com.github.terralian.fastmaj.util.Assert;
import com.github.terralian.fastmaj.util.ZipUtil;

/**
 * 默认的天凤牌谱解析器，天凤牌谱下载下来时是通过GZIP进行压缩的压缩包（无后缀），该类
 * 的部分方法针对压缩进行了特别处理，使用时需要注意。
 * 
 * @author terra.lian
 */
public class TenhouPaifuParser implements IPaifuParser {

    /**
     * 牌谱分析器，用于实际处理牌谱解析的信息，转换为所需的实体
     */
    private ITenhouPaifuAnalyzer analyzer;

    /**
     * 根据牌谱分析器构建一个{@link TenhouPaifuParser}，{@link ITenhouPaifuAnalyzer}为所需解析实体的分析器
     * 
     * @param analyzer
     */
    public TenhouPaifuParser(ITenhouPaifuAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    /**
     * 解析牌谱文件，该文件应当是单个牌谱的GZIP形式压缩文件，比如天凤位下载下来的牌谱为一个牌谱压缩包，内部含多个牌谱压缩文件。
     * 这里能够处理的为压缩包里内的单个压缩文件
     */
    @Override
    public void parseFile(File file) throws Exception {
        Assert.isTrue(file != null, "牌谱文件不存在，请检查路径");
        Assert.isTrue(!file.isDirectory(), "参数是一个文件夹，请检查路径");
        String data = ZipUtil.ungzip(file);
        parseContent(data);
    }

    /**
     * 解析牌谱流，该流应当是单个牌谱的GZIP形式压缩文件，比如天凤位下载下来的牌谱为一个牌谱压缩包，内部含多个牌谱压缩文件。
     * 这里能够处理的为压缩包里内的单个压缩文件流
     */
    @Override
    public void parseStream(InputStream in) throws Exception {
        String data = ZipUtil.ungzip(in);
        parseContent(data);
    }

    /**
     * 根据牌谱内容解析牌谱，该内容是通过解压缩后的牌谱内容，是一个可读取的XML文件
     * 
     * @param content XML内容
     */
    @Override
    public void parseContent(String content) throws Exception {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        TenhouPaifuDecodeHandler xmlHandler = new TenhouPaifuDecodeHandler(analyzer);
        InputSource inputSource = new InputSource(new ByteArrayInputStream(content.getBytes()));
        saxParser.parse(inputSource, xmlHandler);
    }
}
