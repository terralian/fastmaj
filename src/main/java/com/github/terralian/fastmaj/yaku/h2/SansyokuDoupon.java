package com.github.terralian.fastmaj.yaku.h2;

import java.util.HashMap;
import java.util.Map;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.HaiTypeEnum;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;

/**
 * 三色同刻
 * 
 * @author terra.lian 
 */
public class SansyokuDoupon implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        Map<Integer, Integer> map = new HashMap<>(4);
        if (divide != null && !divide.getAllKanKotsuFirst().isEmpty()) {
            for (IHai hai : divide.getAllKanKotsuFirst()) {
                if (hai.isJiHai()) {
                    continue;
                }

                int sign;
                if (hai.geHaiType() == HaiTypeEnum.M)
                    sign = 2;
                else if (hai.geHaiType() == HaiTypeEnum.S)
                    sign = 3;
                else
                    sign = 4;

                int literal = EncodeMark.clearRed(hai.getLiteral());
                int mapValue = map.getOrDefault(literal, 1) * sign;
                if (mapValue % 24 == 0) {
                    return true;
                }

                map.put(literal, mapValue);
            }
        }
        return false;
    }

    @Override
    public String getName() {
        return YakuNamePool.SansyokuDoupon;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 2;
    }

}
