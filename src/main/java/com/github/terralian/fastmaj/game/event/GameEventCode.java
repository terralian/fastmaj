package com.github.terralian.fastmaj.game.event;

import com.github.terralian.fastmaj.game.event.river.RiverEventCode;
import com.github.terralian.fastmaj.game.event.system.SystemEventCode;
import com.github.terralian.fastmaj.game.event.tehai.TehaiEventCode;

/**
 * 游戏事件类型，包含系统事件、手牌事件、牌河事件
 *
 * @author Terra.Lian
 */
public interface GameEventCode extends SystemEventCode, TehaiEventCode, RiverEventCode, ActionRequestCode {

    /**
     * 摸牌事件
     */
    int DRAW = 1;
}
