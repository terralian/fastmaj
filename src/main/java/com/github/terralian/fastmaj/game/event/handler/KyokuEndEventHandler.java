package com.github.terralian.fastmaj.game.event.handler;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.IGameEventQueue;
import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.GameEventCode;
import com.github.terralian.fastmaj.game.event.system.CommonSystemEventPool;

/**
 * @author Terra.Lian
 */
public class KyokuEndEventHandler implements IGameEventHandler {

    @Override
    public int handleEventCode() {
        return GameEventCode.KYOKU_END;
    }

    @Override
    public void handle(GameEvent gameEvent, IGameCore gameCore, GameConfig gameConfig, IGameEventQueue eventQueue) {
        gameCore.endKyoku();
        // 发起游戏结束校验事件
        eventQueue.addPriority(CommonSystemEventPool.get(GameEventCode.GAME_END_CHECK));
    }
}
