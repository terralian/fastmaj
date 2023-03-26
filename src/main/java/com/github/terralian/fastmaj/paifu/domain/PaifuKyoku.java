package com.github.terralian.fastmaj.paifu.domain;

import java.util.List;

import com.github.terralian.fastmaj.game.KazeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author terra.lian
 * @since 2023-03-21
 */
@Data
@Accessors(chain = true)
public class PaifuKyoku {

    /**
     * 场风
     */
    private KazeEnum bakaze;

    /**
     * 牌山
     */
    private int[] yamas;

    /**
     * 庄家
     */
    private int oya;

    /**
     * 每个玩家对局开始时的分数
     */
    private int[] startPoints;

    /**
     * 每个玩家的结束时的分数
     */
    private int[] endPoints;

    /**
     * 场供
     * <p/>
     * 上局及之前积累在场上的立直棒
     */
    private int kyotaku;

    /**
     * 本场数
     * <p/>
     * 东一局0本场 = [ 场风: 东，本场数: 0 ]
     */
    private int honba;

    /**
     * 该对局的所有动作
     */
    private List<PaifuAction> actions;

    /**
     * 是否是（东/南）某局. 如东4局，那么isEqual(KazeEnum.Don, 4)
     *
     * @param bakaze 场风
     * @param kyokuNumber 第几局
     */
    public boolean isKyoku(KazeEnum bakaze, int kyokuNumber) {
        return this.bakaze == bakaze && (kyokuNumber - 1) == oya;
    }
}
