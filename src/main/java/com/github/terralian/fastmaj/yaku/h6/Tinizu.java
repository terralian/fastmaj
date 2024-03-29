package com.github.terralian.fastmaj.yaku.h6;

import java.util.List;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.IPlayerGameContext;
import com.github.terralian.fastmaj.hai.HaiTypeEnum;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.NonJihaiYaku;


/**
 * 清一色
 *
 * @author terra.lian
 */
@NonJihaiYaku
public class Tinizu implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, IPlayerGameContext holder) {
        // 没有字牌，且花色一致
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
        return YakuNamePool.Tinizu;
    }

    @Override
    public int getHan(boolean isNaki) {
        return isNaki ? 5 : 6;
    }
}
