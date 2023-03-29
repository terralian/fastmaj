package com.github.terralian.fastmaj.game.event.tehai;

import com.github.terralian.fastmaj.game.event.ActionEvent;
import com.github.terralian.fastmaj.hai.IHai;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 弃牌事件
 *
 * @author terra.lian
 * @since 2023-03-29
 */
@Data
@Accessors(chain = true)
public class KiriEvent implements ActionEvent {

    /**
     * 弃牌的玩家
     */
    private int position;

    /**
     * 切的牌
     */
    private IHai kiriHai;
}
