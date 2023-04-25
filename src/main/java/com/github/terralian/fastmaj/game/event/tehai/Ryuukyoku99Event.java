package com.github.terralian.fastmaj.game.event.tehai;

import com.github.terralian.fastmaj.game.event.GameEventEnum;
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
    public GameEventEnum getType() {
        return GameEventEnum.RYUUKYOKU99;
    }
}
