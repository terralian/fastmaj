package com.github.terralian.fastmaj.agari;

import java.util.List;

import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;

/**
 * 符数计算接口
 * 
 * @author 作者: terra.lian
 * 
 */
public interface IFuCalculator {

    /**
     * 计算符数
     * 
     * @param tehai 手牌
     * @param agariHai 和的牌
     * @param divideInfos 拆分的手牌
     * @param ron 是否荣和（True为是，False为否）
     * @param context 玩家游戏上下文
     */
    int compute(ITehai tehai, IHai agariHai, List<DivideInfo> divideInfos, PlayerGameContext context);


    /**
     * 计算符数
     * 
     * @param tehai 手牌
     * @param agariHai 和的牌
     * @param divideInfos 拆分的手牌
     * @param ron 是否荣和（True为是，False为否）
     * @param context 玩家游戏上下文
     */
    int compute(ITehai tehai, IHai agariHai, DivideInfo divideInfo, PlayerGameContext context);
}
