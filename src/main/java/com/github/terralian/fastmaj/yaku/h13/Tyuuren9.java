package com.github.terralian.fastmaj.yaku.h13;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.MenchanYaku;

/**
 * 纯正九莲宝灯
 *
 * @author terra.lian
 */
@MenchanYaku
public class Tyuuren9 extends Tyuuren {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        if (!divide.isTyuuren() || tehai.isNaki() || tehai.isAnnkan()) {
            return false;
        }

        // 检测是否纯九
        int[] value34 = Encode34.toEncode34(tehai.getAll());
        IHai agariHai = tehai.getDrawHai();
        return isTyuuren9(value34, agariHai);
    }

    @Override
    public String getName() {
        return YakuNamePool.Tyuuren9;
    }
}
