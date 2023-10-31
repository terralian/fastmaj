package com.github.terralian.fastmaj.game.event.system;

/**
 * 对局结束确认事件
 *
 * @author terra.lian
 */
public class KyokuEndCheckEvent implements SystemGameEvent {

    @Override
    public SystemEventType getEventType() {
        return SystemEventType.KYOKU_END_CHECK;
    }
}
