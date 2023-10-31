package com.github.terralian.fastmaj.game.event.system;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 放置立直棒事件，扣取玩家1000点数
 *
 * @author terra.lian
 */
@Data
@AllArgsConstructor
public class ReachSetEvent implements SystemGameEvent {

    private int position;

    @Override
    public SystemEventType getEventType() {
        return SystemEventType.REACH_SET;
    }
}
