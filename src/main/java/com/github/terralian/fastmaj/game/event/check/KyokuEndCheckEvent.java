package com.github.terralian.fastmaj.game.event.check;

import com.github.terralian.fastmaj.game.event.system.SystemEventCode;

/**
 * @author Terra.Lian
 */
public class KyokuEndCheckEvent implements CheckEvent {

    @Override
    public int getCode() {
        return SystemEventCode.KYOKU_END_CHECK;
    }
}
