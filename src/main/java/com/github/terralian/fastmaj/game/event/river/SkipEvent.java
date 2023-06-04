package com.github.terralian.fastmaj.game.event.river;

import com.github.terralian.fastmaj.game.action.river.RiverActionType;
import com.github.terralian.fastmaj.game.event.GameEventEnum;
import com.github.terralian.fastmaj.hai.IHai;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * CreateByTerra
 *
 * @author terra.lian
 * @since 2023-06-04
 */
@Data
@Accessors(chain = true)
public class SkipEvent implements RiverActionEvent {
    /**
     * 处理的玩家
     */
    private int from;

    /**
     * 和了的玩家
     */
    private int position;

    /**
     * 来源的牌
     */
    private IHai fromHai;

    /**
     * 优先级
     */
    private final int order = Integer.MIN_VALUE;

    @Override
    public GameEventEnum getType() {
        return GameEventEnum.SKIP;
    }

    @Override
    public RiverActionType getRiverType() {
        return RiverActionType.SKIP;
    }

}
