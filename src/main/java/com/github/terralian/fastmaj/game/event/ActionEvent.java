package com.github.terralian.fastmaj.game.event;

/**
 * 动作事件
 * <p/>
 * 玩家通过发起动作事件来和系统进行交互，如吃碰杠荣和等。系统也可产生事件来达到游戏执行的效果
 * <p/>
 * 事件与动作(IGameAction)的差别是，动作是系统的操作，事件则是消息体。
 *
 * @author terra.lian
 * @since 2023-03-29
 */
public interface ActionEvent extends GameEvent {

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
