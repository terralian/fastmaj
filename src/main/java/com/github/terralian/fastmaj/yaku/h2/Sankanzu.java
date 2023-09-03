package com.github.terralian.fastmaj.yaku.h2;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.IPlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.KozuYaku;

/**
 * 三杠子
 *
 * @author terra.lian
 */
@KozuYaku
public class Sankanzu implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, IPlayerGameContext holder) {
        // 杠子数（暗杠+明杠） = 3
        return tehai.getLock().kanzuSize() == 3;
    }

    @Override
    public String getName() {
        return YakuNamePool.Sankanzu;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 2;
    }

}
