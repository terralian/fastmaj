package com.github.terralian.fastmaj.game.event.handler;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.IGameEventQueue;
import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.system.ReachSetEvent;
import com.github.terralian.fastmaj.game.event.system.SystemEventType;

/**
 * @author terra.lian
 */
public class ReachSetEventHandler implements ISystemGameEventHandler {

    @Override
    public SystemEventType getEventType() {
        return SystemEventType.REACH_SET;
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
