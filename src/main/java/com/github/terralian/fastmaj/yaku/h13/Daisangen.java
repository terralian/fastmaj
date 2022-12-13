package com.github.terralian.fastmaj.yaku.h13;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYakuman;
import com.github.terralian.fastmaj.yaku.YakuNamePool;

/**
 * 大三元
 * 
 * @author terra.lian 
 */
public class Daisangen implements IYakuman {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        int[] value34 = Encode34.toEncode34(tehai.getAll());
        if (value34[Encode34.HAKU] >= 3 && value34[Encode34.HATU] >= 3 && value34[Encode34.TYUN] >= 3)
            return true;
        return false;
    }

    @Override
    public String getName() {
        return YakuNamePool.Daisangen;
    }
}
