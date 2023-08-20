package com.github.terralian.fastmaj.game.event;

import com.github.terralian.fastmaj.yama.DrawFrom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 摸牌事件（请求）
 *
 * @author Terra.Lian
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class DrawEvent implements GameEvent {

    /**
     * 摸牌玩家坐席
     */
    private int position;
    /**
     * 摸牌来源
     */
    private DrawFrom drawFrom;
    /**
     * 来源事件
     */
    private GameEvent fromEvent;

    @Override
    public int getCode() {
        return GameEventCode.DRAW;
    }
}
