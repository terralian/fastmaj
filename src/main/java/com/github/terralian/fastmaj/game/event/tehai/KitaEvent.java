package com.github.terralian.fastmaj.game.event.tehai;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 拔北事件
 *
 * @author terra.lian
 * @since 2023-03-29
 */
@Data
@Accessors(chain = true)
public class KitaEvent {

    /**
     * 拔北事件
     */
    private int position;
}
