package com.github.terralian.fastmaj.game.event.check;

import com.github.terralian.fastmaj.game.event.GameEventCode;

/**
 * @author Terra.Lian
 */
public class KyokuEndCheckEvent implements CheckEvent {

    @Override
    public int getCode() {
        return GameEventCode.KYOKU_END_CHECK;
    }
}
