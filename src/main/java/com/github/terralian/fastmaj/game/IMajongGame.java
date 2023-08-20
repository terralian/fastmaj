package com.github.terralian.fastmaj.game;

/**
 * 麻将游戏
 *
 * @author Terra.Lian
 */
public interface IMajongGame {

    /**
     * 开始一局游戏
     */
    void startGame();

    /**
     * 获取游戏核心
     */
    IGameCore getGameCore();
}
