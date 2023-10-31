package com.github.terralian.fastmaj.game.context;

import java.util.List;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.KazeEnum;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.player.space.PlayerDefaultSpace;
import com.github.terralian.fastmaj.player.space.PlayerPublicSpace;
import com.github.terralian.fastmaj.river.IHaiRiver;
import com.github.terralian.fastmaj.tehai.ITehai;

/**
 * 玩家游戏上下文
 *
 * @author terra.lian
 */
public interface IPlayerGameContext {

    // --------------------------------------------
    // 游戏定义
    // --------------------------------------------

    /**
     * 游戏规则
     */
    GameConfig getGameConfig();

    /**
     * 获取玩家数量
     */
    int getPlayerSize();

    // --------------------------------------------
    // 游戏变量
    // --------------------------------------------

    /**
     * 获取到当前为止的对局数，回合数从1开始，连庄、中途流局等都会增加对局数
     */
    int getRound();

    /**
     * 获取当前场风
     */
    KazeEnum getBakaze();

    /**
     * 获取本场数（展示值）
     */
    int getHonba();

    /**
     * 获取本场数（实际值）TODO，该值并不应该出现在玩家上下文
     */
    int getRealHonba();

    /**
     * 获取场供，遗留在场上的立直棒
     */
    int getKyotaku();

    /**
     * 当前庄家
     */
    int getOya();

    /**
     * 是否是庄家
     */
    default boolean isOya() {
        return getOya() == getPosition();
    }

    /**
     * 获取牌山剩余数量
     */
    int getYamaCountdown();

    /**
     * 获取当前的所有宝牌
     * <p/>
     * 可通过宝牌类计算里宝牌
     */
    List<IHai> getDoraDisplays();

    // --------------------------------------------
    // 你的信息
    // --------------------------------------------

    /**
     * 获取你的玩家空间的信息
     */
    PlayerDefaultSpace getSpace();

    /**
     * 你的坐席
     */
    default int getPosition() {
        return getSpace().getPosition();
    }

    /**
     * 你的手牌
     */
    default ITehai getTehai() {
        return getSpace().getTehai();
    }

    /**
     * 你的牌河
     */
    default IHaiRiver getHaiRiver() {
        return getSpace().getHaiRiver();
    }

    /**
     * 你的自风
     */
    default KazeEnum getJikaze() {
        return getSpace().getJikaze();
    }

    /**
     * 你是否处于振听状态
     */
    default boolean isFuriten() {
        return getSpace().isFuriten();
    }

    /**
     * 你是否处于立直状态
     */
    default boolean isReach() {
        return getSpace().isReach();
    }

    /**
     * 你手牌的向听数
     */
    default int getSyaten() {
        return getSpace().getSyaten();
    }

    /**
     * 你是否副露了
     */
    default boolean isNaki() {
        return getSpace().getHaiRiver().hasNaki();
    }

    // --------------------------------------------
    // 公开信息
    // --------------------------------------------

    /**
     * 获取所有玩家的玩家公开空间
     * <p/>
     * 该玩家空间中记录了所有玩家可公开的信息，包含最后一个动作信息
     */
    List<PlayerPublicSpace> getPublicSpaces();

    /**
     * 获取某个玩家的玩家公开空间
     *
     * @param position 玩家坐席
     */
    default PlayerPublicSpace getPublicSpace(int position) {
        return getPublicSpaces().get(position);
    }

    /**
     * 将所有玩家的分数转换复制为数组
     */
    default int[] copyPlayerPoints() {
        int[] res = new int[getPlayerSize()];
        for (int i = 0; i < getPlayerSize(); i++) {
            res[i] = getPublicSpace(i).getPlayerPoint();
        }
        return res;
    }

    // --------------------------------------------
    // 结束信息
    // --------------------------------------------

    /**
     * 是否荣和
     */
    boolean isEndByRon();
}
