package com.github.terralian.fastmaj.game.event.system;

import com.github.terralian.fastmaj.game.event.DefaultGameEvent;

/**
 * @author terra.lian
 */
public interface SystemGameEvent extends DefaultGameEvent {

    /**
     * 获取系统事件类型
     */
    SystemEventType getEventType();

    /**
     * 系统事件的编码为其类型编码
     */
    @Override
    default int getEventCode() {
        return getEventType().getCode();
    }
}
