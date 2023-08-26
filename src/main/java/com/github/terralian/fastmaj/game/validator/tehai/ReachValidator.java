package com.github.terralian.fastmaj.game.validator.tehai;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;

/**
 * 立直动作判定器
 *
 * @author terra.lian
 */
public class ReachValidator implements ITehaiActionValidator {

    @Override
    public boolean resolveAction(int position, GameConfig gameConfig, IGameCore gameCore) {
        return !gameCore.getReachs().get(position) && gameCore.getSyaten(position) <= 0;
    }

    @Override
    public TehaiActionType getTehaiActionType() {
        return TehaiActionType.REACH;
    }
}
