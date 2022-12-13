package com.github.terralian.fastmaj.game.validator.tehai;

import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;

/**
 * 拔北动作判定器
 * 
 * @author terra.lian
 */
public class KitaValidator implements ITehaiActionValidator {

    @Override
    public boolean resolveAction(int position, GameConfig gameConfig, IGameCore gameCore) {
        // 仅三麻
        if (gameConfig.getPlayerSize() != 3) {
            return false;
        }
        // 手上需要有北，若立直，仅能够对刚入手的牌进行拔北
        ITehai tehai = gameCore.getTehai(position);
        IHai drawHai = tehai.getDrawHai();
        return gameCore.getReachs().get(position) ? Encode34.BEI == drawHai.getValue() : tehai.canKita();
    }

    @Override
    public TehaiActionType getTehaiActionType() {
        return TehaiActionType.KITA;
    }
}
