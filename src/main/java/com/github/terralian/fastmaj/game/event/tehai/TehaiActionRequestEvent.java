package com.github.terralian.fastmaj.game.event.tehai;

import com.github.terralian.fastmaj.game.event.ActionRequestCode;
import com.github.terralian.fastmaj.game.event.GameEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 玩家手牌动作请求事件，请求玩家执行一个手牌动作
 *
 * @author Terra.Lian
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TehaiActionRequestEvent implements GameEvent {

    /**
     * 请求动作的玩家坐席
     */
    private int position;

    /**
     * 从请求动作来
     */
    private GameEvent fromEvent;

    @Override
    public int getCode() {
        return ActionRequestCode.REQUEST_TEHAI_ACTION;
    }
}
