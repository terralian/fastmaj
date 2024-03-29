package com.github.terralian.fastmaj.game.event.system;

/**
 * 游戏开始事件
 *
 * @author Terra.Lian
 */
public class KyokuStartEvent implements SystemGameEvent {

    @Override
    public SystemEventType getEventType() {
        return SystemEventType.KYOKU_START;
    }
}
