package com.github.terralian.fastmaj.game.action.river;

import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.player.RiverActionCall;

import lombok.Data;

@Data
public class RiverActionValue {

    /**
     * 动作类型
     */
    private RiverActionType actionType;
    /**
     * 操作的玩家
     */
    private int actionPlayer;

    /**
     * 操作的牌
     */
    private IHai actionHai;

    /**
     * 来源玩家
     */
    private int fromPlayer;

    /**
     * 自己的搭子
     */
    private IHai[] selfHais;

    /**
     * 排序
     */
    private int order;

    /**
     * 根据牌河处理动作请求创建{@link RiverActionValue}
     * 
     * @param drawHaiHandle 牌河处理动作请求
     * @param actionPlayer 动作的玩家
     * @param actionHai 动作的牌
     * @param fromPlayer 来源的玩家
     */
    public RiverActionValue(RiverActionCall drawHaiHandle, int actionPlayer, IHai actionHai, int fromPlayer) {
        this.actionType = drawHaiHandle.getActionType();
        this.selfHais = drawHaiHandle.getSelfHais();
        this.order = drawHaiHandle.getOrder();

        this.actionPlayer = actionPlayer;
        this.actionHai = actionHai;
        this.fromPlayer = fromPlayer;
    }

    /**
     * 是否是吃或者碰动作
     */
    public boolean isChiiOrPon() {
        return actionType == RiverActionType.CHII //
                || actionType == RiverActionType.PON;
    }

    /**
     * 是否是明杠动作
     */
    public boolean isMinkan() {
        return actionType == RiverActionType.MINKAN;
    }

    /**
     * 是否是牌河结束动作
     */
    public boolean isEndKyokuAction() {
        return actionType == RiverActionType.RON;
    }
}
