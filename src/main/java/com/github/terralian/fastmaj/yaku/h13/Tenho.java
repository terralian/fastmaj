package com.github.terralian.fastmaj.yaku.h13;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.IPlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.ITehaiLock;
import com.github.terralian.fastmaj.yaku.IYakuman;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.RequestContextYaku;

/**
 * 天和
 *
 * @author 作者: terra.lian
 */
@RequestContextYaku
public class Tenho implements IYakuman {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, IPlayerGameContext holder) {
        ITehaiLock lock = tehai.getLock();
        return holder != null && holder.isOya() // 是庄家
                && lock.isEmpty() // 未鸣牌且未暗杠
                && tehai.countKita() == 0 // 未拔北
                && holder.getHaiRiver().size() == 0; // 没有打过牌
    }

    @Override
    public String getName() {
        return YakuNamePool.Tenho;
    }
}
