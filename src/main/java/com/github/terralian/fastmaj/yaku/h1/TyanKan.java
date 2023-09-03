package com.github.terralian.fastmaj.yaku.h1;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.game.context.IPlayerGameContext;
import com.github.terralian.fastmaj.game.event.river.RonEvent;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;
import com.github.terralian.fastmaj.player.space.PlayerPublicSpace;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.util.Assert;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.RonYaku;

/**
 * 抢杠
 * <p/>
 * 抢杠为当其他家进行加杠操作时，该枚牌为和了牌时的役种
 * <p/>
 * 该役的计算时点应该是，在对方操作抢杠时，对其他听牌且未为振听的一方，计算一次和了判定，若未和牌，修改一发巡标识为否。
 *
 * @author 作者: terra.lian
 */
@RonYaku
public class TyanKan implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, IPlayerGameContext holder) {
        if (holder == null || !holder.isEndByRon()) {
            return false;
        }
        RonEvent ronEvent = (RonEvent) holder.getSpace().getLastAction();

        int fromPosition = ronEvent.getFrom();
        PlayerPublicSpace publicSpace = holder.getPublicSpace(fromPosition);
        TehaiActionEvent tehaiActionEvent = publicSpace.getLastTehaiAction();
        Assert.notNull(tehaiActionEvent, "荣和情况下，对手必有一个手牌动作");

        return tehaiActionEvent.getEventType() == TehaiActionType.ANNKAN //
                || tehaiActionEvent.getEventType() == TehaiActionType.KAKAN;
    }

    @Override
    public String getName() {
        return YakuNamePool.TyanKan;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 1;
    }
}
