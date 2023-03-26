package com.github.terralian.fastmaj.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

/**
 * 提供获取在test下的资源文件的方法集合
 */
public abstract class TestResourceUtil {

    /**
     * 将文件内容读为字符串
     *
     * @param prefix 前缀包
     * @param simpleFileName 文件名
     */
    public static String readToString(String prefix, String simpleFileName) {
        String fullFileName = getFullFileName(prefix, simpleFileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(fullFileName))) {
            return reader.lines().toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 读取文件
     *
     * @param prefix 前缀包
     * @param simpleFileName 文件名
     */
    public static File readToFile(String prefix, String simpleFileName) {
        String fullFileName = getFullFileName(prefix, simpleFileName);
        File targetFile = new File(fullFileName);
        if (!targetFile.exists()) {
            throw new IllegalArgumentException("文件不存在：" + fullFileName);
        }
        return targetFile;
    }

    /**
     * 获取文件的完整路径
     *
     * @param prefix 前缀包
     * @param simpleFileName 文件名
     */
    public static String getFullFileName(String prefix, String simpleFileName) {
        String projectUrl = Objects.requireNonNull(TestResourceUtil.class.getResource("/")).getPath();
        return projectUrl + prefix + "/" + simpleFileName;
    }
}
