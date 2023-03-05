package com.github.terralian.fastmaj.yaku.h1;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.JihaiYaku;
import com.github.terralian.fastmaj.yaku.meta.KozuYaku;

/**
 * 役牌发
 *
 * @author terra.lian
 */
@JihaiYaku
@KozuYaku
public class Hatu implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        int[] value34 = Encode34.toEncode34(tehai.getAll());
        return value34[Encode34.HATU] >= 3;
    }

    @Override
    public String getName() {
        return YakuNamePool.Hatu;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 1;
    }
}