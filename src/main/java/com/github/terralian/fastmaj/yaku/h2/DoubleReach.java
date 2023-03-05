package com.github.terralian.fastmaj.yaku.h2;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.river.IHaiRiver;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.StateYaku;

/**
 * 两立直
 *
 * @author terra.lian
 */
@StateYaku
public class DoubleReach implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        if (holder == null || holder.getHaiRiver() == null) {
            return false;
        }
        IHaiRiver haiRiver = holder.getHaiRiver();
        return haiRiver.isDoubleReach();
    }

    @Override
    public String getName() {
        return YakuNamePool.DoubleReach;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 2;
    }

}
