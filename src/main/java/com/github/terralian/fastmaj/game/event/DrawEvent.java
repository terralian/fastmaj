package com.github.terralian.fastmaj.game.event;

import com.github.terralian.fastmaj.hai.IHai;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 一个摸牌事件，通过牌山或者王牌区摸牌
 *
 * @author terra.lian
 * @since 2023-03-29
 */
@Data
@Accessors(chain = true)
public class DrawEvent implements ActionEvent {

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
}
