package com.github.terralian.fastmaj.yaku.h1;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;

/**
 * 一杯口
 * 
 * @author 作者: terra.lian
 * 
 */
public class Iipeikou implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        // 门清役
        if (tehai.isNaki()) {
            return false;
        }
        if (divide != null && divide.isIipeikou()) {
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return YakuNamePool.Iipeikou;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 1;
    }
}
