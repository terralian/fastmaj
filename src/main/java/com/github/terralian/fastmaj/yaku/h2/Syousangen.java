package com.github.terralian.fastmaj.yaku.h2;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;

/**
 * 小三元
 * 
 * @author terra.lian 
 */
public class Syousangen implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        int[] value34 = Encode34.toEncode34(tehai.getAll());
        boolean toitu = false;
        int koutu = 0;
        for (int i = Encode34.HAKU; i <= Encode34.TYUN; i++) {
            if (value34[i] == 2 && toitu == false)
                toitu = true;
            if (value34[i] >= 3)
                koutu++;
        }
        return (koutu == 2 && toitu == true);
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
