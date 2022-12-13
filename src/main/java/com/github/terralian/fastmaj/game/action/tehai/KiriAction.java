package com.github.terralian.fastmaj.game.action.tehai;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.KyokuState;

/**
 * 一个弃牌动作
 * 
 * @author terra.lian
 */
public class KiriAction implements ITehaiAction {

    @Override
    public KyokuState doAction(TehaiActionValue actionParam, IGameCore gameCore, GameConfig gameOptions) {
        if (!gameCore.getTehai().canKiri(actionParam.getActionHai())) {
            throw new IllegalArgumentException("参数错误，玩家" + (actionParam.getActionPlayer() + 1) //
                    + "的手牌不能进行模切操作：" + actionParam.getActionHai());
        }
        gameCore.kiri(actionParam.getActionHai(), false);
        return KyokuState.CONTINUE;
    }

    @Override
    public TehaiActionType getTehaiActionType() {
        return TehaiActionType.KIRI;
    }
}
