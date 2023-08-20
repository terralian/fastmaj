package com.github.terralian.fastmaj.game.event.river;

import com.github.terralian.fastmaj.game.action.river.RiverActionType;
import com.github.terralian.fastmaj.hai.IHai;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 鸣牌-明杠事件
 *
 * @author terra.lian
 * @since 2023-03-29
 */
@Data
@Accessors(chain = true)
public class MinkanEvent implements RiverActionEvent {

    /**
     * 从玩家鸣牌
     */
    private int from;

    /**
     * 鸣牌的玩家
     */
    private int position;

    /**
     * 自己的搭子
     */
    private IHai[] selfHais;

    /**
     * 鸣的牌
     */
    private IHai fromHai;

    /**
     * 优先级
     */
    private final int order = 10;

    @Override
    public RiverActionType getEventType() {
        return RiverActionType.MINKAN;
    }
}
