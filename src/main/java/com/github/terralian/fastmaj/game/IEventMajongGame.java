package com.github.terralian.fastmaj.game;

import com.github.terralian.fastmaj.game.event.GameEvent;

/**
 * @author Terra.Lian
 */
public interface IEventMajongGame extends IMajongGame {

    /**
     * 游戏是否结束
     */
    boolean isGameEnd();

    /**
     * 执行游戏的下一步，并返回被执行的事件
     */
    GameEvent next();
}
