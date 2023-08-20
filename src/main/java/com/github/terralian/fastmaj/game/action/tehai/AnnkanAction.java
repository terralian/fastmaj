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
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.yama.DrawFrom;

/**
 * 表示一个暗杠动作，执行该动作会对当前玩家的手牌进行暗杠
 *
 * @author terra.lian
 */
public class AnnkanAction implements ITehaiAction, IGameEventHandler {

    /**
     * 根据构建的暗杠动作信息，执行暗杠动作。
     *
     * @throws IllegalArgumentException 构建的暗杠牌不能够进行暗杠的情况，抛出该异常
     */
    @Override
    public KyokuState doAction(TehaiActionEvent actionParam, IGameCore gameCore, GameConfig gameOptions) {
        IHai annkanHai = actionParam.getIfHai();
        if (!gameCore.getTehai().canAnnkan(annkanHai)) {
            throw new IllegalArgumentException("手牌不可基于参数牌操作暗杠：" + annkanHai.toString());
        }
        gameCore.annkan(annkanHai);
        // 对局未结束
        return KyokuState.CONTINUE;
    }

    @Override
    public TehaiActionType getTehaiActionType() {
        return TehaiActionType.ANNKAN;
    }

    @Override
    public int handleEventCode() {
        return GameEventCode.ANNKAN;
    }

    @Override
    public void handle(GameEvent gameEvent, IGameCore gameCore, GameConfig gameConfig, IGameEventQueue eventQueue) {
        TehaiActionEvent tehaiActionEvent = (TehaiActionEvent) gameEvent;
        int position = tehaiActionEvent.getPosition();
        DoraAddRule doraAddRule = gameConfig.getNewDoraByAnnkanRule();

        // 进行一次荣和动作请求
        eventQueue.addNormal(new RiverActionRequestEvent(tehaiActionEvent));
        // 破坏同巡标识
        eventQueue.addNormal(CommonSystemEventPool.get(GameEventCode.BREAK_SAME_JUN));
        // 先宝牌规则下，翻一张新宝牌
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
