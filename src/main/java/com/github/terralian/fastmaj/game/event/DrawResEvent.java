package com.github.terralian.fastmaj.game.event;

import com.github.terralian.fastmaj.game.event.system.SystemEventType;
import com.github.terralian.fastmaj.hai.IHai;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 摸牌结果事件
 *
 * @author terra.lian
 */
@Data
@Accessors(chain = true)
public class DrawResEvent implements ActionEvent {

    /**
     * 从玩家
     */
    private int position;

    /**
     * 摸的牌
     */
    private IHai drawHai;

    /**
     * 是否从牌山摸牌
     */
    private boolean fromYama;

    @Override
    public int getEventCode() {
        return SystemEventType.DRAW.getCode();
    }

    /**
     * @return
     */
    @Override
    public Enum getEventType() {
        return null;
    }
}
