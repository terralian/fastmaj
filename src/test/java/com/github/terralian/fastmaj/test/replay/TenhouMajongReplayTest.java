package com.github.terralian.fastmaj.test.replay;

import org.junit.Test;

import com.github.terralian.fastmaj.game.GameCore;
import com.github.terralian.fastmaj.game.StepMajongGame;
import com.github.terralian.fastmaj.game.log.PrintGameLogger;
import com.github.terralian.fastmaj.replay.TenhouMajongReplay;

/**
 * {@link TenhouMajongReplay}及{@link StepMajongGame}及{@link GameCore}测试
 * 
 * @author terra.lian
 */
public class TenhouMajongReplayTest extends TenhouMajongReplay {

    public TenhouMajongReplayTest() {}

    @Test
    public void test() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(true);
        String fileName = "tehou/2014050315gm-0041-0000-0f3f6de5&tw=3.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    @Test
    public void test1() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(false);
        String fileName = "tehou/2014050315gm-0041-0000-06a52f11&tw=2.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    @Test
    public void test2() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(true);
        String fileName = "tehou/2014050315gm-0041-0000-6babc3e5&tw=0.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    @Test
    public void test3() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(false);
        String fileName = "tehou/2014050315gm-0041-0000-7ddece8e&tw=0.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    @Test
    public void test4() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(false);
        String fileName = "tehou/2014050706gm-0041-0000-4e38a006&tw=1.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    @Test
    public void test5() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(false);
        String fileName = "tehou/2014050707gm-0041-0000-5cf9066d&tw=3.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    @Test
    public void test6() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(false);
        String fileName = "tehou/2014050821gm-0041-0000-a1298f1a&tw=2.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    @Test
    public void test7() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(false);
        String fileName = "tehou/2014050911gm-0041-0000-01181f16&tw=0.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    @Test
    public void test8() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(false);
        String fileName = "tehou/2014051313gm-0041-0000-c47daaf7&tw=2.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    @Test
    public void test9() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(true);
        String fileName = "tehou/2014051511gm-0041-0000-a29f5b7b&tw=1.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    /**
     * 末局庄家第一位的九种九牌
     */
    @Test
    public void test10() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(true);
        String fileName = "tehou/2014052604gm-0041-0000-dc1795af&tw=3.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    @Test
    public void test11() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(false);
        String fileName = "tehou/2014052605gm-0041-0000-5db7afd1&tw=1.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    @Test
    public void test12() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(false);
        String fileName = "tehou/2014060513gm-00c1-0000-84fa529d&tw=1.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    @Test
    public void test13() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(false);
        String fileName = "tehou/2014072411gm-0089-0000-37d4cafc&tw=1.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    @Test
    public void test14() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(false);
        String fileName = "tehou/2014112412gm-00c1-0000-bfbe4417&tw=1.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    @Test
    public void test15() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(false);
        String fileName = "tehou/2014120309gm-0029-0000-ed40750a&tw=1.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    @Test
    public void test16() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(true);
        String fileName = "tehou/2014120822gm-0029-0000-808ea6c0&tw=0.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    @Test
    public void test17() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(true);
        String fileName = "tehou/2014051123gm-0041-0000-19ae6ef7&tw=0.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    @Test
    public void test18() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(true);
        String fileName = "tehou/2014120823gm-0029-0000-26f781c3&tw=0.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    @Test
    public void test19() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(false);
        String fileName = "tehou/2014123118gm-0029-0000-4700b218&tw=0.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    @Test
    public void test20() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(false);
        String fileName = "tehou/2015070411gm-00a9-0000-b8b13fcc&tw=0.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    @Test
    public void test21() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(false);
        String fileName = "tehou/2015080113gm-00a9-0000-0213ac0c&tw=3.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    /**
     * 役满
     */
    @Test
    public void test22() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(false);
        String fileName = "tehou/2015082211gm-00a9-0000-b665ff0a&tw=1.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    /**
     * 流局满贯
     */
    @Test
    public void test23() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(false);
        String fileName = "tehou/2018061303gm-00a9-0000-5a8850e9&tw=2.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    @Test
    public void test24() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(false);
        String fileName = "tehou/2019050400gm-00a9-0000-8c08e32a&tw=3.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    @Test
    public void test25() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(false);
        String fileName = "tehou/2019110302gm-00a9-0000-473e5852&tw=3.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    @Test
    public void test26() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(false);
        String fileName = "tehou/2021041720gm-00a9-0000-f3b19e15&tw=3.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    /**
     * 三家和了
     */
    @Test
    public void test27() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(false);
        String fileName = "tehou/2021010917gm-0089-0000-d9225d74&tw=1.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    @Test
    public void test28() throws Exception {
        ((PrintGameLogger) gameLogger).setShortKyokuSummary(true);
        String fileName = "tehou/2021100323gm-00a9-0000-d0ac1302&tw=1.mjlog";
        this.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }
}
