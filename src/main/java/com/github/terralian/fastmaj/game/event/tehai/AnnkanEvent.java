package com.github.terralian.fastmaj.game.event.tehai;

import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.game.event.GameEventEnum;
import com.github.terralian.fastmaj.hai.IHai;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 暗杠事件
 *
 * @author terra.lian
 * @since 2023-03-29
 */
@Data
@Accessors(chain = true)
public class AnnkanEvent implements TehaiActionEvent {

    private int position;

    private IHai[] annkanHai;

    @Override
    public GameEventEnum getType() {
        return GameEventEnum.ANNKAN;
    }

    @Override
    public TehaiActionType getActionType() {
        return TehaiActionType.ANNKAN;
    }

    @Override
    public IHai getIfHai() {
        return annkanHai[0];
    }
}
