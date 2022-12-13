package com.github.terralian.fastmaj.game.validator.tehai;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.tehai.ITehai;

/**
 * 加杠动作可执行判断
 * 
 * @author terra.lian
 */
public class KakanValidator implements ITehaiActionValidator {

    @Override
    public boolean resolveAction(int position, GameConfig gameConfig, IGameCore gameCore) {
        // 需要牌山还有可摸的牌
        // 宝牌数需要小于5，即没有人操作开四杠
        // 立直时不会存在暗杠的情况
        if (gameCore.getYamaCountdown() <= 0 || gameCore.getDoraDisplays().size() >= 5 || gameCore.getHaiRiver().isReach()) {
            return false;
        }
        ITehai tehai = gameCore.getTehai(position);
        return tehai.canKakan();
    }

    @Override
    public TehaiActionType getTehaiActionType() {
        return TehaiActionType.KAKAN;
    }
}
