package com.github.terralian.fastmaj.paifu.source;

import java.io.InputStream;

/**
 * 表示单个牌谱的数据源，可以是URI，File，或者InputStream，String等
 * <p>
 * 该接口本质上就是套娃IO流，但是基于更方便扩展的目的而进行了封装。
 * 相较于原始IO流，使用者只需根据不同来源的牌谱选择最小的必须实现进行嵌套即可。
 * <p/>
 * 若需要进行多个牌谱的ZIP压缩包解析、或者文件夹下所有牌谱解析，需要通过单个遍历执行.
 *
 * @author terra.lian
 */
public interface IPaifuSource {

    /**
     * 获取牌谱的文件内容
     */
    InputStream getInputStream() throws Exception;
}
