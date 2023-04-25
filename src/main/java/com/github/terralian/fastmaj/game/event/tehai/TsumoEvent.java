package com.github.terralian.fastmaj.game.event.tehai;

import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.game.event.GameEventEnum;
import com.github.terralian.fastmaj.hai.IHai;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 自摸事件
 *
 * @author terra.lian
 * @since 2023-03-29
 */
@Data
@Accessors(chain = true)
public class TsumoEvent implements TehaiActionEvent {

    /**
     * 自摸的玩家
     */
    private int position;

    @Override
    public GameEventEnum getType() {
        return GameEventEnum.TSUMO;
    }

    @Override
    public TehaiActionType getActionType() {
        return TehaiActionType.TSUMO;
    }

    @Override
    public IHai getIfHai() {
        return null;
    }
}
