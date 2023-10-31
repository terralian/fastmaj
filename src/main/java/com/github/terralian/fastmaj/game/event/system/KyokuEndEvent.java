package com.github.terralian.fastmaj.game.event.system;

/**
 * 游戏结束事件
 *
 * @author terra.lian
 */
public class KyokuEndEvent implements SystemGameEvent {

    @Override
    public SystemEventType getEventType() {
        return SystemEventType.KYOKU_END;
    }
}
