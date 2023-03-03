package com.github.terralian.fastmaj.yaku.h2;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;

/**
 * 一气通贯
 * 
 * @author terra.lian 
 */
public class Ittuu implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        if (divide == null)
            return false;
        // 收集所有的顺子第一枚
        int[] value34 = new int[Encode34.length()];
        for (IHai hai : divide.getAllShuntsuFirst()) {
            value34[hai.getValue()]++;
        }
        // 若顺子的第一枚包含某一个花色的1,4,7则是一气
        if (value34[Encode34.MAN_1] > 0 && value34[Encode34.MAN_4] > 0 && value34[Encode34.MAN_7] > 0) {
            return true;
        }
        if (value34[Encode34.SO_1] > 0 && value34[Encode34.SO_4] > 0 && value34[Encode34.SO_7] > 0) {
            return true;
        }
        if (value34[Encode34.PIN_1] > 0 && value34[Encode34.PIN_4] > 0 && value34[Encode34.PIN_7] > 0) {
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return YakuNamePool.Ittuu;
    }

    @Override
    public int getHan(boolean isNaki) {
        return isNaki ? 1 : 2;
    }

}
