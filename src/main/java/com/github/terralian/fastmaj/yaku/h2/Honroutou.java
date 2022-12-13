package com.github.terralian.fastmaj.yaku.h2;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;

/**
 * 混老头
 * <p>
 * 需要所有牌都是幺九牌，可以看做国士下位，但是和国士不复合。可以和七对子复合。
 * <p>
 * 混老头和混全带的区别是，混全带需要含幺九顺子（123,789），所以两者不复合
 * 
 * @author terra.lian 
 */
public class Honroutou implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        int[] value34 = Encode34.toEncode34(tehai.getAll());
        boolean nonjiHai = true;
        for (int i = Encode34.DONG; i <= Encode34.ZHONG; i++) {
            // 过滤国士
            if (value34[i] == 1)
                return false;
            if (nonjiHai && value34[i] > 1)
                nonjiHai = false;
        }
        // 过滤清老头
        if (nonjiHai) {
            return false;
        }
        // 非幺九校验
        for (int i = 0; i < Encode34.DONG; i++) {
            if (value34[i] == 0)
                continue;
            if (Encode34.isMiddleHai(i))
                return false;
        }
        return true;
    }

    @Override
    public String getName() {
        return YakuNamePool.Honroutou;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 2;
    }

}
