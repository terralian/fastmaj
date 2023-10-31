package com.github.terralian.fastmaj.game.event.system;

/**
 * 游戏结束判定事件
 *
 * @author terra.lian
 */
public class GameEndCheckEvent implements SystemGameEvent {

    @Override
    public SystemEventType getEventType() {
        return SystemEventType.GAME_END_CHECK;
    }
}
