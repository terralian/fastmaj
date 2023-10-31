package com.github.terralian.fastmaj.game.event.system;

import com.github.terralian.fastmaj.game.event.GameEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 玩家手牌动作请求事件，请求玩家执行一个手牌动作
 *
 * @author terra.lian
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TehaiActionRequestEvent implements SystemGameEvent {

    /**
     * 请求动作的玩家坐席
     */
    private int position;

    /**
     * 从请求动作来
     */
    private GameEvent fromEvent;

    @Override
    public SystemEventType getEventType() {
        return SystemEventType.TEHAI_ACTION_REQUEST;
    }
}
