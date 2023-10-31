package com.github.terralian.fastmaj.game;

import com.github.terralian.fastmaj.game.event.ActionEvent;
import com.github.terralian.fastmaj.game.event.GameEvent;

/**
 * 使用事件驱动的麻将游戏，在{@link #isGameEnd()}之前，循环执行{@link #nextEvent()}或者{@link #nextActionEvent()}
 * 即可一步步推进游戏进行。
 *
 * @author terra.lian
 * @see EventMajongGame
 * @see EventStreamMajongGame
 */
public interface IEventMajongGame extends IMajongGame {

    /**
     * 游戏是否结束
     */
    boolean isGameEnd();

    /**
     * 执行游戏的下一步，并返回被执行的事件
     */
    GameEvent nextEvent();

    /**
     * 执行到下一个动作事件，跳过系统事件，并返回被执行的事件
     */
    ActionEvent nextActionEvent();
}
