package com.github.terralian.fastmaj.yaku.h1;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.IPlayerGameContext;
import com.github.terralian.fastmaj.game.event.ActionEvent;
import com.github.terralian.fastmaj.game.event.DrawEvent;
import com.github.terralian.fastmaj.player.space.PlayerDefaultSpace;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.RequestContextYaku;
import com.github.terralian.fastmaj.yama.DrawFrom;

/**
 * 海底摸月
 * <p/>
 * 海底摸月为山牌的最后一枚牌自摸（非岭上牌）
 *
 * @author 作者: terra.lian
 */
@RequestContextYaku
public class Haitei implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, IPlayerGameContext holder) {
        if (holder == null || holder.getYamaCountdown() > 0) {
            return false;
        }
        // TODO，现阶段自摸事件处理和荣和事件没有对齐
        // 自摸事件是玩家动作发出后，事件未加入到玩家空间，就执行了动作
        // 荣和事件是先加入到玩家空间，才执行了动作，后续需要调整自摸事件的处理。
        // 海底，河底
        PlayerDefaultSpace defaultSpace = holder.getSpace();
        // 岭上和海底不复合
        ActionEvent lastAction = defaultSpace.getLastAction();
        if (lastAction instanceof DrawEvent) {
            DrawEvent drawEvent = (DrawEvent) lastAction;
            return drawEvent.getDrawFrom() == DrawFrom.YAMA;
        }
        return false;
    }

    @Override
    public String getName() {
        return YakuNamePool.Haitei;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 1;
    }
}
