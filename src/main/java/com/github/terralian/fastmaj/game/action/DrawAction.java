package com.github.terralian.fastmaj.game.action;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.IGameEventQueue;
import com.github.terralian.fastmaj.game.event.DrawEvent;
import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.handler.ISystemGameEventHandler;
import com.github.terralian.fastmaj.game.event.system.SystemEventType;
import com.github.terralian.fastmaj.game.event.system.TehaiActionRequestEvent;

/**
 * 摸牌动作，从牌山或者王牌区摸一枚牌。
 * <p/>
 * 该动作由程序自动执行
 *
 * @author terra.lian
 */
public class DrawAction implements IGameAction, ISystemGameEventHandler {

    @Override
    public SystemEventType getEventType() {
        return SystemEventType.DRAW;
    }

    /**
     * 摸牌事件处理：根据事件执行摸牌动作，并产生玩家手牌请求动作
     */
    @Override
    public void handle(GameEvent gameEvent, IGameCore gameCore, GameConfig gameConfig, IGameEventQueue eventQueue) {
        DrawEvent drawEvent = (DrawEvent) gameEvent;
        gameCore.switchPlayer(drawEvent.getPosition());
        gameCore.draw(drawEvent.getPosition(), drawEvent.getDrawFrom());
        eventQueue.addNormal(new TehaiActionRequestEvent(drawEvent.getPosition(), drawEvent));
    }
}
