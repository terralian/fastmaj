package com.github.terralian.fastmaj.paifu.source;

import java.io.InputStream;

/**
 * 表示单个牌谱的数据源，可以是URI，File，或者InputStream，String等
 * <p>
 * 该接口的设计类似于IO流，使用装饰器模式，通过不同实现类的组合达到解析不同来源牌谱到字符串流的能力。
 * <p/>
 * 若需要进行多个牌谱的ZIP压缩包解析、或者文件夹下所有牌谱解析，需要通过单个遍历执行.
 *
 * @author Terra.Lian
 */
public interface IPaifuSource {

    /**
     * 获取牌谱的文件内容
     */
    InputStream getInputStream() throws Exception;
}
