package com.github.terralian.fastmaj.game.action.tehai;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.KyokuState;
import com.github.terralian.fastmaj.game.ryuuky.IHalfwayRyuukyoku;

/**
 * 九种九牌流局动作
 * 
 * @author terra.lian
 * @since 2022-10-14
 */
public class Ryuukyoku99Action implements ITehaiAction, IHalfwayRyuukyoku {

    @Override
    public KyokuState doAction(TehaiActionValue actionParam, IGameCore gameCore, GameConfig gameOptions) {
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
}
