package com.github.terralian.fastmaj.paifu.domain;

import java.util.List;

import com.github.terralian.fastmaj.game.KazeEnum;
import lombok.Data;

/**
 * @author terra.lian
 * @since 2023-03-21
 */
@Data
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
     * 每个玩家的分数
     */
    private int[] points;

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
}
