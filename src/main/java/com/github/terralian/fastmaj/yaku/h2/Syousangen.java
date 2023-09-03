package com.github.terralian.fastmaj.yaku.h2;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.context.IPlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.JihaiYaku;

/**
 * 小三元
 *
 * @author terra.lian
 */
@JihaiYaku
public class Syousangen implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, IPlayerGameContext holder) {
        // 白发中需要全部大于3枚，并且有一个=2枚（全部3枚以上的就是大三元了）
        int[] value34 = Encode34.toEncode34(tehai.getAll());
        boolean toitu = false;
        int kozu = 0;
        for (int i = Encode34.HAKU; i <= Encode34.TYUN; i++) {
            if (value34[i] == 2 && !toitu)
                toitu = true;
            if (value34[i] >= 3)
                kozu++;
        }
        return (kozu == 2 && toitu);
    }

    @Override
    public String getName() {
        return YakuNamePool.Syousangen;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 2;
    }

}
