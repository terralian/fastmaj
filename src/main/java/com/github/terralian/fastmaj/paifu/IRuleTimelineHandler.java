package com.github.terralian.fastmaj.paifu;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.paifu.domain.PaifuGame;

/**
 * 规则在时间线下变更的处理器，比如天凤10年6月1日导入了听牌也自动结束的规则（之前连庄），这部分在时间段上的差异仅体现在
 * 能够知晓时间的信息上。该接口的作用即为处于不同时间的牌谱规则进行兼容
 *
 * @author Terra.Lian
 */
public interface IRuleTimelineHandler {

    /**
     * 进行推断
     *
     * @param gameConfig 规则
     * @param paifuGame 牌谱
     * @param params 更多的参数，由子类定义规则
     */
    void deduce(GameConfig gameConfig, PaifuGame paifuGame, String... params);
}
