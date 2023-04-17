package com.github.terralian.fastmaj.yaku.h13;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYakuman;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.KozuYaku;
import com.github.terralian.fastmaj.yaku.meta.MenchanYaku;
import com.github.terralian.fastmaj.yaku.meta.RequestContextYaku;
import com.github.terralian.fastmaj.yaku.meta.RonYaku;

/**
 * 四暗刻
 * <p/>
 * 需要知道荣和信息，一般规则荣和并不是四暗刻
 *
 * @author terra.lian
 */
@MenchanYaku
@RonYaku(false)
@KozuYaku(minKozuSize = 4)
@RequestContextYaku
public class Suuankou implements IYakuman {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        // 副露或者荣和都不是四暗刻，荣和仅四暗刻单骑
        if (tehai.isNaki() || holder == null || holder.isRon()) {
            return false;
        }

        int[] value34 = Encode34.toEncode34(tehai.getAll());
        // 和牌后，和牌的搭子变成暗刻，2枚的情况为四暗刻单骑
        IHai agariHai = tehai.getDrawHai();
        if (value34[agariHai.getValue()] < 3) {
            return false;
        }

        // 刻子
        int kozu = 0;
        // 是否有对子
        boolean toitu = false;

        for (int i = 0; i < 34; i++) {
            if (value34[i] == 0) {
            } else if (value34[i] == 1) {
                return false;
            } else if (value34[i] >= 3) {
                kozu++;
            } else if (!toitu && value34[i] == 2) {
                toitu = true;
            }
        }
        return kozu == 4 && toitu;
    }

    @Override
    public String getName() {
        return YakuNamePool.Suuankou;
    }
}
