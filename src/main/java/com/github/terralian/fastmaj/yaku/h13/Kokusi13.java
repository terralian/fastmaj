package com.github.terralian.fastmaj.yaku.h13;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYakuman;
import com.github.terralian.fastmaj.yaku.YakuNamePool;

/**
 * 国士无双13面
 * 
 * @author terra.lian 
 */
public class Kokusi13 implements IYakuman {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        int[] value34 = Encode34.toEncode34(tehai.getAll());
        IHai agariHai = tehai.getDrawHai();
        if (agariHai == null) {
            return false;
        }
        value34[agariHai.getValue()]--;
        for (int i = 0; i < 13; i++) {
            if (value34[Encode34.YAOTYU_HAIS[i]] != 1) {
                value34[agariHai.getValue()]++;
                return false;
            }
        }
        value34[agariHai.getValue()]++;
        return true;
    }

    @Override
    public String getName() {
        return YakuNamePool.Kokusi13;
    }

    @Override
    public boolean isDoubleYakuman() {
        return true;
    }
}
