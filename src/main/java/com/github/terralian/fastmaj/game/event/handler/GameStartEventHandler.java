package com.github.terralian.fastmaj.game.event.handler;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.IGameEventQueue;
import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.system.KyokuStartEvent;
import com.github.terralian.fastmaj.game.event.system.SystemEventType;

/**
 * 游戏开始事件处理器
 *
 * @author terra.lian
 */
public class GameStartEventHandler implements ISystemGameEventHandler {

    @Override
    public SystemEventType getEventType() {
        return SystemEventType.GAME_START;
    }

    /**
     * 游戏开始处理：调用内核进行初始化、产生一个对局开始事件
     */
    @Override
    public void handle(GameEvent gameEvent, IGameCore gameCore, GameConfig gameConfig, IGameEventQueue eventQueue) {
        gameCore.startGame();
        eventQueue.addNormal(new KyokuStartEvent());
    }
}
