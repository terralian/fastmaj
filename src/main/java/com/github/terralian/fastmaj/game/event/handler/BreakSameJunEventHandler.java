package com.github.terralian.fastmaj.game.event.handler;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.IGameEventQueue;
import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.GameEventCode;

/**
 * 破坏同巡状态事件处理器
 *
 * @author Terra.Lian
 */
public class BreakSameJunEventHandler implements IGameEventHandler {

    @Override
    public int handleEventCode() {
        return GameEventCode.BREAK_SAME_JUN;
    }

    /**
     * 移除同巡标识
     *
     * @param gameEvent 事件（实现类）
     * @param gameCore 游戏内核
     * @param gameConfig 游戏配置
     * @param eventQueue 事件队列
     */
    @Override
    public void handle(GameEvent gameEvent, IGameCore gameCore, GameConfig gameConfig, IGameEventQueue eventQueue) {
        gameCore.breakSameJun();
    }
}
