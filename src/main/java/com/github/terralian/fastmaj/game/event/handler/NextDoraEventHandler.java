package com.github.terralian.fastmaj.game.event.handler;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.IGameEventQueue;
import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.system.SystemEventType;

/**
 * @author terra.lian
 */
public class NextDoraEventHandler implements ISystemGameEventHandler {

    @Override
    public SystemEventType getEventType() {
        return SystemEventType.NEXT_DORA;
    }

    @Override
    public void handle(GameEvent gameEvent, IGameCore gameCore, GameConfig gameConfig, IGameEventQueue eventQueue) {
        gameCore.nextDoraDisplay();
    }
}
