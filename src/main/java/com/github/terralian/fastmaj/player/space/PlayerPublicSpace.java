package com.github.terralian.fastmaj.player.space;

import com.github.terralian.fastmaj.game.KazeEnum;
import com.github.terralian.fastmaj.game.event.river.RiverActionEvent;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;
import com.github.terralian.fastmaj.river.IHaiRiver;
import com.github.terralian.fastmaj.tehai.ITehaiLock;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 玩家的公开空间，这些信息对所有玩家公开展示。
 * <p/>
 * 该空间包含了游戏开始前的玩家静态信息{@link PlayerStaticSpace} 以及会随游戏进行发生变动的动态信息组合而成。
 *
 * @author Terra.Lian
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerPublicSpace extends PlayerStaticSpace {

    /**
     * 玩家当前分数信息
     */
    private int playerPoint = 0;
    /**
     * 玩家当前的排名
     */
    private int ranking = -1;

    /**
     * 副露信息
     */
    private ITehaiLock tehaiLock;
    /**
     * 玩家的牌河信息
     */
    private IHaiRiver haiRiver;

    /**
     * 是否是庄家
     */
    private boolean oya = false;
    /**
     * 是否立直
     */
    private boolean reach = false;
    /**
     * 玩家的自风
     */
    private KazeEnum jikaze;

    /**
     * 玩家的最近一个手牌动作，不包含摸牌
     */
    private TehaiActionEvent lastTehaiAction;
    /**
     * 玩家的最近一个牌河动作
     */
    private RiverActionEvent lastRiverAction;

    @Override
    public void copyTo(IPlayerSpace playerSpace) {
        if (playerSpace instanceof PlayerPublicSpace) {
            PlayerPublicSpace publicSpace = (PlayerPublicSpace) playerSpace;
            publicSpace.setPlayerPoint(playerPoint);
            publicSpace.setRanking(ranking);
            publicSpace.setTehaiLock(tehaiLock);
            publicSpace.setHaiRiver(haiRiver); // TODO
            publicSpace.setReach(reach);
            publicSpace.setJikaze(jikaze);
            publicSpace.setLastTehaiAction(lastTehaiAction);
            publicSpace.setLastRiverAction(lastRiverAction);
        }
        super.copyTo(playerSpace);
    }
}
