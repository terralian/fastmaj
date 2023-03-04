package com.github.terralian.fastmaj.game.context;

import java.util.List;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.KazeEnum;
import com.github.terralian.fastmaj.game.action.river.RiverActionValue;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.river.IHaiRiver;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.ITehaiLock;
import com.github.terralian.fastmaj.yama.DrawFrom;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 玩家的游戏上下文
 * 
 * @author terra.lian 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PlayerGameContext {

    // --------------------------------------------
    // 游戏定义
    // --------------------------------------------

    /**
     * 游戏规则
     */
    private GameConfig gameConfig;
    /**
     * 玩家数量
     */
    private int playerSize;

    // --------------------------------------------
    // 当局临时变量
    // --------------------------------------------

    /**
     * 当前对局数（依照局数本场数，递增）
     */
    private int round;
    /**
     * 当前动作数（摸牌动作，手牌动作和弃牌动作都会增加动作数）
     */
    private int actionCount;
    /**
     * 当局场风
     */
    private KazeEnum bakaze;
    /**
     * 本场数（展示值）
     */
    private Integer honba;
    /**
     * 本场数（实际值）
     */
    private Integer realHonba;
    /**
     * 场供
     */
    private Integer kyotaku;
    /**
     * 当前庄家
     */
    private int oya;
    /**
     * 所有玩家的分数
     */
    private int[] playerPoints;
    /**
     * 牌山剩余
     */
    private int yamaCountdown;
    /**
     * 当前宝牌
     */
    private List<IHai> doraDisplays;

    /**
     * 所有玩家的牌河
     */
    private List<IHaiRiver> haiRivers;
    /**
     * 所有玩家手牌固定区（鸣牌区）
     */
    private List<ITehaiLock> tehaiLocks;

    // --------------------------------------------
    // 临时标记
    // --------------------------------------------

    /**
     * 你的坐席
     */
    private int position;
    /**
     * 自风
     */
    private KazeEnum jikaze;
    /**
     * 是否振听
     */
    private boolean isFuriten = false;
    /**
     * 当前向听数
     */
    private int syaten;
    /**
     * 玩家的手牌
     */
    private ITehai tehai;

    // -------------------------------------------
    // 和了信息
    // -------------------------------------------

    /**
     * 和了时，和了的牌
     */
    private IHai agariHai;
    /**
     * 是否荣和
     */
    private boolean isRon = false;
    /**
     * 最近玩家的手牌动作
     */
    private TehaiActionType lastTehaiActionType;
    /**
     * 最近玩家的牌河动作
     */
    private RiverActionValue lastRiverAction;
    /**
     * 最近一名玩家的最佳一次摸牌来源
     */
    private DrawFrom lastDrawFrom;

    // -------------------------------------------
    // 快捷方法
    // -------------------------------------------

    /**
     * 返回玩家是否鸣牌
     */
    public boolean isNaki() {
        return tehai.isNaki();
    }

    /**
     * 是否是庄家
     */
    public boolean isOya() {
        return position == getOya();
    }

    /**
     * 返回自风
     */
    public KazeEnum jikaze() {
        return jikaze;
    }

    /**
     * 是否荣和
     */
    public boolean isRon() {
        return isRon;
    }

    /**
     * 该玩家（你）的当前分数
     */
    public int getPlayerPoint() {
        return getPlayerPoints()[position];
    }

    /**
     * 该玩家（你）的牌河
     */
    public IHaiRiver getHaiRiver() {
        return getHaiRivers().get(position);
    }

    /**
     * 获取某个玩家的牌河
     * 
     * @param position 玩家坐席
     */
    public IHaiRiver getHaiRiver(int position) {
        return getHaiRivers().get(position);
    }
}
