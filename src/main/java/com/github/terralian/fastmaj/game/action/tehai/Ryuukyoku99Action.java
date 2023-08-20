package com.github.terralian.fastmaj.game.action.tehai;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.IGameEventQueue;
import com.github.terralian.fastmaj.game.KyokuState;
import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.GameEventCode;
import com.github.terralian.fastmaj.game.event.handler.IGameEventHandler;
import com.github.terralian.fastmaj.game.event.system.CommonSystemEventPool;
import com.github.terralian.fastmaj.game.event.system.SystemEventCode;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;
import com.github.terralian.fastmaj.game.ryuuky.IHalfwayRyuukyoku;

/**
 * 九种九牌流局动作
 *
 * @author terra.lian
 * @since 2022-10-14
 */
public class Ryuukyoku99Action implements ITehaiAction, IHalfwayRyuukyoku, IGameEventHandler {

    @Override
    public KyokuState doAction(TehaiActionEvent actionParam, IGameCore gameCore, GameConfig gameOptions) {
        gameCore.ryuukyoku(this, gameCore.getPlayerPoints());
        // 连庄
        gameCore.setRenchan(true, false);
        return KyokuState.END;
    }

    @Override
    public TehaiActionType getTehaiActionType() {
        return TehaiActionType.RYUUKYOKU;
    }

    @Override
    public String getRyuukyokuName() {
        return "九种九牌";
    }

    @Override
    public int handleEventCode() {
        return GameEventCode.RYUUKYOKU99;
    }

    @Override
    public void handle(GameEvent gameEvent, IGameCore gameCore, GameConfig gameConfig, IGameEventQueue eventQueue) {
        // 产生对局结束优先事件
        eventQueue.addPriority(CommonSystemEventPool.get(SystemEventCode.KYOKU_END));
    }
}
