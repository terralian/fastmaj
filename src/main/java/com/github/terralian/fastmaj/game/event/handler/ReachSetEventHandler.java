package com.github.terralian.fastmaj.game.event.handler;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.IGameEventQueue;
import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.GameEventCode;
import com.github.terralian.fastmaj.game.event.system.ReachSetEvent;

/**
 * @author Terra.Lian
 */
public class ReachSetEventHandler implements IGameEventHandler {

    @Override
    public int handleEventCode() {
        return GameEventCode.REACH_SET;
    }

    @Override
    public void handle(GameEvent gameEvent, IGameCore gameCore, GameConfig gameConfig, IGameEventQueue eventQueue) {
        ReachSetEvent reachSetEvent = (ReachSetEvent) gameEvent;
        // 放置立直棒
        int[] increaseAndDecrease = gameCore.getPlayerPoints();
        increaseAndDecrease[reachSetEvent.getPosition()] -= 1000;
        gameCore.reach2(increaseAndDecrease);
    }
}
