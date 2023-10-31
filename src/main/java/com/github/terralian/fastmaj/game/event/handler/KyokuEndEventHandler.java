package com.github.terralian.fastmaj.game.event.handler;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.IGameEventQueue;
import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.system.GameEndCheckEvent;
import com.github.terralian.fastmaj.game.event.system.SystemEventType;

/**
 * @author terra.lian
 */
public class KyokuEndEventHandler implements ISystemGameEventHandler {

    @Override
    public SystemEventType getEventType() {
        return SystemEventType.KYOKU_END;
    }

    @Override
    public void handle(GameEvent gameEvent, IGameCore gameCore, GameConfig gameConfig, IGameEventQueue eventQueue) {
        gameCore.endKyoku();
        // 发起游戏结束校验事件
        eventQueue.addPriority(new GameEndCheckEvent());
    }
}
