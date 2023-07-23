package com.github.terralian.fastmaj.paifu.source;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * 通过文件解析源
 *
 * @author Terra.Lian
 */
public class FileSource implements IPaifuSource {

    private final File file;

    /**
     * 根据文件创建牌谱源
     *
     * @param file 文件
     */
    public FileSource(File file) {
        this.file = file;
    }

    @Override
    public InputStream getInputStream() throws Exception {
        return Files.newInputStream(file.toPath());
    }
}
