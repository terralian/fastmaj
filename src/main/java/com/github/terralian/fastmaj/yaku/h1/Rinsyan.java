package com.github.terralian.fastmaj.yaku.h1;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.action.river.RiverActionType;
import com.github.terralian.fastmaj.game.action.river.RiverActionValue;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;

/**
 * 岭上开花
 * 
 * @author 作者: terra.lian
 * 
 */
public class Rinsyan implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        if (holder == null || holder.isRon()) {
            return false;
        }
        // 先判定是否明杠
        RiverActionValue riverAction = holder.getLastRiverAction();
        if (riverAction!= null && riverAction.getActionPlayer() == holder.getPosition()) {
            if (riverAction.getActionType() == RiverActionType.MINKAN) {
                return true;
            }
        }
        // 再判定是否暗杠，加杠
        TehaiActionType tehaiAction = holder.getLastTehaiActionType();
        if (tehaiAction == null) {
            return false;
        }
        return tehaiAction == TehaiActionType.ANNKAN // 暗杠
                || tehaiAction == TehaiActionType.KAKAN;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 1;
    }

    @Override
    public String getName() {
        return YakuNamePool.Rinsyan;
    }
}
