package com.github.terralian.fastmaj.game.action.tehai;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.KyokuState;

/**
 * 拔北动作
 * 
 * @author terra.lian
 */
public class KitaAction implements ITehaiAction {

    @Override
    public KyokuState doAction(TehaiActionValue actionParam, IGameCore gameCore, GameConfig gameOptions) {
        if (!gameCore.getTehai().canKita()) {
            throw new IllegalArgumentException("手牌不能操作拔北");
        }
        gameCore.kita(actionParam.getActionHai());
        return KyokuState.CONTINUE;
    }

    @Override
    public TehaiActionType getTehaiActionType() {
        return TehaiActionType.KITA;
    }
}
