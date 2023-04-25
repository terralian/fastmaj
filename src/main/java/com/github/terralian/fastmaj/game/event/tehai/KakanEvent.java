package com.github.terralian.fastmaj.game.event.tehai;

import com.github.terralian.fastmaj.game.event.GameEventEnum;
import com.github.terralian.fastmaj.hai.IHai;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 加杠事件
 *
 * @author terra.lian
 * @since 2023-03-29
 */
@Data
@Accessors(chain = true)
public class KakanEvent implements TehaiActionEvent {

    private int position;

    private IHai kakanHai;

    @Override
    public GameEventEnum getType() {
        return GameEventEnum.KAKAN;
    }
}
