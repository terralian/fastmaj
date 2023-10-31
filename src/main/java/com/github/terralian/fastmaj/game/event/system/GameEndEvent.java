package com.github.terralian.fastmaj.game.event.system;

/**
 * 游戏结束事件
 *
 * @author terra.lian
 */
public class GameEndEvent implements SystemGameEvent {

    @Override
    public SystemEventType getEventType() {
        return SystemEventType.GAME_END;
    }
}
