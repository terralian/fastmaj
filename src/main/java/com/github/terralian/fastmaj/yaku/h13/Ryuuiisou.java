package com.github.terralian.fastmaj.yaku.h13;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYakuman;
import com.github.terralian.fastmaj.yaku.YakuNamePool;

/**
 * 绿一色
 *
 * @author terra.lian
 */
public class Ryuuiisou implements IYakuman {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        for (IHai hai : tehai.getAll()) {
            int value = hai.getValue();
            // 23468s发
            if (!(value == Encode34.SO_2 || value == Encode34.SO_3 || value == Encode34.SO_4 //
                    || value == Encode34.SO_6
                    || value == Encode34.SO_8
                    || value == Encode34.HATU)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getName() {
        return YakuNamePool.Ryuuiisou;
    }
}
