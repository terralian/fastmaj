package com.github.terralian.fastmaj.agari;

import java.util.Collection;
import java.util.List;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.util.RankingUtil;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.IYakuman;

/**
 * 默认的分数计算器
 *
 * @author terra.lian
 */
public class PointCalculator implements IPointCalculator {

    @Override
    public void ryuukyokuTransfer(Collection<Integer> tenpais, int basePoint, int[] playerPoints) {
        // 无人听牌
        if (tenpais.isEmpty() || tenpais.size() == playerPoints.length) {
            return;
        }
        // 参数正确校验
        if (tenpais.size() > playerPoints.length) {
            throw new IllegalArgumentException("听牌的人数大于牌局人数，请确认代码");
        }
        // 按听牌人数情况仅有3种， 1人听牌 未听牌付1000; 2人听牌 未听付1500; 3人听牌，未听付3000
        int decreasePoint = 3000 / (playerPoints.length - tenpais.size());
        int increasePoint = 3000 / tenpais.size();
        for (int i = 0; i < playerPoints.length; i++) {
            if (tenpais.contains(i))
                playerPoints[i] += increasePoint;
            else
                playerPoints[i] -= decreasePoint;
        }
    }

    @Override
    public void ryuukyokuTransfer(int position, int oya, int basePoint, int[] playerPoints) {
        int rivalSize = playerPoints.length - 1;
        // 若得分的人是庄家，得分点数 x 2
        if (position == oya) {
            int point = basePoint * 2;
            playerPoints[position] += point * rivalSize;
            // 闲家每人减底分 x 2
            for (int i = 0; i < playerPoints.length; i++) {
                if (position == i) {
                    continue;
                }
                playerPoints[i] -= point;
            }
        }
        // 若得分的是闲家
        else {
            int point = basePoint;
            int oyaDecrease = point * 2;
            // 赢的人获得庄*2，闲家*1的分数
            playerPoints[position] += oyaDecrease + point * (rivalSize - 1);
            for (int i = 0; i < playerPoints.length; i++) {
                if (position == i) {
                    continue;
                }
                // 庄家扣2倍
                playerPoints[i] -= (oya == i) ? oyaDecrease : point;
            }
        }
    }

    @Override
    public void gameEndRyuukyoku(int kyotaku, int[] playerPoints) {
        if (kyotaku == 0) {
            return;
        }
        int[] ranking = RankingUtil.calcRanking(playerPoints);
        for (int i = 0; i < playerPoints.length; i++) {
            if (ranking[i] == 1) {
                playerPoints[i] += kyotaku * 1000;
                break;
            }
        }
    }

    @Override
    public void tsumoTransfer(int position, int oya, int basePoint, int honba, int kyotaku, int[] playerPoints) {
        int rivalSize = playerPoints.length - 1;
        // 若得分的人是庄家，本场风由闲家出
        if (position == oya) {
            int point = ceilPoint(basePoint * 2) + honba * 100;
            // 庄家得分为底分两倍，及收立直棒
            playerPoints[position] += point * rivalSize + kyotaku * 1000;
            // 闲家每人减分
            for (int i = 0; i < playerPoints.length; i++) {
                if (position == i) {
                    continue;
                }
                playerPoints[i] -= point;
            }
        }
        // 若得分的是闲家，本场分由庄家出
        else {
            int point = ceilPoint(basePoint) + honba * 100;
            int oyaDecrease = ceilPoint(basePoint * 2) + honba * 100;
            // 赢的人获得庄*2，闲家*1的分数，及收立直棒
            playerPoints[position] += oyaDecrease + point * (rivalSize - 1) + kyotaku * 1000;
            for (int i = 0; i < playerPoints.length; i++) {
                if (position == i) {
                    continue;
                }
                // 庄家扣2倍
                playerPoints[i] -= (oya == i) ? oyaDecrease : point;
            }
        }
    }

    @Override
    public void ronTransfer(int position, int rivalPlayer, int oya, int basePoint, int honba, int kyotaku,
            int[] playerPoints) {
        // 自己为庄家的情况，得分为底分的两倍（取整）
        // 放铳者需要支付3人份的得分（在3麻也是）
        basePoint = ceilPoint(position == oya ? basePoint * 6 : basePoint * 4) + honba * 100 * 3;
        playerPoints[position] += basePoint + kyotaku * 1000;
        playerPoints[rivalPlayer] -= basePoint;
    }

    @Override
    public int calcBasePoint(int yakumanMultiple, int fu, int han) {
        // 役满，底分8000，若使用复合役满，则看复合的役满数
        if (yakumanMultiple > 0) {
            return yakumanMultiple * 8000;
        }
        // 非役满役
        if (han >= 13) return 8000;  // 累计役満
        else if (han >= 11) return 6000;  // 三倍満
        else if (han >= 8) return 4000;  // 倍満
        else if (han >= 6) return 3000;  // 跳満
        else if (han == 5) return 2000;  // 满贯
        // 满贯及以下
        int BP = (int) (fu * Math.pow(2, han + 2));
        return Math.min(2000, BP);
    }

    @Override
    public int calcBasePoint(List<IYaku> yakus, int fu, int han, GameConfig gameConfig) {
        int yakumanMultiple = calcYakumanMultiple(yakus, gameConfig);
        return calcBasePoint(yakumanMultiple, fu, han);
    }

    @Override
    public int ceilPoint(int point) {
        return (int) (Math.ceil(point / 100.0) * 100);
    }

    /**
     * 计算役满倍数，若非役满返回0
     *
     * @param yakus 和了的所有役
     * @param gameConfig 游戏配置
     */
    private int calcYakumanMultiple(List<IYaku> yakus, GameConfig gameConfig) {
        if (yakus.get(0).isYakuman()) {
            int multiple = 1;
            // 复合役满及多倍役满
            if (gameConfig.isMultipleYakuman() && gameConfig.isDoubleYakuman()) {
                multiple = yakus.stream().mapToInt(k -> ((IYakuman) k).isDoubleYakuman() ? 2 : 1).sum();
            }
            // 只计复合役满
            else if (gameConfig.isMultipleYakuman()) {
                multiple = yakus.size();
            }
            return multiple;
        }
        return 0;
    }
}
