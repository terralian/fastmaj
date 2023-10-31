package com.github.terralian.fastmaj.game.event;

/**
 * 系统默认定义的事件
 *
 * @author terra.lian
 */
public interface DefaultGameEvent extends GameEvent {

    /**
     * 获取事件类型，每种系统默认定义的实际都有其对应的类型
     */
    Enum getEventType();
}
