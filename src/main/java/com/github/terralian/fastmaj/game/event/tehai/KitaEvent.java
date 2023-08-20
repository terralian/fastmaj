package com.github.terralian.fastmaj.game.event.tehai;

import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.game.event.GameEventCode;
import com.github.terralian.fastmaj.hai.IHai;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 拔北事件
 *
 * @author terra.lian
 * @since 2023-03-29
 */
@Data
@Accessors(chain = true)
public class KitaEvent implements TehaiActionEvent {

    /**
     * 拔北事件
     */
    private int position;

    @Override
    public int getCode() {
        return GameEventCode.KITA;
    }

    @Override
    public TehaiActionType getActionType() {
        return TehaiActionType.KITA;
    }

    @Override
    public IHai getIfHai() {
        return null;
    }
}
