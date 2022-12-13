package com.github.terralian.fastmaj.yaku.h1;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;

/**
 * 役牌白
 * 
 * @author terra.lian 
 */
public class Haku implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        int[] value34 = Encode34.toEncode34(tehai.getAll());
        if (value34[Encode34.HAKU] >= 3) {
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return YakuNamePool.Haku;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 1;
    }
}

