package com.github.terralian.fastmaj.game.action.river;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;

/**
 * 明杠动作
 * 
 * @author terra.lian
 */
public class MinKanAction implements IRiverAction {

    public void doAction(RiverActionValue value, GameConfig gameConfig, IGameCore gameCore) {
        if (!gameCore.getTehai().canMinkan(value.getActionHai())) {
            throw new IllegalArgumentException("手牌不可基于参数牌操作明杠：" + value.getActionHai());
        }
        gameCore.minkan(value.getFromPlayer());
    }

    @Override
    public RiverActionType getRiverActionType() {
        return RiverActionType.MINKAN;
    }
}
