package com.github.terralian.fastmaj.player;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.github.terralian.fastmaj.game.action.river.RiverActionType;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.replay.TenhouQueueReplayPlayerBuilder;
import com.github.terralian.fastmaj.tehai.ITehai;

/**
 * 队列回放玩家实例，使用队列记录一个玩家在某个牌谱中的所有操作，可以在同一个种子的牌谱中复现所有操作。
 * 采用队列的先进先出机制，在遍历牌谱时向队列末尾添加操作，在实际回放中，从队列头取出操作执行。
 * 基于此，该实例的动作是不可逆的流，执行一次后需要进行重新初始化。
 * <p>
 * 为了处理牌谱中丢失的牌河跳过动作，一般生成器如{@link TenhouQueueReplayPlayerBuilder}会为每个弃牌对其他玩家设置{@link RiverActionCall#SKIP}动作.
 * 所以需要<b>开启<code>GameConfig.setForceRequestRiverAction(true)</code>设置</b>.这样才可以正常执行
 * 
 * @author terra.lian
 */
public class QueueReplayPlayer implements IPlayer {

    /**
     * 手牌动作队列
     */
    private List<Queue<TehaiActionCall>> tehaiActionCalls;
    /**
     * 牌河动作
     */
    private Map<Integer, Map<Integer, RiverActionCall>> riverActionCalls;

    /**
     * 初始化构建队列回放玩家{@link QueueReplayPlayer}
     */
    public QueueReplayPlayer() {
        tehaiActionCalls = new ArrayList<>();
        riverActionCalls = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TehaiActionCall drawHai(ITehai tehai, Set<TehaiActionType> enableActions, PlayerGameContext context) {
        Queue<TehaiActionCall> queue = tehaiActionCalls.get(context.getRound());
        return queue.poll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RiverActionCall nakiOrRon(int fromPosition, TehaiActionType fromDealAction, Set<RiverActionType> enableActions,
            PlayerGameContext context) {
        Map<Integer, RiverActionCall> roundActionMap = riverActionCalls.get(context.getRound());
        if (roundActionMap == null) {
            return RiverActionCall.SKIP;
        }
        return roundActionMap.get(context.getActionCount());
    }

    /**
     * 将一个手牌动作增加到动作队列末尾，后续会按先进先出使用。
     * 
     * @param action 动作
     */
    public void addTehaiAction(int round, TehaiActionCall action) {
        while (tehaiActionCalls.size() <= round) {
            tehaiActionCalls.add(new ArrayDeque<>());
        }
        Queue<TehaiActionCall> queue = tehaiActionCalls.get(round);
        queue.add(action);
    }

    /**
     * 将一个牌河动作增加到动作队列末尾，后续会按先进先出使用。
     * 
     * @param round 对局数
     * @param actionCount 动作数
     * @param action 动作
     */
    public void addRiverAction(int round, int actionCount, RiverActionCall action) {
        Map<Integer, RiverActionCall> roundActionMap = riverActionCalls.get(round);
        if (roundActionMap == null) {
            roundActionMap = new HashMap<>();
            riverActionCalls.put(round, roundActionMap);
        }
        roundActionMap.put(actionCount, action);
    }

    /**
     * 清空队列
     */
    public void clear() {
        tehaiActionCalls.clear();
        riverActionCalls.clear();
    }
}
