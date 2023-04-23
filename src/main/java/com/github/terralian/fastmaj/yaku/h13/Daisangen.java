package com.github.terralian.fastmaj.yaku.h13;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYakuman;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.JihaiYaku;

/**
 * 大三元
 *
 * @author terra.lian
 */
@JihaiYaku
public class Daisangen implements IYakuman {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        // 中发白每个都大于3
        int[] value34 = Encode34.toEncode34(tehai.getAll());
        return value34[Encode34.HAKU] >= 3 && value34[Encode34.HATU] >= 3 && value34[Encode34.TYUN] >= 3;
    }

    @Override
    public String getName() {
        return YakuNamePool.Daisangen;
    }
}
