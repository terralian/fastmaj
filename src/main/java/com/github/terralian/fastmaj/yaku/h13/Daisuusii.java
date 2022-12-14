package com.github.terralian.fastmaj.yaku.h13;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYakuman;
import com.github.terralian.fastmaj.yaku.YakuNamePool;

/**
 * 大四喜
 * 
 * @author terra.lian 
 */
public class Daisuusii implements IYakuman {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        int[] value34 = Encode34.toEncode34(tehai.getAll());
        for (int i = Encode34.TON; i <= Encode34.PEI; i++) {
            if (value34[i] < 3)
                return false;
        }
        return true;
    }

    @Override
    public String getName() {
        return YakuNamePool.Daisuusii;
    }

    @Override
    public boolean isDoubleYakuman() {
        return true;
    }
}
