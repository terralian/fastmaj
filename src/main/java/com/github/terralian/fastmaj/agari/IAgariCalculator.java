package com.github.terralian.fastmaj.agari;

import java.util.List;

import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;

/**
 * 和了时计算器
 * 
 * @author terra.lian 
 */
public interface IAgariCalculator {

    /**
     * 和了计算
     * 
     * @param tehai 手牌，已包含和了牌
     * @param agariHai 和了牌
     * @param fromPlayer 来自玩家，自摸则和自己一致
     * @param doraHais 宝牌
     * @param uraDoraHais 里宝牌，当没有立直时，为空
     * @param context 玩家游戏上下文
     */
    AgariInfo calc(ITehai tehai, IHai agariHai, Integer fromPlayer, List<IHai> doraHais, List<IHai> uraDoraHais,
            PlayerGameContext context);
}
