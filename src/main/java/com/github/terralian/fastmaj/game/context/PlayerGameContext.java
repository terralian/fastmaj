package com.github.terralian.fastmaj.game.context;

import java.util.List;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.KazeEnum;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.player.space.PlayerDefaultSpace;
import com.github.terralian.fastmaj.player.space.PlayerPublicSpace;
import com.github.terralian.fastmaj.river.IHaiRiver;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.ITehaiLock;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 玩家的游戏上下文，包含游戏信息及当前玩家可见的自身信息及其他玩家的公开信息
 *
 * @author terra.lian
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PlayerGameContext implements IPlayerGameContext {

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
    private int honba;
    /**
     * 本场数（实际值）
     */
    private int realHonba;
    /**
     * 场供
     */
    private int kyotaku;
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
    private boolean endByRon = false;

    // -------------------------------------------
    // 玩家空间
    // -------------------------------------------

    /**
     * 你的玩家空间（包含私有空间信息）
     */
    private PlayerDefaultSpace space;

    /**
     * 所有玩家的公开空间信息
     */
    private List<PlayerPublicSpace> publicSpaces;

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
