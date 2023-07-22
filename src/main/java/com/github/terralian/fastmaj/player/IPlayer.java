package com.github.terralian.fastmaj.player;

import java.util.Set;

import com.github.terralian.fastmaj.game.action.river.RiverActionType;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.game.event.river.RiverActionEvent;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;
import com.github.terralian.fastmaj.tehai.ITehai;

/**
 * 玩家和游戏关键的交互接口，一个麻将游戏中除可视画面外，所需玩家决策的只有2种情况，即摸牌后的手牌动作处理{@link TehaiActionEvent},
 * 及其他玩家手牌动作后，进行牌河动作处理{@link RiverActionCall}（鸣牌，荣和）两种。
 * <p/>
 * 目前版本中，由于玩家部分都是面向AI设计，而AI只需要在需要决策时，通过游戏上下文即可还原出先前其他人的所有动作。则这里不提供其他事件接口。
 *
 * @author terra.lian
 */
public interface IPlayer {

    /**
     * 手牌动作处理，玩家摸一枚牌后，需要执行打出一枚牌或者暗杠自摸等操作，这个动作必定不能为null
     *
     * @param tehai 手牌
     * @param enableActions 可用动作
     * @param context 玩家游戏上下文
     */
    TehaiActionEvent drawHai(ITehai tehai, Set<TehaiActionType> enableActions, PlayerGameContext context);

    /**
     * 鸣牌或者荣和，通过{@link RiverActionCall}创建操作，若跳过支持返回null，但是建议使用{@link RiverActionCall#SKIP}.
     * 每个动作可以使用{@link RiverActionCall}提供的工厂类进行构建
     *
     * @param fromPosition 从玩家
     * @param fromDealAction 玩家的动作
     * @param enableActions 我可执行的动作（必有一个）
     * @param context 玩家游戏上下文
     * @return null | {@link RiverActionCall}
     */
    RiverActionEvent nakiOrRon(int fromPosition, TehaiActionType fromDealAction, Set<RiverActionType> enableActions,
                               PlayerGameContext context);
}
