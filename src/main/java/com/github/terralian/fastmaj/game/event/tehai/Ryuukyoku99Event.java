package com.github.terralian.fastmaj.game.event.tehai;

import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.hai.IHai;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 九种九牌流局事件
 *
 * @author terra.lian
 * @since 2023-03-29
 */
@Data
@Accessors(chain = true)
public class Ryuukyoku99Event implements TehaiActionEvent {

    private int position;

    @Override
    public TehaiActionType getEventType() {
        return TehaiActionType.RYUUKYOKU99;
    }

    @Override
    public IHai getIfHai() {
        return null;
    }
}
