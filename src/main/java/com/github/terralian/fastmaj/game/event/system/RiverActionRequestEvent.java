package com.github.terralian.fastmaj.game.event.system;

import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 牌河动作请求事件
 *
 * @author Terra.Lian
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiverActionRequestEvent implements SystemGameEvent {

    /**
     * 从请求动作来
     */
    private TehaiActionEvent fromEvent;

    @Override
    public SystemEventType getEventType() {
        return SystemEventType.RIVER_ACTION_REQUEST;
    }
}
