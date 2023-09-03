package com.github.terralian.fastmaj.yaku.h13;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.context.IPlayerGameContext;
import com.github.terralian.fastmaj.player.RivalEnum;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.ITehaiLock;
import com.github.terralian.fastmaj.tehai.Menzu;
import com.github.terralian.fastmaj.yaku.IYakuBlame;
import com.github.terralian.fastmaj.yaku.IYakuman;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.KozuYaku;

/**
 * 四杠子
 *
 * @author 作者: terra.lian
 */
@KozuYaku
public class Suukanzu implements IYakuman, IYakuBlame {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, IPlayerGameContext holder) {
        ITehaiLock tehaiLock = tehai.getLock();
        return tehaiLock.kanzuSize() == 4;
    }

    @Override
    public String getName() {
        return YakuNamePool.Suukanzu;
    }

    @Override
    public boolean usingBlameRule(GameConfig config) {
        return config.isUsingSuukanzuBlameRule();
    }

    @Override
    public RivalEnum deduceBlameRival(ITehai tehai) {
        ITehaiLock lock = tehai.getLock();
        // 没有4副露不看
        if (lock.size() < 4) return null;
        int count = 0;
        Menzu lastMenu = null;
        for (int i = 0; i < lock.size(); i++) {
            Menzu currentMenzu = lock.get(i);
            if (currentMenzu.isKanzu()) {
                count++;
                lastMenu = currentMenzu;
            }
        }
        if (count < 4 || lastMenu.isAnnkan()) {
            return null;
        }
        return lastMenu.fromRival();
    }
}
