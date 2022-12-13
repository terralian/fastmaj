package com.github.terralian.fastmaj.game.builder;

import com.github.terralian.fastmaj.game.StreamMajongGame;

/**
 * {@link StreamMajongGame}构造者
 * 
 * @author terra.lian
 */
public abstract class MajongGameBuilder {

    /**
     * 使用默认的游戏构建器
     */
    public static DefaultGameBuilder withDefault() {
        return new DefaultGameBuilder();
    }
}
