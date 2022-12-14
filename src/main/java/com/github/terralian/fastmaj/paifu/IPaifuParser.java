package com.github.terralian.fastmaj.paifu;

import java.io.File;
import java.io.InputStream;

/**
 * 游戏牌谱解析器，用于解析游戏牌谱文件，该接口会将解析的内容调用对应的分析器
 * 
 * @author terra.lian
 */
public interface IPaifuParser {

    /**
     * 解析牌谱文件
     * 
     * @param file 文件
     * @return 对应实体
     * @throws Exception IO异常或者解析异常
     */
    void parseFile(File file) throws Exception;

    /**
     * 解析流
     * 
     * @param in 字节流
     * @return 对应实体
     * @throws Exception IO异常或者解析异常
     */
    void parseStream(InputStream in) throws Exception;

    /**
     * 解析内容
     * 
     * @param content 内容字符串
     * @throws Exception 解析异常
     */
    void parseContent(String content) throws Exception;
}
