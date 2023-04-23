package com.github.terralian.fastmaj.yaku.h2;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.KozuYaku;

/**
 * 对对和
 *
 * @author terra.lian
 */
@KozuYaku
public class Toitoi implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        // 必须4个刻子（杠也行）
        if (divide.getAllKanKozuFirst().size() < 4) {
            return false;
        }
        // 鸣牌后的对对
        if (tehai.isNaki()) {
            return true;
        }
        // 非荣和，又不是鸣牌，四暗刻起步
        if (holder == null || !holder.isRon()) {
            return false;
        }
        // 门清荣和，需要排除四暗刻单骑
        int[] value34 = Encode34.toEncode34(tehai.getAll());
        IHai agariHai = tehai.getDrawHai();
        return value34[agariHai.getValue()] > 2;
    }

    @Override
    public String getName() {
        return YakuNamePool.Toitoi;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 2;
    }

}
