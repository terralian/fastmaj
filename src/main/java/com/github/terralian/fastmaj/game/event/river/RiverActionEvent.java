package com.github.terralian.fastmaj.game.event.river;

import com.github.terralian.fastmaj.game.event.ActionEvent;

/**
 * 牌河事件
 * @author terra.lian
 * @since 2023-04-25
 */
public interface RiverActionEvent extends ActionEvent {

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
}
