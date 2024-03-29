package com.github.terralian.fastmaj.game.action.tehai;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.IGameEventQueue;
import com.github.terralian.fastmaj.game.KyokuState;
import com.github.terralian.fastmaj.game.event.DrawEvent;
import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.system.BreakSameJunEvent;
import com.github.terralian.fastmaj.game.event.system.RiverActionRequestEvent;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;
import com.github.terralian.fastmaj.yama.DrawFrom;

/**
 * 拔北动作
 *
 * @author terra.lian
 */
public class KitaAction implements ITehaiAction {

    @Override
    public KyokuState doAction(TehaiActionEvent actionParam, IGameCore gameCore, GameConfig gameOptions) {
        if (!gameCore.getTehai(actionParam.getPosition()).canKita()) {
            throw new IllegalArgumentException("手牌不能操作拔北");
        }
        gameCore.kita(actionParam.getPosition(), actionParam.getIfHai());
        return KyokuState.CONTINUE;
    }

    @Override
    public TehaiActionType getEventType() {
        return TehaiActionType.KITA;
    }

    @Override
    public void handle(GameEvent gameEvent, IGameCore gameCore, GameConfig gameConfig, IGameEventQueue eventQueue) {
        TehaiActionEvent tehaiActionEvent = (TehaiActionEvent) gameEvent;
        int position = tehaiActionEvent.getPosition();

        // 进行一次荣和动作请求
        eventQueue.addNormal(new RiverActionRequestEvent(tehaiActionEvent));
        // 破坏同巡标识
        eventQueue.addNormal(new BreakSameJunEvent());
        // 新摸牌事件
        eventQueue.addNormal(new DrawEvent(position, DrawFrom.RINSYAN, tehaiActionEvent));
    }
}
