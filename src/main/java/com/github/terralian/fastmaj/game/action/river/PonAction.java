package com.github.terralian.fastmaj.game.action.river;

import java.util.Arrays;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;

/**
 * 【动作】碰， 从对手牌河碰一枚牌
 * 
 * @author terra.lian
 */
public class PonAction implements IRiverAction {

    public void doAction(RiverActionValue value, GameConfig gameConfig, IGameCore gameCore) {
        if (!gameCore.getTehai().canPon(value.getActionHai())) {
            throw new IllegalArgumentException("手牌不可基于参数牌操作碰：" + value.getActionHai());
        }
        gameCore.pon(value.getFromPlayer(), Arrays.stream(value.getSelfHais()).anyMatch(k -> k.isRedDora()));
    }

    @Override
    public RiverActionType getRiverActionType() {
        return RiverActionType.PON;
    }
}

