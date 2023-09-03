package com.github.terralian.fastmaj.game.validator.tehai;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;

/**
 * 自摸动作可执行判断
 *
 * @author terra.lian
 */
public class TsumoValidator implements ITehaiActionValidator {

    @Override
    public boolean resolveAction(int position, GameConfig gameConfig, IGameCore gameCore) {
        // 只需要手牌已经完成和了动作
        // 若存在番缚，这个还需要增加校验（或者写个新类），后面再考虑 TODO
        return gameCore.getSyaten(position) < 0;
    }

    @Override
    public TehaiActionType getTehaiActionType() {
        return TehaiActionType.TSUMO;
    }
}
