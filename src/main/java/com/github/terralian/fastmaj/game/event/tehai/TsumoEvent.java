package com.github.terralian.fastmaj.game.event.tehai;

import com.github.terralian.fastmaj.game.event.ActionEvent;
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
public class TsumoEvent implements ActionEvent {

    /**
     * 自摸的玩家
     */
    private int position;
}
