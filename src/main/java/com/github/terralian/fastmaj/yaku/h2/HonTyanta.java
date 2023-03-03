package com.github.terralian.fastmaj.yaku.h2;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;

/**
 * 混全带
 * <p/>
 * 混全带和混老头并不会复合，因为混全带需要带有顺子
 * 
 * @author terra.lian
 */
public class HonTyanta implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        // 无分割情况下，不会是混全带，如国士
        if (divide == null) {
            return false;
        }
        // 存在字牌，存在顺子，且所有顺子或刻子为幺九
        if (tehai.getAll().stream().noneMatch(k -> k.isJiHai())) {
            return false;
        }
        // 雀头要是幺九牌
        if (!divide.getJantou().isYaotyuHai()) {
            return false;
        }

        int shuntsuSize = divide.getAllShuntsuFirst().size();
        // 有顺子
        //
        // 刻子需要全是幺九
        if (shuntsuSize > 0 //
                && divide.getAllKanKotsuFirst().stream().allMatch(k -> k.isYaotyuHai())
                && divide.getAllShuntsuFirst().stream().allMatch(k -> isTyantaShuntsuFirst(k)) // 顺子需要 1或7开头
        ) {
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return YakuNamePool.Tyanta;
    }

    @Override
    public int getHan(boolean isNaki) {
        return isNaki ? 1 : 2;
    }

    /**
     * 顺子第一枚是否为1或者7
     * 
     * @param hai 牌
     */
    private boolean isTyantaShuntsuFirst(IHai hai) {
        int literal = hai.getLiteral();
        return literal == 1 || literal == 7;
    }
}
