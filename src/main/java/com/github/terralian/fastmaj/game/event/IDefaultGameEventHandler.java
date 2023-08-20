package com.github.terralian.fastmaj.game.event;

/**
 * 系统默认定义的游戏事件处理器
 *
 * @author Terra.Lian
 */
public interface IDefaultGameEventHandler extends IGameEventHandler {

    /**
     * 获取处理器对应的事件类型
     */
    Enum getEventType();
}
