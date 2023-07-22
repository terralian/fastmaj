package com.github.terralian.fastmaj.game.action.tehai;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.KyokuState;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;

/**
 * 立直动作
 *
 * @author terra.lian
 */
public class ReachAction implements ITehaiAction {

    @Override
    public KyokuState doAction(TehaiActionEvent actionParam, IGameCore gameCore, GameConfig gameOptions) {
        if (!gameCore.getTehai().canKiri(actionParam.getIfHai())) {
            throw new IllegalArgumentException("参数错误，参数牌不能进行模切操作：" + actionParam.getIfHai());
        }
        gameCore.kiri(actionParam.getIfHai(), true);
        return KyokuState.CONTINUE;
    }

    @Override
    public TehaiActionType getTehaiActionType() {
        return TehaiActionType.REACH;
    }
}