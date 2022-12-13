package com.github.terralian.fastmaj.game.action.river;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;

/**
 * 【动作】吃，从上家吃一枚牌
 * 
 * @author terra.lian
 */
public class ChiiAction implements IRiverAction {

    @Override
    public void doAction(RiverActionValue value, GameConfig gameConfig, IGameCore gameCore) {
        IHai[] selfHais = value.getSelfHais();
        ITehai tehai = gameCore.getTehai(value.getActionPlayer());
        if (!tehai.canChii(value.getActionHai())) {
            throw new IllegalArgumentException("手牌不可基于参数牌操作吃：" + value.getActionHai());
        }
        // 判断搭子牌是否存在
        if (!tehai.canKiri(selfHais[0]) || !tehai.canKiri(selfHais[1])) {
            throw new IllegalArgumentException("参数错误，不能操作");
        }
        gameCore.chii(value.getFromPlayer(), selfHais[0], selfHais[1]);
    }

    @Override
    public RiverActionType getRiverActionType() {
        return RiverActionType.CHII;
    }
}
