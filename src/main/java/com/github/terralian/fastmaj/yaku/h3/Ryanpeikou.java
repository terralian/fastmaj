package com.github.terralian.fastmaj.yaku.h3;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;

/**
 * 二杯口
 * 
 * @author terra.lian 
 */
public class Ryanpeikou implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        if (tehai.isNaki()) {
            return false;
        }
        if (divide != null && divide.isRyanpeikou()) {
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return YakuNamePool.Ryanpeikou;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 3;
    }

}
