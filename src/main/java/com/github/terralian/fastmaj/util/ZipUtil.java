package com.github.terralian.fastmaj.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

/**
 * 解压工具类
 * 
 * @author terra.lian
 */
public abstract class ZipUtil {

    /**
     * 解压GZIP，返回加压的字符串
     * <p>
     * 天凤牌谱使用GZIP进行二次压缩，通过解压可以获得其XML文档
     * 
     * @param fileName 文件名
     * @throws IOException IO异常
     * @throws FileNotFoundException 文件未找到异常
     */
    public static String ungzip(String fileName) throws FileNotFoundException, IOException {
        return ungzip(new File(fileName));
    }

    /**
     * 解压GZIP，返回加压的字符串
     * <p>
     * 天凤牌谱使用GZIP进行二次压缩，通过解压可以获得其XML文档
     * 
     * @param file 文件
     * @throws IOException IO异常
     * @throws FileNotFoundException 文件未找到异常
     */
    public static String ungzip(File file) throws FileNotFoundException, IOException {
        try (FileInputStream fin = new FileInputStream(file);) {
            return ungzip(fin);
        }
    }

    /**
     * 解压GZIP，返回加压的字符串
     * <p>
     * 天凤牌谱使用GZIP进行二次压缩，通过解压可以获得其XML文档
     * 
     * @param file 文件
     * @throws IOException IO异常
     * @throws FileNotFoundException 文件未找到异常
     */
    public static String ungzip(InputStream stream) throws FileNotFoundException, IOException {
        try (GZIPInputStream gzin = new GZIPInputStream(stream);
                InputStreamReader ir = new InputStreamReader(gzin);
                BufferedReader br = new BufferedReader(ir);) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
