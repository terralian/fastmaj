package com.github.terralian.fastmaj.game.event.system;

/**
 * 下一枚Dora事件
 *
 * @author Terra.Lian
 */
public class NextDoraEvent implements SystemGameEvent {

    @Override
    public SystemEventType getEventType() {
        return SystemEventType.NEXT_DORA;
    }
}
