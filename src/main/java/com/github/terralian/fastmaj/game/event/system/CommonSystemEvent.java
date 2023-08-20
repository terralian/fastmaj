package com.github.terralian.fastmaj.game.event.system;

/**
 * 无状态通用系统事件，这些事件不含可变参数
 *
 * @author Terra.Lian
 */
public class CommonSystemEvent implements SystemEvent {

    private final int eventType;

    public CommonSystemEvent(int eventType) {
        this.eventType = eventType;
    }

    @Override
    public int getCode() {
        return eventType;
    }
}
