package com.github.terralian.fastmaj.yaku.h6;

import java.util.List;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.HaiTypeEnum;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;


/**
 * 清一色
 * 
 * @author terra.lian 
 */
public class Tinitu implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        List<IHai> value = tehai.getAll();
        HaiTypeEnum haiType = value.get(0).geHaiType();
        if (haiType == HaiTypeEnum.Z) {
            return false;
        }
        for (int i = 1; i < value.size(); i++) {
            if (value.get(i).geHaiType() != haiType) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getName() {
        return YakuNamePool.Tinitu;
    }

    @Override
    public int getHan(boolean isNaki) {
        return isNaki ? 5 : 6;
    }
}
