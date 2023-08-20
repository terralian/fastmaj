package com.github.terralian.fastmaj.game.event.handler;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.IGameEventQueue;
import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.GameEventCode;

/**
 * @author Terra.Lian
 */
public class NextDoraEventHandler implements IGameEventHandler {

    @Override
    public int handleEventCode() {
        return GameEventCode.NEXT_DORA_EVENT;
    }

    @Override
    public void handle(GameEvent gameEvent, IGameCore gameCore, GameConfig gameConfig, IGameEventQueue eventQueue) {
        gameCore.nextDoraDisplay();
    }
}
