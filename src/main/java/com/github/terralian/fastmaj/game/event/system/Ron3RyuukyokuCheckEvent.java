package com.github.terralian.fastmaj.game.event.system;

/**
 * 三家和了校验
 *
 * @author Terra.Lian
 */
public class Ron3RyuukyokuCheckEvent implements SystemGameEvent {

    @Override
    public SystemEventType getEventType() {
        return SystemEventType.RON3_RYUUKYOKU_CHECK;
    }
}
