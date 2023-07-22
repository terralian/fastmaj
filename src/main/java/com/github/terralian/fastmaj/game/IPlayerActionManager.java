package com.github.terralian.fastmaj.game;

import java.util.Set;

import com.github.terralian.fastmaj.game.action.IPlayerAction;
import com.github.terralian.fastmaj.game.action.river.IRiverAction;
import com.github.terralian.fastmaj.game.action.river.RiverActionType;
import com.github.terralian.fastmaj.game.action.tehai.ITehaiAction;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;
import com.github.terralian.fastmaj.game.validator.IPlayerActionValidator;
import com.github.terralian.fastmaj.game.validator.river.IRiverActionValidator;
import com.github.terralian.fastmaj.game.validator.tehai.ITehaiActionValidator;

/**
 * 玩家动作管理器，用于管理{@link IPlayerAction}及对应的{@link IPlayerActionValidator}
 *
 * @author terra.lian
 * @since 2022-10-25
 */
public interface IPlayerActionManager {

    /**
     * 根据动作类型获取手牌动作
     *
     * @param actionType 动作类型
     */
    ITehaiAction getTehaiAction(TehaiActionType actionType);

    /**
     * 根据动作类型获取手牌动作
     *
     * @param actionType 动作类型
     */
    ITehaiActionValidator getTehaiActionValidator(TehaiActionType actionType);

    /**
     * 校验哪些手牌动作可执行
     *
     * @param position 玩家坐席
     * @param gameConfig 游戏规则
     * @param gameCore 游戏核心
     */
    Set<TehaiActionType> validateTehaiActions(int position, GameConfig gameConfig, IGameCore gameCore);

    /**
     * 校验单个动作是否可执行
     *
     * @param actionType 动作类型
     * @param position 当前玩家
     * @param gameConfig 游戏规则
     * @param gameCore 游戏核心
     */
    boolean validateTehaiAction(TehaiActionType actionType, int position, GameConfig gameConfig, IGameCore gameCore);

    /**
     * 根据动作类型获取牌河动作
     *
     * @param actionType 动作类型
     */
    IRiverAction getRiverAction(RiverActionType actionType);

    /**
     * 根据动作类型获取牌河动作
     *
     * @param actionType 动作类型
     */
    IRiverActionValidator getRiverActionValidator(RiverActionType actionType);

    /**
     * 校验哪些牌河动作玩家可执行
     *
     * @param position 玩家
     * @param rivalTehaiAction 对手的手牌动作
     * @param gameConfig 游戏配置
     * @param gameCore 游戏核心
     * @param context 玩家游戏上下文
     */
    Set<RiverActionType> validateRiverActions(int position, TehaiActionEvent rivalTehaiAction, GameConfig gameConfig, IGameCore gameCore,
                                              PlayerGameContext context);

    /**
     * 校验单个动作是否可执行
     *
     * @param actionType 动作类型
     * @param position 玩家
     * @param rivalTehaiAction 对手的手牌动作
     * @param gameConfig 游戏配置
     * @param gameCore 游戏核心
     * @param context 玩家游戏上下文
     */
    boolean validateRiverAction(RiverActionType actionType, int position, TehaiActionEvent rivalTehaiAction, GameConfig gameConfig,
                                IGameCore gameCore,
                                PlayerGameContext context);

    /**
     * 移除某个手牌动作
     *
     * @param actionType 动作类型
     */
    void remove(TehaiActionType actionType);

    /**
     * 移除某个牌河动作
     *
     * @param actionType 动作类型
     */
    void remove(RiverActionType actionType);
}
