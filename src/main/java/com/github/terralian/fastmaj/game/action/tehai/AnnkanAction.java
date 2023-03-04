package com.github.terralian.fastmaj.game.action.tehai;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.KyokuState;
import com.github.terralian.fastmaj.hai.IHai;

/**
 * 表示一个暗杠动作，执行该动作会对当前玩家的手牌进行暗杠
 * 
 * @author terra.lian
 */
public class AnnkanAction implements ITehaiAction {

    /**
     * 根据构建的暗杠动作信息，执行暗杠动作。
     * 
     * @throws IllegalArgumentException 构建的暗杠牌不能够进行暗杠的情况，抛出该异常
     */
    @Override
    public KyokuState doAction(TehaiActionValue actionParam, IGameCore gameCore, GameConfig gameOptions) {
        IHai annkanHai = actionParam.getActionHai();
        if (!gameCore.getTehai().canAnnkan(annkanHai)) {
            throw new IllegalArgumentException("手牌不可基于参数牌操作暗杠：" + annkanHai.toString());
        }
        gameCore.annkan(annkanHai);
        // 对局未结束
        return KyokuState.CONTINUE;
    }

    @Override
    public TehaiActionType getTehaiActionType() {
        return TehaiActionType.ANNKAN;
    }
}
