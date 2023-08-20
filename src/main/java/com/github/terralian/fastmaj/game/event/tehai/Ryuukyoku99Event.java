package com.github.terralian.fastmaj.game.event.tehai;

import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.game.event.GameEventCode;
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
    public int getCode() {
        return GameEventCode.RYUUKYOKU99;
    }

    @Override
    public TehaiActionType getActionType() {
        return TehaiActionType.RYUUKYOKU99;
    }

    @Override
    public IHai getIfHai() {
        return null;
    }
}
