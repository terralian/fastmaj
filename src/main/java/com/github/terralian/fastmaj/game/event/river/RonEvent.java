package com.github.terralian.fastmaj.game.event.river;

import com.github.terralian.fastmaj.game.event.ActionEvent;
import com.github.terralian.fastmaj.game.event.GameEventEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author terra.lian
 * @since 2023-03-29
 */
@Data
@Accessors(chain = true)
public class RonEvent implements ActionEvent {

    /**
     * 放铳的玩家
     */
    private int from;

    /**
     * 和了的玩家
     */
    private int position;

    @Override
    public GameEventEnum getType() {
        return GameEventEnum.RON;
    }
}
