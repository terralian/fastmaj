package com.github.terralian.fastmaj.yaku.h1;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.game.context.IPlayerGameContext;
import com.github.terralian.fastmaj.game.event.river.RonEvent;
import com.github.terralian.fastmaj.player.space.PlayerDefaultSpace;
import com.github.terralian.fastmaj.player.space.PlayerPublicSpace;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.RonYaku;

/**
 * 河底捞鱼
 * <p/>
 * 河底需要对手打出一枚牌，而非抢杠。
 *
 * @author 作者: terra.lian
 */
@RonYaku
public class Houtei implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, IPlayerGameContext holder) {
        //海底
        if (holder == null || holder.getYamaCountdown() > 0 || !holder.isEndByRon()) {
            return false;
        }
        // 非抢杠
        PlayerDefaultSpace defaultSpace = holder.getSpace();
        RonEvent lastAction = (RonEvent) defaultSpace.getLastAction();
        int fromPosition = lastAction.getFrom();
        PlayerPublicSpace publicSpace = holder.getPublicSpace(fromPosition);
        return publicSpace.getLastTehaiAction().getEventType() == TehaiActionType.KIRI;
    }

    @Override
    public String getName() {
        return YakuNamePool.Houtei;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 1;
    }
}
