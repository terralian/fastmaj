package com.github.terralian.fastmaj.yaku.h3;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.HaiTypeEnum;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;

/**
 * 纯全带幺九
 * 
 * @author terra.lian 
 */
public class JyunTyanta implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        if (divide == null) {
            return false;
        }
        // 不含字牌，且非清老头（有顺子，且是全带顺子）
        for (IHai hai : tehai.getAll())
            if (HaiTypeEnum.Z == hai.geHaiType())
                return false;

        // 雀头需要是幺九牌（非字牌）
        if (!divide.getJantou().isYaotyuHai())
            return false;

        int shunzuSize = divide.getAllShunzuFirst().size();
        return shunzuSize > 0
                // 顺子需要 1或7开头
                && divide.getAllKanKozuFirst().stream().allMatch(IHai::isYaotyuHai)
                // 刻子没有不是幺九牌的
                && divide.getAllShunzuFirst().stream().allMatch(this::isTyantaShunzuFirst);
    }

    @Override
    public String getName() {
        return YakuNamePool.JyunTyanta;
    }

    @Override
    public int getHan(boolean isNaki) {
        return isNaki ? 2 : 3;
    }

    /**
     * 是否是包含幺九顺子的第一枚
     * 
     * @param hai 牌
     */
    private boolean isTyantaShunzuFirst(IHai hai) {
        int literal = hai.getLiteral();
        return literal == 1 || literal == 7;
    }
}
