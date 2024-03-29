package com.github.terralian.fastmaj.game.event.system;

/**
 * 游戏开始事件
 *
 * @author Terra.Lian
 */
public class GameStartEvent implements SystemGameEvent {

    @Override
    public SystemEventType getEventType() {
        return SystemEventType.GAME_START;
    }
}
