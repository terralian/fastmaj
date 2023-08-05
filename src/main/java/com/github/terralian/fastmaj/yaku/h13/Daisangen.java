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
 * 大三元
 *
 * @author terra.lian
 */
@JihaiYaku
public class Daisangen implements IYakuman, IYakuBlame {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        // 中发白每个都大于3
        int[] value34 = Encode34.toEncode34(tehai.getAll());
        return value34[Encode34.HAKU] >= 3 && value34[Encode34.HATU] >= 3 && value34[Encode34.TYUN] >= 3;
    }

    @Override
    public String getName() {
        return YakuNamePool.Daisangen;
    }

    @Override
    public boolean usingBlameRule(GameConfig config) {
        return config.isUsingDaisangenBlameRule();
    }

    @Override
    public RivalEnum deduceBlameRival(ITehai tehai) {
        ITehaiLock tehaiLock = tehai.getLock();
        // 没有三副露不看
        if (tehaiLock.size() < 3) {
            return null;
        }
        // 大三元，需要副露中包含中发白三种类型，并且最后一种还需要是碰或者明杠的牌
        int count = 0;
        Menzu lastMenzu = null;
        for (int i = 0; i < tehaiLock.size(); i++) {
            Menzu currentMenzu = tehai.getLock().get(i);
            if (currentMenzu.getMenzuFirst().isSanGenHai()) {
                count++;
                lastMenzu = currentMenzu;
            }
        }
        if (count < 3 || lastMenzu.isAnnkan()) {
            return null;
        }
        return lastMenzu.fromRival();
    }
}
