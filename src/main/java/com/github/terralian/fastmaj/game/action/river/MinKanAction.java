package com.github.terralian.fastmaj.game.action.river;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.event.river.RiverActionEvent;

/**
 * 明杠动作
 *
 * @author terra.lian
 */
public class MinKanAction implements IRiverAction {

    public void doAction(RiverActionEvent value, GameConfig gameConfig, IGameCore gameCore) {
        if (!gameCore.getTehai().canMinkan(value.getFromHai())) {
            throw new IllegalArgumentException("手牌不可基于参数牌操作明杠：" + value.getFromHai());
        }
        gameCore.minkan(value.getFrom());
    }

    @Override
    public RiverActionType getRiverActionType() {
        return RiverActionType.MINKAN;
    }
}
