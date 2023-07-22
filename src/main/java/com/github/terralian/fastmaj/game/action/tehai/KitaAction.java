package com.github.terralian.fastmaj.game.action.tehai;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.KyokuState;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;

/**
 * 拔北动作
 *
 * @author terra.lian
 */
public class KitaAction implements ITehaiAction {

    @Override
    public KyokuState doAction(TehaiActionEvent actionParam, IGameCore gameCore, GameConfig gameOptions) {
        if (!gameCore.getTehai().canKita()) {
            throw new IllegalArgumentException("手牌不能操作拔北");
        }
        gameCore.kita(actionParam.getIfHai());
        return KyokuState.CONTINUE;
    }

    @Override
    public TehaiActionType getTehaiActionType() {
        return TehaiActionType.KITA;
    }
}
