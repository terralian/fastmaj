package com.github.terralian.fastmaj.player;

import com.github.terralian.fastmaj.game.action.river.RiverActionType;
import com.github.terralian.fastmaj.hai.IHai;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 牌河处理动作请求参数，如鸣牌荣和等，该类用于存储玩家所选动作的请求参数返回给游戏。
 *
 * @author terra.lian
 */
@Getter
@Setter
@Accessors(chain = true)
public class RiverActionCall {

    /**
     * 动作类型
     */
    private RiverActionType actionType;

    /**
     * 自己的搭子
     */
    private IHai[] selfHais;

    /**
     * 优先级
     */
    private int order;

    /**
     * 荣和动作
     */
    public static final RiverActionCall RON = new RiverActionCall() //
            .setActionType(RiverActionType.RON) //
            .setOrder(0);
    /**
     * 跳过动作
     */
    public static final RiverActionCall SKIP = new RiverActionCall()
            .setActionType(RiverActionType.SKIP) //
            .setOrder(999); //

    /**
     * 构建一个鸣牌吃动作参数
     * 
     * @param selfHais 自己的搭子
     */
    public static RiverActionCall newChii(IHai... selfHais) {
        return new RiverActionCall() //
                .setActionType(RiverActionType.CHII) //
                .setSelfHais(selfHais) //
                .setOrder(20);
    }

    /**
     * 构建一个碰动作参数
     * 
     * @param selfHais 自己的搭子
     */
    public static RiverActionCall newPon(IHai... selfHais) {
        return new RiverActionCall() //
                .setActionType(RiverActionType.PON) //
                .setSelfHais(selfHais) //
                .setOrder(15);
    }

    /**
     * 构建一个明杠动作参数
     * 
     * @param selfHais 自己的搭子
     */
    public static RiverActionCall newMinkan(IHai... selfHais) {
        return new RiverActionCall() //
                .setActionType(RiverActionType.MINKAN) //
                .setSelfHais(selfHais) //
                .setOrder(10);
    }

    @Override
    public String toString() {
        return actionType.toString();
    }
}
