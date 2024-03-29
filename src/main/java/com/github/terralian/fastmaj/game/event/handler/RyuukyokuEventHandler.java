package com.github.terralian.fastmaj.game.event.handler;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.IGameEventQueue;
import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.system.GameEndCheckEvent;
import com.github.terralian.fastmaj.game.event.system.RyuukyokuEvent;
import com.github.terralian.fastmaj.game.event.system.SystemEventType;

/**
 * @author Terra.Lian
 */
public class RyuukyokuEventHandler implements ISystemGameEventHandler {

    @Override
    public SystemEventType getEventType() {
        return SystemEventType.RYUUKYOKU;
    }

    @Override
    public void handle(GameEvent gameEvent, IGameCore gameCore, GameConfig gameConfig, IGameEventQueue eventQueue) {
        RyuukyokuEvent ryuukyokuEvent = (RyuukyokuEvent) gameEvent;
        ryuukyokuEvent.getRyuukyokuResolver().execute(gameConfig, gameCore);
        // 发起游戏结束校验事件
        eventQueue.addPriority(new GameEndCheckEvent());
    }
}
