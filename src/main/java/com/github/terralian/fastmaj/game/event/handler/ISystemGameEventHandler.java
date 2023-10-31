package com.github.terralian.fastmaj.game.event.handler;

import com.github.terralian.fastmaj.game.event.IDefaultGameEventHandler;
import com.github.terralian.fastmaj.game.event.system.SystemEventType;

/**
 * 系统事件处理器
 *
 * @author terra.lian
 */
public interface ISystemGameEventHandler extends IDefaultGameEventHandler {

    /**
     * 获取系统事件类型
     */
    @Override
    SystemEventType getEventType();

    /**
     * 获取系统事件枚举
     */
    @Override
    default int getEventCode() {
        return getEventType().getCode();
    }
}
