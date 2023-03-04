package com.github.terralian.fastmaj.game.validator.tehai;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.PlayerHideStatus;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;

/**
 * 立直动作判定器
 * 
 * @author terra.lian
 */
public class ReachValidator implements ITehaiActionValidator {

    @Override
    public boolean resolveAction(int position, GameConfig gameConfig, IGameCore gameCore) {
        PlayerHideStatus playerHide = gameCore.getPlayerHide(position);
        return !gameCore.getReachs().get(position) && playerHide.getSyaten() <= 0;
    }

    @Override
    public TehaiActionType getTehaiActionType() {
        return TehaiActionType.REACH;
    }
}
