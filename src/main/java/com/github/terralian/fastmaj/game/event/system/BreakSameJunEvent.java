package com.github.terralian.fastmaj.game.event.system;

/**
 * 破一发事件
 *
 * @author Terra.Lian
 */
public class BreakSameJunEvent implements SystemGameEvent {

    @Override
    public SystemEventType getEventType() {
        return SystemEventType.BREAK_SAME_JUN;
    }
}
