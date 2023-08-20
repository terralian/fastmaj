package com.github.terralian.fastmaj.game.event.system;

import com.github.terralian.fastmaj.game.event.GameEventCode;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Terra.Lian
 */
@Data
@AllArgsConstructor
public class ReachSetEvent implements SystemEvent {

    private int position;

    @Override
    public int getCode() {
        return GameEventCode.REACH_SET;
    }
}
