package com.github.terralian.fastmaj.game.event.system;

import com.github.terralian.fastmaj.game.event.GameEventCode;
import com.github.terralian.fastmaj.game.ryuuky.IRyuukyokuResolver;
import lombok.Getter;

/**
 * 流局事件
 *
 * @author Terra.Lian
 */
@Getter
public class RyuukyokuEvent implements SystemEvent {

    private final IRyuukyokuResolver ryuukyokuResolver;

    public RyuukyokuEvent(IRyuukyokuResolver ryuukyokuResolver) {
        this.ryuukyokuResolver = ryuukyokuResolver;
    }

    @Override
    public int getCode() {
        return GameEventCode.RYUUKYOKU;
    }
}
