package com.github.terralian.fastmaj.yaku.h13;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.context.IPlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYakuman;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.KozuYaku;

/**
 * 小四喜
 *
 * @author terra.lian
 */
@KozuYaku
public class Syousuusii implements IYakuman {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, IPlayerGameContext holder) {
        boolean toitu = false;
        int kozu = 0;

        // 3刻一雀头
        int[] value34 = Encode34.toEncode34(tehai.getAll());
        for (int i = Encode34.TON; i <= Encode34.PEI; i++) {
            if (!toitu && value34[i] == 2)
                toitu = true;
            else if (value34[i] >= 3)
                kozu++;
        }
        return kozu == 3 && toitu;
    }

    @Override
    public String getName() {
        return YakuNamePool.Syousuusii;
    }
}
