package com.github.terralian.fastmaj.paifu;

import com.github.terralian.fastmaj.paifu.domain.PaifuGame;

/**
 * 牌谱解析器工厂接口，提供可便捷构建牌谱解析器的方式
 *
 * @author terra.lian
 * @see IPaifuParser
 * @see PaifuGame
 */
public interface IPaifuParserFactory {

    /**
     * 创建不同平台的默认牌谱解析器，将牌谱解析为{@link PaifuGame}
     */
    IPaifuParser<PaifuGame> createParser();
}
