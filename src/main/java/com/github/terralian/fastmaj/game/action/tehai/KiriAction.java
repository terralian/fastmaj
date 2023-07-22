package com.github.terralian.fastmaj.game.action.tehai;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.KyokuState;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;

/**
 * 一个弃牌动作
 *
 * @author terra.lian
 */
public class KiriAction implements ITehaiAction {

    @Override
    public KyokuState doAction(TehaiActionEvent actionParam, IGameCore gameCore, GameConfig gameOptions) {
        if (!gameCore.getTehai().canKiri(actionParam.getIfHai())) {
            throw new IllegalArgumentException("参数错误，玩家" + (actionParam.getPosition() + 1) //
                    + "的手牌不能进行模切操作：" + actionParam.getIfHai() + " 当前玩家手牌：" + gameCore.getTehai()
                    .getHand());
        }
        gameCore.kiri(actionParam.getIfHai(), false);
        return KyokuState.CONTINUE;
    }

    @Override
    public TehaiActionType getTehaiActionType() {
        return TehaiActionType.KIRI;
    }
}
