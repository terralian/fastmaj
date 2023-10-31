package com.github.terralian.fastmaj.player.space;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.github.terralian.fastmaj.game.event.ActionEvent;
import com.github.terralian.fastmaj.game.event.river.RiverActionEvent;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;
import com.github.terralian.fastmaj.player.IPlayer;
import com.github.terralian.fastmaj.tehai.ITehai;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 由多种不同类型的玩家空间组成的默认玩家空间完全体，一般由玩家自身及系统持有。
 * <p/>
 * 与逻辑无关，规则部分的值初始化需要由外部进行设置，如自风，是否庄家等。
 *
 * @author terra.lian
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
     * <p/>
     * 同巡振听、立直振听、切牌振听
     */
    private boolean furiten = false;

    /**
     * 玩家的当局动作序列，每个新动作会放在队列的最前面
     */
    private List<ActionEvent> actionEventList = new LinkedList<>();

    /**
     * 玩家动作事件记录到动作序列
     *
     * @param actionEvent 动作事件
     */
    public void addAction(ActionEvent actionEvent) {
        actionEventList.add(0, actionEvent);

        // 公开空间覆盖上一个动作类型，可通过该动作判定一些役种
        if (actionEvent instanceof TehaiActionEvent)
            this.setLastTehaiAction(((TehaiActionEvent) actionEvent));
        else if (actionEvent instanceof RiverActionEvent)
            this.setLastRiverAction(((RiverActionEvent) actionEvent));
    }

    /**
     * 增加假设的动作，使得可以根据动作进行一些判定
     *
     * @param actionEvent 动作
     */
    public void addIfAction(ActionEvent actionEvent) {
        actionEventList.add(0, actionEvent);
    }

    /**
     * 移除假设的动作
     */
    public void removeIfAction() {
        actionEventList.remove(0);
    }

    /**
     * 获取上一个动作
     */
    public ActionEvent getLastAction() {
        return actionEventList.get(0);
    }

    /**
     * 获取上上个动作
     *
     * @return {@link ActionEvent} | null
     */
    public ActionEvent getSecondLastAction() {
        return actionEventList.size() >= 1 //
                ? actionEventList.get(1)
                : null;
    }

    @Override
    public void copyTo(IPlayerSpace playerSpace) {
        if (playerSpace instanceof PlayerDefaultSpace) {
            PlayerDefaultSpace defaultSpace = (PlayerDefaultSpace) playerSpace;
            defaultSpace.setSelf(self);
            defaultSpace.setTehai(tehai.deepClone());
            defaultSpace.setSyaten(syaten);
            defaultSpace.setFuriten(furiten);
            defaultSpace.setActionEventList(new ArrayList<>(actionEventList));
        }
        super.copyTo(playerSpace);
    }

    /**
     * 重置对局信息，令可以进行下一局游戏
     */
    public void resetKyokuState() {
        this.setTehaiLock(null);
        if (this.getHaiRiver() != null) {
            this.getHaiRiver().clear();
        }
        this.setReach(false);
        this.setLastTehaiAction(null);
        this.setLastRiverAction(null);
        this.setOya(false);
        this.setJikaze(null);

        this.tehai = null;
        this.syaten = MAX_SYATEN;
        this.furiten = false;
        this.actionEventList.clear();
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
