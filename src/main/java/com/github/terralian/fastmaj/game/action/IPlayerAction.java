package com.github.terralian.fastmaj.game.action;

import com.github.terralian.fastmaj.game.action.river.IRiverAction;
import com.github.terralian.fastmaj.game.action.tehai.ITehaiAction;

/**
 * 游戏中所有由玩家交互选择执行的动作接口，如玩家的自摸荣和，摸牌弃牌等。
 * 玩家动作包含几种大类别，在实际游戏内会使用更加细分的类型，而非该{@link IPlayerAction}接口，看:
 * <ul>
 * <li>{@link IRiverAction} 牌流入手牌的动作
 * <li>{@link ITehaiAction} 牌流出手牌的动作
 * </ul>
 * 
 * @author terra.lian
 * @see IRiverAction
 * @see ITehaiAction
 */
public interface IPlayerAction extends IGameAction {

}
