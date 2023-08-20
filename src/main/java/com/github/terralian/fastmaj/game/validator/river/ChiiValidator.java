package com.github.terralian.fastmaj.game.validator.river;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.action.river.RiverActionType;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;
import com.github.terralian.fastmaj.player.RivalEnum;
import com.github.terralian.fastmaj.tehai.ITehai;

/**
 * 计算吃动作是否可执行
 *
 * @author terra.lian
 * @since 2022-10-14
 */
public class ChiiValidator implements IRiverActionValidator {

    @Override
    public boolean resolveAction(int position, TehaiActionEvent rivalTehaiAction, GameConfig gameConfig,
            IGameCore gameCore,
            PlayerGameContext context) {
        // 需要牌山还有可摸的牌
        // 玩家未立直
        if (gameCore.getYamaCountdown() <= 0 || gameCore.getHaiRiver(position).isReach()) {
            return false;
        }
        // 对手的动作需要是模切或者立直
        if (rivalTehaiAction.getEventType() != TehaiActionType.KIRI && rivalTehaiAction.getEventType() != TehaiActionType.REACH) {
            return false;
        }
        // 需要是上家，且手牌有对应的搭子
        ITehai tehai = gameCore.getTehai(position);
        return RivalEnum.calc(position, rivalTehaiAction.getPosition()) == RivalEnum.TOP
                && tehai.canChii(rivalTehaiAction.getIfHai());
    }

    @Override
    public RiverActionType getRiverActionType() {
        return RiverActionType.CHII;
    }

}
