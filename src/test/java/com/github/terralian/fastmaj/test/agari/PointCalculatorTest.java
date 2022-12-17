package com.github.terralian.fastmaj.test.agari;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.terralian.fastmaj.agari.IPointCalculator;
import com.github.terralian.fastmaj.agari.PointCalculator;
import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h13.Daisuusii;
import com.github.terralian.fastmaj.yaku.h13.Kokusi;
import com.github.terralian.fastmaj.yaku.h13.Kokusi13;
import com.github.terralian.fastmaj.yaku.h13.SuuankouTanki;
import com.github.terralian.fastmaj.yaku.h13.Suukantu;
import com.github.terralian.fastmaj.yaku.h13.Tenho;
import com.github.terralian.fastmaj.yaku.h13.Tuuiisou;

/**
 * {@link PointCalculator}分数计算单元测试
 * 
 * @author terra.lian
 * @since 2022-10-27
 */
public class PointCalculatorTest {

    private IPointCalculator pointCalculator;

    @Before
    public void before() {
        pointCalculator = new PointCalculator();
    }

    /**
     * 普通流局
     */
    @Test
    public void ryuukyokuTransfer_normal() {
        int basePoint = 1000;
        // 1人听牌
        int[] playerPoints = new int[] {25000, 25000, 25000, 25000};
        List<Integer> tenpais = Arrays.asList(0);
        pointCalculator.ryuukyokuTransfer(tenpais, basePoint, playerPoints);
        checkPointArrayValue(playerPoints, 25000 + 3000, 25000 - 1000, 25000 - 1000, 25000 - 1000);

        // 两人听牌
        playerPoints = new int[] {26500, 26300, 34100, 13100};
        tenpais = Arrays.asList(2, 3);
        pointCalculator.ryuukyokuTransfer(tenpais, basePoint, playerPoints);
        checkPointArrayValue(playerPoints, 26500 - 1500, 26300 - 1500, 34100 + 1500, 13100 + 1500);

        // 3人听牌
        playerPoints = new int[] {25000, 25000, 25000, 25000};
        tenpais = Arrays.asList(0, 1, 2);
        pointCalculator.ryuukyokuTransfer(tenpais, basePoint, playerPoints);
        checkPointArrayValue(playerPoints, 25000 + 1000, 25000 + 1000, 25000 + 1000, 25000 - 3000);

        // 4人听牌
        playerPoints = new int[] {25000, 25000, 25000, 25000};
        tenpais = Arrays.asList(0, 1, 2, 4);
        pointCalculator.ryuukyokuTransfer(tenpais, basePoint, playerPoints);
        checkPointArrayValue(playerPoints, 25000, 25000, 25000, 25000);
    }

    /**
     * 流局满贯
     */
    @Test
    public void ryuukyokuTransfer_mangan() {
        int basePoint = 2000;

        // 非庄家
        int[] playerPoints = new int[] {25000, 25000, 25000, 25000};
        pointCalculator.ryuukyokuTransfer(0, 1, basePoint, playerPoints);
        checkPointArrayValue(playerPoints, 25000 + 8000, 25000 - 4000, 25000 - 2000, 25000 - 2000);

        // 庄家
        playerPoints = new int[] {25000, 25000, 25000, 25000};
        pointCalculator.ryuukyokuTransfer(1, 1, basePoint, playerPoints);
        checkPointArrayValue(playerPoints, 25000 - 4000, 25000 + 12000, 25000 - 4000, 25000 - 4000);
    }

    /**
     * 自摸
     */
    @Test
    public void tsumoTransfer() {
        // 20符4番 + 1立直棒
        int[] playerPoints = new int[] {29100, 28900, 25300, 15700};
        int basePoint = pointCalculator.calcBasePoint(0, 20, 4);
        pointCalculator.tsumoTransfer(2, 2, basePoint, 0, 1, playerPoints);
        checkPointArrayValue(playerPoints, 29100 - 2600, 28900 - 2600, 25300 + 8800, 15700 - 2600);

        // 30符2番
        playerPoints = new int[] {25000, 34400, 35600, 5000};
        basePoint = pointCalculator.calcBasePoint(0, 30, 2);
        pointCalculator.tsumoTransfer(1, 3, basePoint, 0, 0, playerPoints);
        checkPointArrayValue(playerPoints, 25000 - 500, 34400 + 2000, 35600 - 500, 5000 - 1000);

        // 满贯5番
        playerPoints = new int[] {24500, 36400, 35100, 4000};
        basePoint = pointCalculator.calcBasePoint(0, -1, 5);
        pointCalculator.tsumoTransfer(0, 0, basePoint, 0, 0, playerPoints);
        checkPointArrayValue(playerPoints, 24500 + 12000, 36400 - 4000, 35100 - 4000, 4000 - 4000);

        // 2本场跳满
        playerPoints = new int[] {24300, 27100, 21100, 27500};
        basePoint = pointCalculator.calcBasePoint(0, 20, 7);
        pointCalculator.tsumoTransfer(3, 1, basePoint, 2, 1, playerPoints);
        checkPointArrayValue(playerPoints, 24300 - 3200, 27100 - 6200, 21100 - 3200, 27500 + 13600);
    }

    /**
     * 荣和
     */
    @Test
    public void ronTransfer() {
        int[] playerPoints = new int[] {25000, 25000, 25000, 25000};
        int basePoint = pointCalculator.calcBasePoint(0, 30, 3);
        pointCalculator.ronTransfer(2, 1, 0, basePoint, 0, 0, playerPoints);
        checkPointArrayValue(playerPoints, 25000, 21100, 28900, 25000);

        // 40符1番
        playerPoints = new int[] {25000, 25000, 25000, 25000};
        basePoint = pointCalculator.calcBasePoint(0, 40, 1);
        pointCalculator.ronTransfer(2, 3, 0, basePoint, 0, 0, playerPoints);
        checkPointArrayValue(playerPoints, 25000, 25000, 25000 + 1300, 25000 - 1300);

        // 40符2番 + 1立直棒
        playerPoints = new int[] {25000, 24000, 26300, 23700};
        basePoint = pointCalculator.calcBasePoint(0, 40, 2);
        pointCalculator.ronTransfer(1, 0, 1, basePoint, 0, 1, playerPoints);
        checkPointArrayValue(playerPoints, 25000 - 3900, 24000 + 4900, 26300, 23700);

        // 30符4番 + 1立直棒 + 1本场
        playerPoints = new int[] {20100, 28900, 26300, 23700};
        basePoint = pointCalculator.calcBasePoint(0, 30, 4);
        pointCalculator.ronTransfer(0, 3, 1, basePoint, 1, 1, playerPoints);
        checkPointArrayValue(playerPoints, 20100 + 9000, 28900, 26300, 23700 - 8000);

        // 满贯5番 + 2本场棒 + 2立直棒
        playerPoints = new int[] {25000, 23800, 35600, 13600};
        basePoint = pointCalculator.calcBasePoint(0, -1, 5);
        pointCalculator.ronTransfer(1, 3, 2, basePoint, 2, 2, playerPoints);
        checkPointArrayValue(playerPoints, 25000, 23800 + 10600, 35600, 13600 - 8600);
    }

    /**
     * 役满底分
     */
    @Test
    public void calcBasePoint_yakuman() {
        GameConfig gameConfig = GameConfig.defaultRule();
        // 无复合
        gameConfig.setMutiYakuman(false);
        List<IYaku> yakus = Arrays.asList(new Tenho(), new Kokusi());
        int basePoint = pointCalculator.calcBasePoint(1, -1, -1);
        assertEquals(8000, basePoint);

        // 有复合
        gameConfig.setMutiYakuman(true);
        basePoint = pointCalculator.calcBasePoint(yakus, -1, -1, gameConfig);
        assertEquals(8000 * 2, basePoint);

        // 两倍役满
        gameConfig.setDoubleYakuman(true);
        yakus = Arrays.asList(new Kokusi13());
        basePoint = pointCalculator.calcBasePoint(yakus, -1, -1, gameConfig);
        assertEquals(8000 * 2, basePoint);

        // 两倍役满 + 复合役满
        yakus = Arrays.asList(new Tenho(), new Kokusi13());
        basePoint = pointCalculator.calcBasePoint(yakus, -1, -1, gameConfig);
        assertEquals(8000 * 3, basePoint);

        // 理论最大役满
        // 字一色，大四喜，四杠子，四暗刻单骑 6倍
        yakus = Arrays.asList(new Tuuiisou(), new Daisuusii(), new Suukantu(), new SuuankouTanki());
        basePoint = pointCalculator.calcBasePoint(yakus, -1, -1, gameConfig);
        assertEquals(8000 * 6, basePoint);
    }

    /**
     * 所有5番以上底分计算
     */
    @Test
    public void calcBasePoint_plus5() {
        assertEquals(8000, pointCalculator.calcBasePoint(0, -1, 13));
        assertEquals(6000, pointCalculator.calcBasePoint(0, -1, 12));
        assertEquals(6000, pointCalculator.calcBasePoint(0, -1, 11));
        assertEquals(4000, pointCalculator.calcBasePoint(0, -1, 10));
        assertEquals(4000, pointCalculator.calcBasePoint(0, -1, 9));
        assertEquals(4000, pointCalculator.calcBasePoint(0, -1, 8));
        assertEquals(3000, pointCalculator.calcBasePoint(0, -1, 7));
        assertEquals(3000, pointCalculator.calcBasePoint(0, -1, 6));
        assertEquals(2000, pointCalculator.calcBasePoint(0, -1, 5));
    }

    /**
     * 小于5番底分计算
     */
    @Test
    public void calcBasePoint_m5() {
        // 20符
        assertEquals(400, calcBasePointCeil(2, 20));
        assertEquals(700, calcBasePointCeil(3, 20));
        assertEquals(1300, calcBasePointCeil(4, 20));

        // 25符
        assertEquals(800, calcBasePointCeil(3, 25));
        assertEquals(1600, calcBasePointCeil(4, 25));

        // 30符
        assertEquals(300, calcBasePointCeil(1, 30));
        assertEquals(500, calcBasePointCeil(2, 30));
        assertEquals(1000, calcBasePointCeil(3, 30));
        assertEquals(2000, calcBasePointCeil(4, 30));

        // 40符
        assertEquals(400, calcBasePointCeil(1, 40));
        assertEquals(700, calcBasePointCeil(2, 40));
        assertEquals(1300, calcBasePointCeil(3, 40));
        assertEquals(2000, calcBasePointCeil(4, 40));

        // 50符
        assertEquals(400, calcBasePointCeil(1, 50));
        assertEquals(800, calcBasePointCeil(2, 50));
        assertEquals(1600, calcBasePointCeil(3, 50));
        assertEquals(2000, calcBasePointCeil(4, 50));

        // 60符
        assertEquals(500, calcBasePointCeil(1, 60));
        assertEquals(1000, calcBasePointCeil(2, 60));
        assertEquals(2000, calcBasePointCeil(3, 60));
        assertEquals(2000, calcBasePointCeil(4, 60));

        // 70符
        assertEquals(600, calcBasePointCeil(1, 70));
        assertEquals(1200, calcBasePointCeil(2, 70));
        assertEquals(2000, calcBasePointCeil(3, 70));
        assertEquals(2000, calcBasePointCeil(4, 70));

        // 80符
        assertEquals(700, calcBasePointCeil(1, 80));
        assertEquals(1300, calcBasePointCeil(2, 80));
        assertEquals(2000, calcBasePointCeil(3, 80));
        assertEquals(2000, calcBasePointCeil(4, 80));

        // 90符
        assertEquals(800, calcBasePointCeil(1, 90));
        assertEquals(1500, calcBasePointCeil(2, 90));
        assertEquals(2000, calcBasePointCeil(3, 90));
        assertEquals(2000, calcBasePointCeil(4, 90));

        // 100符
        assertEquals(800, calcBasePointCeil(1, 100));
        assertEquals(1600, calcBasePointCeil(2, 100));
        assertEquals(2000, calcBasePointCeil(3, 100));
        assertEquals(2000, calcBasePointCeil(4, 100));

        // 110符
        assertEquals(1800, calcBasePointCeil(2, 110));
        assertEquals(2000, calcBasePointCeil(3, 110));
        assertEquals(2000, calcBasePointCeil(4, 110));
    }

    /**
     * 计算底分并向上取整
     * 
     * @param han 番数
     * @param fu 符数
     */
    private int calcBasePointCeil(int han, int fu) {
        return pointCalculator.ceilPoint(pointCalculator.calcBasePoint(0, fu, han));
    }

    /**
     * 检查分数
     * 
     * @param playerPoints 分数数组
     * @param a p1
     * @param b p2
     * @param c p3
     * @param d p4
     */
    private void checkPointArrayValue(int[] playerPoints, int a, int b, int c, int d) {
        assertEquals(a, playerPoints[0]);
        assertEquals(b, playerPoints[1]);
        assertEquals(c, playerPoints[2]);
        assertEquals(d, playerPoints[3]);
    }
}
