package com.github.terralian.fastmaj.game.event.system;

import com.github.terralian.fastmaj.game.event.GameEventCode;

/**
 * @author Terra.Lian
 */
public class NextDoraEvent implements SystemEvent {

    @Override
    public int getCode() {
        return GameEventCode.NEXT_DORA_EVENT;
    }
}
