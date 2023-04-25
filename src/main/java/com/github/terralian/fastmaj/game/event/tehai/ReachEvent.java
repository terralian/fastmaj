package com.github.terralian.fastmaj.game.event.tehai;

import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.game.event.GameEventEnum;
import com.github.terralian.fastmaj.hai.IHai;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 立直事件
 *
 * @author terra.lian
 * @since 2023-03-29
 */
@Data
@Accessors(chain = true)
public class ReachEvent implements TehaiActionEvent {

    /**
     * 自摸的玩家
     */
    private int position;

    /**
     * 立直打出的牌
     */
    private IHai reachHai;

    @Override
    public GameEventEnum getType() {
        return GameEventEnum.REACH;
    }

    @Override
    public TehaiActionType getActionType() {
        return TehaiActionType.REACH;
    }

    @Override
    public IHai getIfHai() {
        return reachHai;
    }
}
