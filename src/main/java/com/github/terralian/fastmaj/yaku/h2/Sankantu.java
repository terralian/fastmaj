package com.github.terralian.fastmaj.yaku.h2;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;

/**
 * 三杠子
 * 
 * @author terra.lian 
 */
public class Sankantu implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        return tehai.getLock().kanzuSize() == 3;
    }

    @Override
    public String getName() {
        return YakuNamePool.Sankantu;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 2;
    }

}
