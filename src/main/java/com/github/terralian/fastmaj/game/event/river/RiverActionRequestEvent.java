package com.github.terralian.fastmaj.game.event.river;

import com.github.terralian.fastmaj.game.event.ActionRequestCode;
import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Terra.Lian
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiverActionRequestEvent implements GameEvent {

    /**
     * 从请求动作来
     */
    private TehaiActionEvent fromEvent;

    @Override
    public int getCode() {
        return ActionRequestCode.REQUEST_RIVER_ACTION;
    }
}
