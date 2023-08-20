package com.github.terralian.fastmaj.game.event.river;

import com.github.terralian.fastmaj.game.action.river.RiverActionType;
import com.github.terralian.fastmaj.hai.IHai;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author terra.lian
 * @since 2023-03-29
 */
@Data
@Accessors(chain = true)
public class RonEvent implements RiverActionEvent {

    /**
     * 放铳的玩家
     */
    private int from;

    /**
     * 和了的玩家
     */
    private int position;

    /**
     * 荣和的牌
     */
    private IHai fromHai;

    /**
     * 优先级
     */
    private final int order = 0;

    @Override
    public int getCode() {
        return RiverEventCode.RON;
    }

    @Override
    public RiverActionType getRiverType() {
        return RiverActionType.RON;
    }
}
