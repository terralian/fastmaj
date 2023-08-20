package com.github.terralian.fastmaj.game.event;

/**
 * 游戏事件消息本体，其实现类有三种：系统事件，手牌事件、牌河事件。
 * <p/>
 * 事件一般通过队列有序生产消费，每种事件一般都有与其一一对应的处理器，这些处理器将会消费事件；
 *
 * @author Terra.Lian
 */
public interface GameEvent extends GameEventBase {
}
