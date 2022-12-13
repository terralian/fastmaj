package com.github.terralian.fastmaj.yaku.h1;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.KazeEnum;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;

/**
 * 场风，根据场况得出东南西北
 * 
 * @author terra.lian 
 */
public class Bakaze implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        // 无场况情况下，不考虑场风
        if (holder == null) {
            return false;
        }
        KazeEnum bakaze = holder.getBakaze();
        int[] value34 = Encode34.toEncode34(tehai.getAll());
        return value34[Encode34.toEncode34(bakaze)] >= 3;
    }

    @Override
    public String getName() {
        return YakuNamePool.Bakaze;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 1;
    }
}
