package com.github.terralian.fastmaj.paifu;

import com.github.terralian.fastmaj.paifu.source.IPaifuSource;

/**
 * 游戏牌谱解析器，用于解析游戏牌谱文件，该接口会将解析的内容调用对应的分析器
 *
 * @author terra.lian
 */
public interface IPaifuParser<T> {

    /**
     * 解析数据源到实体
     *
     * @param source 数据源
     * @throws Exception IO异常或者解析异常
     */
    T parse(IPaifuSource source) throws Exception;
}
