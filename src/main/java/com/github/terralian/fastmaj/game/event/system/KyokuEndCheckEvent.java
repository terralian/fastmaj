package com.github.terralian.fastmaj.game.event.system;

import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.GameEventCode;

/**
 * @author Terra.Lian
 */
public class KyokuEndCheckEvent implements GameEvent {

    @Override
    public int getCode() {
        return GameEventCode.KYOKU_END_CHECK;
    }
}
