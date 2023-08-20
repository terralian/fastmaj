package com.github.terralian.fastmaj.game.action.tehai;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.IGameEventQueue;
import com.github.terralian.fastmaj.game.KyokuState;
import com.github.terralian.fastmaj.game.event.DrawEvent;
import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.GameEventCode;
import com.github.terralian.fastmaj.game.event.handler.IGameEventHandler;
import com.github.terralian.fastmaj.game.event.river.RiverActionRequestEvent;
import com.github.terralian.fastmaj.game.event.system.CommonSystemEventPool;
import com.github.terralian.fastmaj.game.event.system.NextDoraEvent;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;
import com.github.terralian.fastmaj.game.option.DoraAddRule;
import com.github.terralian.fastmaj.yama.DrawFrom;

/**
 * 加杠动作
 *
 * @author terra.lian
 */
public class KakanAction implements ITehaiAction, IGameEventHandler {

    @Override
    public KyokuState doAction(TehaiActionEvent actionParam, IGameCore gameCore, GameConfig gameOptions) {
        if (!gameCore.getTehai(actionParam.getPosition()).canKakan(actionParam.getIfHai())) {
            throw new IllegalArgumentException("手牌不可基于参数牌操作加杠：" + actionParam.getIfHai());
        }
        gameCore.kakan(actionParam.getIfHai());
        return KyokuState.CONTINUE;
    }

    @Override
    public TehaiActionType getTehaiActionType() {
        return TehaiActionType.KAKAN;
    }

    @Override
    public int handleEventCode() {
        return GameEventCode.KAKAN;
    }

    @Override
    public void handle(GameEvent gameEvent, IGameCore gameCore, GameConfig gameConfig, IGameEventQueue eventQueue) {
        TehaiActionEvent tehaiActionEvent = (TehaiActionEvent) gameEvent;
        int position = tehaiActionEvent.getPosition();
        DoraAddRule doraAddRule = gameConfig.getNewDoraByKakanRule();

        // 进行一次荣和动作请求
        eventQueue.addNormal(new RiverActionRequestEvent(tehaiActionEvent));
        // 破坏同巡标识
        eventQueue.addNormal(CommonSystemEventPool.get(GameEventCode.BREAK_SAME_JUN));
        // 先新宝牌规则下，翻一张新宝牌
        if (doraAddRule == DoraAddRule.BEFORE_DRAW) {
            eventQueue.addNormal(new NextDoraEvent());
        }
        // 新摸牌事件
        eventQueue.addNormal(new DrawEvent(position, DrawFrom.RINSYAN, tehaiActionEvent));
        // 后宝牌规则下，翻一张新宝牌
        if (doraAddRule == DoraAddRule.AFTER_KIRI) {
            // 前面的动作：荣和、破一发、新摸牌、手牌动作
            eventQueue.addDelay(new NextDoraEvent(), 4);
        }
    }
}
