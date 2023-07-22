package com.github.terralian.fastmaj.game.event.river;

import com.github.terralian.fastmaj.game.action.river.RiverActionType;
import com.github.terralian.fastmaj.game.event.ActionEvent;
import com.github.terralian.fastmaj.hai.IHai;

/**
 * 牌河事件
 *
 * @author terra.lian
 * @since 2023-04-25
 */
public interface RiverActionEvent extends ActionEvent {

    /**
     * 设置手牌事件的玩家坐席
     */
    @SuppressWarnings("unchecked")
    RiverActionEvent setPosition(int position);

    /**
     * 获取来源的玩家坐席
     */
    int getFrom();

    /**
     * 设置来源玩家坐席
     *
     * @param from 来源玩家的坐席
     * @param <T> 子类（chain形式）
     */
    <T extends RiverActionEvent> T setFrom(int from);

    /**
     * 获取操作的牌
     */
    IHai getFromHai();

    /**
     * 设置来源玩家的牌
     *
     * @param fromHai 来源玩家的牌
     * @param <T> 子类（chain形式）
     */
    <T extends RiverActionEvent> T setFromHai(IHai fromHai);

    /**
     * 获取牌河动作类型
     */
    RiverActionType getRiverType();

    /**
     * 获取优先级
     */
    int getOrder();
}
