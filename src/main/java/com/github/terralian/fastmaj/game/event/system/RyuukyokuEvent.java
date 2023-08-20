package com.github.terralian.fastmaj.game.event.system;

import com.github.terralian.fastmaj.game.ryuuky.IRyuukyokuResolver;
import lombok.Getter;

/**
 * 流局事件
 *
 * @author Terra.Lian
 */
@Getter
public class RyuukyokuEvent implements SystemGameEvent {

    private final IRyuukyokuResolver ryuukyokuResolver;

    public RyuukyokuEvent(IRyuukyokuResolver ryuukyokuResolver) {
        this.ryuukyokuResolver = ryuukyokuResolver;
    }

    @Override
    public SystemEventType getEventType() {
        return SystemEventType.RYUUKYOKU;
    }
}
