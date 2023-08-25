package com.github.terralian.fastmaj.player.space;

import com.github.terralian.fastmaj.game.event.ActionEvent;
import com.github.terralian.fastmaj.game.event.river.RiverActionEvent;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;
import com.github.terralian.fastmaj.player.IPlayer;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.util.Assert;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 由多种不同类型的玩家空间组成的默认玩家空间完全体，一般由玩家自身及系统持有。
 * <p/>
 * 与逻辑无关，规则部分的值初始化需要由外部进行设置，如自风，是否庄家等。
 *
 * @author Terra.Lian
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerDefaultSpace extends PlayerPublicSpace implements IPlayerPrivateSpace {

    /**
     * 表示玩家本人，返回一个可以进行事件处理的玩家接口
     */
    private IPlayer self;
    /**
     * 玩家的手牌信息
     */
    private ITehai tehai;
    /**
     * 最大向听数
     */
    private static final int MAX_SYATEN = 13;
    /**
     * 向听数
     */
    private int syaten = MAX_SYATEN;

    /**
     * 是否振听
     */
    private boolean furiten = false;

    /**
     * 上一个动作
     */
    private ActionEvent lastAction;

    /**
     * 设置手牌信息，并会同时取出副露信息放到公开空间
     *
     * @param tehai 手牌
     */
    public void setTehai(ITehai tehai) {
        this.tehai = tehai;
        this.setTehaiLock(this.tehai == null ? null : tehai.getLock());
    }

    /**
     * 设置上一个动作，并同时公开上一个动作类型（手牌/牌河）
     *
     * @param lastAction 上一个动作
     */
    public void setLastAction(ActionEvent lastAction) {
        Assert.notNull(lastAction, "上一个动作不能为空");

        this.lastAction = lastAction;
        if (lastAction instanceof TehaiActionEvent)
            this.setLastTehaiActionType(((TehaiActionEvent) lastAction).getEventType());
        else
            this.setLastRiverActionType(((RiverActionEvent) lastAction).getEventType());
    }

    @Override
    public void copyTo(IPlayerSpace playerSpace) {
        if (playerSpace instanceof PlayerDefaultSpace) {
            PlayerDefaultSpace defaultSpace = (PlayerDefaultSpace) playerSpace;
            defaultSpace.setSelf(self);
            defaultSpace.setTehai(tehai.deepClone());
            defaultSpace.setSyaten(syaten);
            defaultSpace.setFuriten(furiten);
            defaultSpace.setLastAction(lastAction);
        }
        super.copyTo(playerSpace);
    }

    /**
     * 重置对局信息，令可以进行下一局游戏
     */
    public void resetKyokuState() {
        this.setTehaiLock(null);
        this.setHaiRiver(null);
        this.setReach(false);
        this.setLastTehaiActionType(null);
        this.setLastRiverActionType(null);
        this.setOya(false);
        this.setJikaze(null);

        this.tehai = null;
        this.syaten = MAX_SYATEN;
        this.furiten = false;
        this.lastAction = null;
    }

    /**
     * 重置游戏信息，令可以开始下一次游戏
     */
    public void resetGameState() {
        this.resetKyokuState();

        this.setPlayerPoint(0);
        this.setRanking(-1);
    }
}
