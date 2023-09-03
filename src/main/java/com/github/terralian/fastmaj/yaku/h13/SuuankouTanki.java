package com.github.terralian.fastmaj.yaku.h13;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.context.IPlayerGameContext;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYakuman;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.KozuYaku;

/**
 * 四暗刻单骑
 *
 * @author terra.lian
 */
@KozuYaku
public class SuuankouTanki implements IYakuman {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, IPlayerGameContext holder) {
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
        // 暗刻+暗杠 = 4
        return divide.getAnnkoFirst().size() + tehai.getLock().getAnnkanFirst().size() == 4;
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
