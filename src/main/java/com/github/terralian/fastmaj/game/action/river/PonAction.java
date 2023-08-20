package com.github.terralian.fastmaj.game.action.river;

import java.util.Arrays;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.IGameEventQueue;
import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.GameEventCode;
import com.github.terralian.fastmaj.game.event.handler.IGameEventHandler;
import com.github.terralian.fastmaj.game.event.river.PonEvent;
import com.github.terralian.fastmaj.game.event.river.RiverActionEvent;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionRequestEvent;
import com.github.terralian.fastmaj.hai.IHai;

/**
 * 【动作】碰， 从对手牌河碰一枚牌
 *
 * @author terra.lian
 */
public class PonAction implements IRiverAction, IGameEventHandler {

    @Override
    public void doAction(RiverActionEvent value, GameConfig gameConfig, IGameCore gameCore) {
        PonEvent ponEvent = (PonEvent) value;
        if (!gameCore.getTehai().canPon(ponEvent.getFromHai())) {
            throw new IllegalArgumentException("手牌不可基于参数牌操作碰：" + ponEvent.getFromHai());
        }
        gameCore.pon(ponEvent.getFrom(), Arrays.stream(ponEvent.getSelfHais()).anyMatch(IHai::isRedDora));
    }

    @Override
    public RiverActionType getRiverActionType() {
        return RiverActionType.PON;
    }

    @Override
    public int handleEventCode() {
        return GameEventCode.PON;
    }

    @Override
    public void handle(GameEvent gameEvent, IGameCore gameCore, GameConfig gameConfig, IGameEventQueue eventQueue) {
        RiverActionEvent riverActionEvent = (RiverActionEvent) gameEvent;
        int position = riverActionEvent.getPosition();
        gameCore.switchPlayer(position);
        doAction(riverActionEvent, gameConfig, gameCore);

        // 请求一次切牌动作
        eventQueue.addNormal(new TehaiActionRequestEvent(position, riverActionEvent));
    }
}

