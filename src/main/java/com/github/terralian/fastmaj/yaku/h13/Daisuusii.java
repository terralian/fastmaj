package com.github.terralian.fastmaj.yaku.h13;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.player.RivalEnum;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.ITehaiLock;
import com.github.terralian.fastmaj.tehai.Menzu;
import com.github.terralian.fastmaj.yaku.IYakuBlame;
import com.github.terralian.fastmaj.yaku.IYakuman;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.JihaiYaku;

/**
 * 大四喜
 *
 * @author terra.lian
 */
@JihaiYaku
public class Daisuusii implements IYakuman, IYakuBlame {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        // 四喜牌每个都要>=3
        int[] value34 = Encode34.toEncode34(tehai.getAll());
        for (int i = Encode34.TON; i <= Encode34.PEI; i++) {
            if (value34[i] < 3)
                return false;
        }
        return true;
    }

    @Override
    public String getName() {
        return YakuNamePool.Daisuusii;
    }

    @Override
    public boolean isDoubleYakuman() {
        return true;
    }

    @Override
    public boolean usingBlameRule(GameConfig config) {
        return config.isUsingDaisuusiiBlameRule();
    }

    @Override
    public RivalEnum deduceBlameRival(ITehai tehai) {
        ITehaiLock tehaiLock = tehai.getLock();
        // 没有四副露不看
        if (tehaiLock.size() < 4) {
            return null;
        }
        // 大四喜，需要副露中包含东南西北四种类型，并且最后一种还需要是碰或者明杠的牌
        int count = 0;
        Menzu lastMenzu = null;
        for (int i = 0; i < tehaiLock.size(); i++) {
            Menzu currentMenzu = tehai.getLock().get(i);
            if (currentMenzu.getMenzuFirst().isKazeHai()) {
                count++;
                lastMenzu = currentMenzu;
            }
        }
        if (count < 4 || lastMenzu.isAnnkan()) {
            return null;
        }
        return lastMenzu.fromRival();
    }
}
