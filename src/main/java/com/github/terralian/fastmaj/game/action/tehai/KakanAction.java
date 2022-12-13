package com.github.terralian.fastmaj.game.action.tehai;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.KyokuState;

/**
 * 加杠动作
 * 
 * @author terra.lian
 */
public class KakanAction implements ITehaiAction {

    @Override
    public KyokuState doAction(TehaiActionValue actionParam, IGameCore gameCore, GameConfig gameOptions) {
        if (!gameCore.getTehai(actionParam.getActionPlayer()).canKakan(actionParam.getActionHai())) {
            throw new IllegalArgumentException("手牌不可基于参数牌操作加杠：" + actionParam.getActionHai());
        }
        gameCore.kakan(actionParam.getActionHai());
        return KyokuState.CONTINUE;
    }

    @Override
    public TehaiActionType getTehaiActionType() {
        return TehaiActionType.KAKAN;
    }
}
