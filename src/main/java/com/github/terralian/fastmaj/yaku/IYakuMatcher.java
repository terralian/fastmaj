package com.github.terralian.fastmaj.yaku;

import java.util.ArrayList;
import java.util.List;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.IPlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;

/**
 * 役匹配器
 * <p/>
 * 根据游戏场景及手牌来匹配对应的役，役种决定根据初始化的不同而不同
 * <p/>
 * 部分会影响点数收益的役种不在该匹配器的判断范围内
 * <ul>
 * <li>流局满贯
 * </ul>
 *
 * @author terra.lian
 */
public interface IYakuMatcher {

    /**
     * 匹配役种
     *
     * @param tehai 手牌
     * @param divideInfo 分割信息
     * @param context 玩家游戏上下文
     */
    List<IYaku> match(ITehai tehai, DivideInfo divideInfo, IPlayerGameContext context);

    /**
     * 匹配役种，返回最大的役种匹配结果
     *
     * @param tehai 手牌
     * @param divideInfos 分割信息
     * @param context 玩家游戏上下文
     */
    default List<IYaku> match(ITehai tehai, List<DivideInfo> divideInfos, IPlayerGameContext context) {
        // 匹配单个的情况下，调用单个匹配方法
        if (divideInfos.size() == 1) {
            return match(tehai, divideInfos.get(0), context);
        }
        // 未匹配分割的情况下（如国士），分割信息增加一个默认为空
        if (divideInfos.isEmpty()) {
            return match(tehai, (DivideInfo) null, context);
        }

        // 多个分割的情况下，取分割役种番数最多的役种
        List<IYaku> bestYakus = new ArrayList<>();
        int biggestHan = 0;
        for (DivideInfo divideInfo : divideInfos) {
            // 进行匹配
            List<IYaku> currentYakus = match(tehai, divideInfo, context);
            // 若匹配到了役满，役满下，没有多种分割
            if (currentYakus.size() > 0 && currentYakus.stream().anyMatch(IYaku::isYakuman)) {
                bestYakus = currentYakus;
                break;
            }
            // 一般情况下，取最大的分割役种作为结果
            int currentHan = IYaku.sumHan(currentYakus, tehai.isNaki());
            if (biggestHan < currentHan) {
                bestYakus = currentYakus;
                biggestHan = currentHan;
            }
        }
        return bestYakus;
    }
}
