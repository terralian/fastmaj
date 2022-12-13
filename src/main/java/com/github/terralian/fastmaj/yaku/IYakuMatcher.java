package com.github.terralian.fastmaj.yaku;

import java.util.List;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;

/**
 * 役匹配器
 * <p>
 * 根据游戏场景及手牌来匹配对应的役，役种决定根据初始化的不同而不同
 * <p>
 * 部分会影响点数收益的役种不在该匹配器的判断范围内
 * <ul>
 * <li>流局满贯
 * </ul>
 * 
 * @author terra.lian 
 */
public interface IYakuMatcher {

    /**
     * 匹配役种，返回最大的役种匹配结果
     * 
     * @param tehai 手牌
     * @param divideInfos 分割信息
     * @param context 玩家游戏上下文
     */
    List<IYaku> match(ITehai tehai, List<DivideInfo> divideInfos, PlayerGameContext context);

    /**
     * 匹配役种
     * 
     * @param tehai 手牌
     * @param divideInfo 分割信息
     * @param context 玩家游戏上下文
     */
    List<IYaku> match(ITehai tehai, DivideInfo divideInfo, PlayerGameContext context);
}
