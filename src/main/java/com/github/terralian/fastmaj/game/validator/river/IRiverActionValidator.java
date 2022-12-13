package com.github.terralian.fastmaj.game.validator.river;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.action.river.IRiverAction;
import com.github.terralian.fastmaj.game.action.river.RiverActionType;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionValue;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.game.validator.IPlayerActionValidator;

/**
 * 计算是否可以执行将牌加入到手牌的动作
 * 
 * @author terra.lian
 * @see IRiverAction
 */
public interface IRiverActionValidator extends IPlayerActionValidator {

    /**
     * 计算某个是否可以执行该动作
     * 
     * @param position 玩家
     * @param rivalTehaiAction 对手的手牌动作
     * @param gameConfig 游戏配置
     * @param gameCore 游戏核心
     * @param context 玩家游戏上下文
     */
    boolean resolveAction(int position, TehaiActionValue rivalTehaiAction, GameConfig gameConfig, IGameCore gameCore,
            PlayerGameContext context);

    /**
     * 获取牌河动作类型
     */
    RiverActionType getRiverActionType();
}
