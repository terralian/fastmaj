package com.github.terralian.fastmaj.yaku.h1;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.context.IPlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.JihaiYaku;

/**
 * 自风，根据场况判断东南西北
 *
 * @author 作者: terra.lian
 */
@JihaiYaku
public class Jikaze implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, IPlayerGameContext holder) {
        // 无场况情况下，不考虑自风
        if (holder == null) {
            return false;
        }
        int[] value34 = Encode34.toEncode34(tehai.getAll());
        return value34[Encode34.toEncode34(holder.getJikaze())] >= 3;
    }

    @Override
    public String getName() {
        return YakuNamePool.Jikaze;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 1;
    }
}
