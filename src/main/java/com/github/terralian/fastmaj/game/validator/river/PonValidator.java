package com.github.terralian.fastmaj.game.validator.river;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.action.river.RiverActionType;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;
import com.github.terralian.fastmaj.tehai.ITehai;

/**
 * 计算碰动作是否可执行
 *
 * @author terra.lian
 */
public class PonValidator implements IRiverActionValidator {

    @Override
    public boolean resolveAction(
            int position, TehaiActionEvent rivalTehaiAction, GameConfig gameConfig, IGameCore gameCore,
            PlayerGameContext context
    ) {
        // 需要牌山还有可摸的牌
        // 玩家未立直
        // 宝牌数需要小于5，即没有人操作开四杠
        if (gameCore.getYamaCountdown() <= 0 || gameCore.getHaiRiver(position).isReach() || gameCore.getDoraDisplays()
                .size() >= 5) {
            return false;
        }
        // 手牌能够操作碰
        ITehai tehai = gameCore.getTehai(position);
        return tehai.canPon(rivalTehaiAction.getIfHai());
    }

    @Override
    public RiverActionType getRiverActionType() {
        return RiverActionType.PON;
    }
}
