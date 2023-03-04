package com.github.terralian.fastmaj.yaku.h1;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.river.IHaiRiver;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;

/**
 * 立直
 * 
 * @author 作者: terra.lian
 * 
 */
public class Reach implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        // 过滤两立直
        if (holder == null) {
            return false;
        }
        IHaiRiver haiRiver = holder.getHaiRiver();
        return haiRiver.isReach() && !haiRiver.isDoubleReach();
    }

    @Override
    public String getName() {
        return YakuNamePool.Reach;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 1;
    }
}

