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
 * 九蓮宝燈
 *
 * @author 作者: terra.lian
 */
@MenchanYaku
public class Tyuuren implements IYakuman {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        if (divide == null || !divide.isTyuuren() || tehai.isNaki() || tehai.isAnnkan()) {
            return false;
        }

        // 检测是否纯九
        int[] value34 = Encode34.toEncode34(tehai.getAll());
        IHai agariHai = tehai.getDrawHai();
        return !isTyuuren9(value34, agariHai);
    }

    @Override
    public String getName() {
        return YakuNamePool.Tyuuren;
    }

    /**
     * 是否是纯九莲
     *
     * @param value34 34编码
     * @param agariHai 和的牌
     */
    protected boolean isTyuuren9(int[] value34, IHai agariHai) {
        // 校验和牌，和纯九莲不一致的点在于，和牌若是1-9，则原应当为2枚，若和牌为2-8，则原应当是0枚 （包含和牌的话就需要+1）
        if (agariHai.isYaotyuHai() && value34[agariHai.getValue()] == 3) {
            return false;
        } else if (!agariHai.isYaotyuHai() && value34[agariHai.getValue()] == 1) {
            return false;
        }
        return true;
    }
}
