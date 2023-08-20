package com.github.terralian.fastmaj.game.event.handler;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.IGameEventQueue;
import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.GameEventCode;
import com.github.terralian.fastmaj.game.event.system.CommonSystemEventPool;

/**
 * 游戏开始事件处理器
 *
 * @author Terra.Lian
 */
public class GameStartEventHandler implements IGameEventHandler {

    @Override
    public int handleEventCode() {
        return GameEventCode.GAME_START;
    }

    /**
     * 游戏开始处理：调用内核进行初始化、产生一个对局开始事件
     */
    @Override
    public void handle(GameEvent gameEvent, IGameCore gameCore, GameConfig gameConfig, IGameEventQueue eventQueue) {
        gameCore.startGame();
        eventQueue.addNormal(CommonSystemEventPool.get(GameEventCode.KYOKU_START));
    }
}
