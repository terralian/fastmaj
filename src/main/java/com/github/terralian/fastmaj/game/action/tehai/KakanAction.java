package com.github.terralian.fastmaj.game.action.tehai;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.KyokuState;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;

/**
 * 加杠动作
 *
 * @author terra.lian
 */
public class KakanAction implements ITehaiAction {

    @Override
    public KyokuState doAction(TehaiActionEvent actionParam, IGameCore gameCore, GameConfig gameOptions) {
        if (!gameCore.getTehai(actionParam.getPosition()).canKakan(actionParam.getIfHai())) {
            throw new IllegalArgumentException("手牌不可基于参数牌操作加杠：" + actionParam.getIfHai());
        }
        gameCore.kakan(actionParam.getIfHai());
        return KyokuState.CONTINUE;
    }

    @Override
    public TehaiActionType getTehaiActionType() {
        return TehaiActionType.KAKAN;
    }
}
