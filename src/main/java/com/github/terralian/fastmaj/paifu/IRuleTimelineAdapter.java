package com.github.terralian.fastmaj.paifu;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.paifu.domain.PaifuGame;

/**
 * 平台规则在时间线下变更的适配器接口
 * <p/>
 * 比如天凤10年6月1日导入了听牌也自动结束的规则（之前连庄），这部分在时间段上的差异仅体现在
 * 能够知晓时间的信息上。该接口的作用即为处于不同时间的牌谱规则进行兼容。
 *
 * @author terra.lian
 */
public interface IRuleTimelineAdapter {

    /**
     * 进行配置适配
     *
     * @param gameConfig 规则
     * @param paifuGame 解析后的牌谱
     * @param params 更多的参数，由子类定义规则
     */
    void adaptConfig(GameConfig gameConfig, PaifuGame paifuGame, String... params);
}
