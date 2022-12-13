package com.github.terralian.fastmaj.yaku.h13;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYakuman;
import com.github.terralian.fastmaj.yaku.YakuNamePool;

/**
 * 四暗刻单骑
 * 
 * @author terra.lian 
 */
public class SuuankouTanki implements IYakuman {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        // 不能副露
        if (tehai.isNaki()) {
            return false;
        }

        int[] value34 = Encode34.toEncode34(tehai.getAll());
        // 和牌后，2枚的情况不为四暗刻单骑
        IHai agariHai = tehai.getDrawHai();
        if (value34[agariHai.getValue()] != 2) {
            return false;
        }

        return divide.getAnnKotsuFirst().size() == 4;
    }

    @Override
    public String getName() {
        return YakuNamePool.SuuankouTanki;
    }

    @Override
    public boolean isDoubleYakuman() {
        return true;
    }
}
