package com.github.terralian.fastmaj.yaku.h1;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.river.IHaiRiver;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.BountyYaku;
import com.github.terralian.fastmaj.yaku.meta.RequestContextYaku;

/**
 * 一发
 * <p/>
 * 一发的判断需要根据场况判断，在立直后的一巡内，进行荣和或者自摸。
 * <p/>
 * 通常规则下，一发可以被其他玩家吃碰杠（大明杠），加杠（未被抢杠），拔北等操作消除
 * <p/>
 * 不同规则的实现会不一致，该类的实现为通常规则，即鸣牌/摸岭上等操作会消除一发，一发抢杠时点会计算一发
 *
 * @author 作者: terra.lian
 */
@BountyYaku
public class Iipatu implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        if (holder == null) {
            return false;
        }
        IHaiRiver haiRiver = holder.getHaiRiver();
        if (haiRiver == null) {
            return false;
        }
        return haiRiver.isReach() && haiRiver.isSameJun() && haiRiver.getLast().isReachDisplay();
    }

    @Override
    public String getName() {
        return YakuNamePool.Iipatu;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 1;
    }
}
