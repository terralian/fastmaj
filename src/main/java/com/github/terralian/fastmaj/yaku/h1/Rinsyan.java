package com.github.terralian.fastmaj.yaku.h1;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.IPlayerGameContext;
import com.github.terralian.fastmaj.game.event.ActionEvent;
import com.github.terralian.fastmaj.game.event.DrawEvent;
import com.github.terralian.fastmaj.game.event.system.SystemEventType;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.RequestContextYaku;
import com.github.terralian.fastmaj.yama.DrawFrom;

/**
 * 岭上开花
 *
 * @author 作者: terra.lian
 */
@RequestContextYaku
public class Rinsyan implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, IPlayerGameContext holder) {
        if (holder == null || holder.isEndByRon()) {
            return false;
        }
        ActionEvent lastAction = holder.getSpace().getLastAction();
        if (lastAction == null || lastAction.getEventType() != SystemEventType.DRAW) {
            return false;
        }
        DrawEvent drawEvent = (DrawEvent) lastAction;
        return drawEvent.getDrawFrom() == DrawFrom.RINSYAN;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 1;
    }

    @Override
    public String getName() {
        return YakuNamePool.Rinsyan;
    }
}
