package com.github.terralian.fastmaj.game;

import com.github.terralian.fastmaj.game.action.river.RiverActionType;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.game.option.DoraAddRule;
import com.github.terralian.fastmaj.game.option.ReachRule;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 游戏可自定义配置
 * 
 * @author terra.lian
 */
@Data
@Accessors(chain = true)
public class GameConfig {

    // ------------------------------------------------
    // 游戏规则选项
    // ------------------------------------------------

    /**
     * 玩家人数
     */
    private int playerSize = 4;
    /**
     * 是否含红宝牌规则
     */
    private boolean useRedHai = true;
    /**
     * 是否是否使用里宝牌的规则
     */
    private boolean useUraDora = true;
    /**
     * 单局胜负，为true时，对局仅有一局
     */
    private Boolean singleKyoku = false;
    /**
     * 结束的场风，东风战 = 东风，至多到南风结束
     */
    private KazeEnum endBakaze = KazeEnum.NAN;

    /**
     * 暗杠宝牌规则
     */
    private DoraAddRule newDoraByAnnkanRule = DoraAddRule.BEFORE_KIRI;
    /**
     * 加杠宝牌规则
     */
    private DoraAddRule newDoraByKakanRule = DoraAddRule.AFTER_KIRI;
    /**
     * 明杠宝牌规则
     */
    private DoraAddRule newDoraByMinkanRule = DoraAddRule.AFTER_KIRI;

    /**
     * 立直规则
     */
    private ReachRule reachRule = ReachRule.YAMA1;
    /**
     * 需要有足够的分数才可以进行立直操作（1000）
     */
    private Boolean reachNeedEnoughPoint = true;
    /**
     * 双倍役满规则，如部分规则中四暗刻单骑，国士十三面，纯九莲计双倍。
     */
    private Boolean doubleYakuman = false;
    /**
     * 复合役满规则
     */
    private Boolean mutiYakuman = true;
    /**
     * 玩家的开始分数
     */
    private Integer startPoint = 25000;
    /**
     * 存在玩家达到这个分数才能结束游戏，否则南入/西入
     */
    private Integer endPointLine = 30000;

    /**
     * 特殊流局：使用三家和了流局（天凤）
     */
    private Boolean useRon3Ryuukyoku = true;

    // ------------------------------------------------
    // 游戏行为配置
    // ------------------------------------------------


    // ------------------------------------------------
    // 方法区
    // ------------------------------------------------

    /**
     * 使用默认的规则
     */
    public static GameConfig defaultRule() {
        return new GameConfig();
    }

    /**
     * 根据配置判定是否需要翻新宝牌
     * 
     * @param timePoint 增加时机
     * @param tehaiActionType 手牌的动作（可为空）
     * @param riverActionType 牌河的动作（可为空）
     */
    public boolean newDoraAction(DoraAddRule timePoint, TehaiActionType tehaiActionType, RiverActionType riverActionType) {
        if (tehaiActionType == null && riverActionType == null) {
            return false;
        }
        if (riverActionType != null && riverActionType == RiverActionType.MINKAN) {
            return getNewDoraByMinkanRule() == timePoint;
        }
        if (tehaiActionType == TehaiActionType.ANNKAN) {
            return getNewDoraByAnnkanRule() == timePoint;
        }
        if (tehaiActionType == TehaiActionType.KAKAN) {
            return getNewDoraByKakanRule() == timePoint;
        }
        return false;
    }
}
