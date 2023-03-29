package com.github.terralian.fastmaj.game.event.tehai;

import com.github.terralian.fastmaj.game.event.ActionEvent;
import com.github.terralian.fastmaj.hai.IHai;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 立直事件
 *
 * @author terra.lian
 * @since 2023-03-29
 */
@Data
@Accessors(chain = true)
public class ReachEvent implements ActionEvent {

    /**
     * 自摸的玩家
     */
    private int position;

    /**
     * 立直打出的牌
     */
    private IHai reachHai;
}
