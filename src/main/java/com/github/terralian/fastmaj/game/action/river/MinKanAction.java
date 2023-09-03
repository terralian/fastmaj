package com.github.terralian.fastmaj.game.action.river;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.IGameEventQueue;
import com.github.terralian.fastmaj.game.event.DrawEvent;
import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.river.RiverActionEvent;
import com.github.terralian.fastmaj.game.event.system.NextDoraEvent;
import com.github.terralian.fastmaj.game.option.DoraAddRule;
import com.github.terralian.fastmaj.yama.DrawFrom;

/**
 * 明杠动作
 *
 * @author terra.lian
 */
public class MinKanAction implements IRiverAction {

    @Override
    public void doAction(RiverActionEvent value, GameConfig gameConfig, IGameCore gameCore) {
        if (!gameCore.getTehai(value.getPosition()).canMinkan(value.getFromHai())) {
            throw new IllegalArgumentException("手牌不可基于参数牌操作明杠：" + value.getFromHai());
        }
        gameCore.minkan(value.getPosition(), value.getFrom());
    }

    @Override
    public RiverActionType getEventType() {
        return RiverActionType.MINKAN;
    }

    @Override
    public void handle(GameEvent gameEvent, IGameCore gameCore, GameConfig gameConfig, IGameEventQueue eventQueue) {
        RiverActionEvent riverActionEvent = (RiverActionEvent) gameEvent;
        int position = riverActionEvent.getPosition();
        gameCore.switchPlayer(position);
        doAction(riverActionEvent, gameConfig, gameCore);

        DoraAddRule doraAddRule = gameConfig.getNewDoraByMinkanRule();
        // 先宝牌规则下，翻一张新宝牌
        if (doraAddRule == DoraAddRule.BEFORE_DRAW) {
            eventQueue.addNormal(new NextDoraEvent());
        }
        // 请求摸牌动作
        eventQueue.addNormal(new DrawEvent(position, DrawFrom.RINSYAN, riverActionEvent));
        // 后宝牌规则下，翻一张新宝牌
        if (doraAddRule == DoraAddRule.AFTER_KIRI) {
            eventQueue.addDelay(new NextDoraEvent(), 2);
        }
    }
}
