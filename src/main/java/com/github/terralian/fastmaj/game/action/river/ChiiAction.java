package com.github.terralian.fastmaj.game.action.river;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.IGameEventQueue;
import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.river.ChiiEvent;
import com.github.terralian.fastmaj.game.event.river.RiverActionEvent;
import com.github.terralian.fastmaj.game.event.system.TehaiActionRequestEvent;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;

/**
 * 【动作】吃，从上家吃一枚牌
 *
 * @author terra.lian
 */
public class ChiiAction implements IRiverAction {

    @Override
    public void doAction(RiverActionEvent value, GameConfig gameConfig, IGameCore gameCore) {
        ChiiEvent chiiEvent = (ChiiEvent) value;
        IHai[] selfHais = chiiEvent.getSelfHais();
        ITehai tehai = gameCore.getTehai(chiiEvent.getPosition());
        if (!tehai.canChii(chiiEvent.getFromHai())) {
            throw new IllegalArgumentException("手牌不可基于参数牌操作吃：" + chiiEvent.getFromHai());
        }
        // 判断搭子牌是否存在
        if (!tehai.canKiri(selfHais[0]) || !tehai.canKiri(selfHais[1])) {
            throw new IllegalArgumentException("参数错误，不能操作");
        }
        gameCore.chii(chiiEvent.getPosition(), chiiEvent.getFrom(), selfHais[0], selfHais[1]);
    }

    @Override
    public RiverActionType getEventType() {
        return RiverActionType.CHII;
    }

    @Override
    public void handle(GameEvent gameEvent, IGameCore gameCore, GameConfig gameConfig, IGameEventQueue eventQueue) {
        RiverActionEvent riverActionEvent = (RiverActionEvent) gameEvent;
        doAction(riverActionEvent, gameConfig, gameCore);

        int position = riverActionEvent.getPosition();
        // 请求一次切牌动作
        eventQueue.addNormal(new TehaiActionRequestEvent(position, riverActionEvent));
    }
}
