package com.github.terralian.fastmaj.game.event;

/**
 * 动作事件
 *
 * @author terra.lian
 * @since 2023-03-29
 */
public interface ActionEvent extends DefaultGameEvent {

    /**
     * 获取发生事件的玩家的坐席
     */
    int getPosition();

    /**
     * 设置手牌事件的玩家坐席
     *
     * @param <T> 子类（chain形式）
     */
    <T extends ActionEvent> T setPosition(int position);
}
