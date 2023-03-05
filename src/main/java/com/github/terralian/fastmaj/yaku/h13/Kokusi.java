package com.github.terralian.fastmaj.yaku.h13;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYakuman;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.MenchanYaku;

/**
 * 国士无双
 * 
 * @author terra.lian 
 */
@MenchanYaku
public class Kokusi implements IYakuman {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        int[] value34 = Encode34.toEncode34(tehai.getAll());
        boolean toitu = false;

        // 国士无双13面情况下，和的牌才会组成对子
        IHai agariHai = tehai.getDrawHai();
        if (agariHai == null) {
            return false;
        }
        if (value34[agariHai.getValue()] > 1) {
            return false;
        }

        for (int i = 0; i < 13; i++) {
            int yaotyuIndex = Encode34.YAOTYU_HAIS[i];
            if (!toitu && value34[yaotyuIndex] == 2) {
                toitu = true;
            } else if (value34[yaotyuIndex] != 1 || toitu && value34[yaotyuIndex] == 2) {
                return false;
            }
        }
        return toitu;
    }

    @Override
    public String getName() {
        return YakuNamePool.Kokusi;
    }
}

