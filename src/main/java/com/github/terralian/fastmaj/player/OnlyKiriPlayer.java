package com.github.terralian.fastmaj.player;

import java.util.Set;

import com.github.terralian.fastmaj.game.action.river.RiverActionType;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.game.event.tehai.KiriEvent;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;
import com.github.terralian.fastmaj.tehai.ITehai;

/**
 * 一个只会模切的玩家
 * <p/>
 * 该类实现的玩家只会将摸到的牌打出去，常用于调试
 * 
 * @author terra.lian 
 */
public class OnlyKiriPlayer implements IPlayer {

    @Override
    public TehaiActionEvent drawHai(ITehai tehai, Set<TehaiActionType> enableActions, PlayerGameContext context) {
        return new KiriEvent().setKiriHai(tehai.getDrawHai());
    }

    @Override
    public RiverActionCall nakiOrRon(int fromPosition, TehaiActionType fromDealAction, Set<RiverActionType> enableActions,
            PlayerGameContext context) {
        return null;
    }
}
