package com.github.terralian.fastmaj.test.game;

import java.util.List;

import com.github.terralian.fastmaj.game.GameComponent;
import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.KazeEnum;
import com.github.terralian.fastmaj.game.StreamMajongGame;
import com.github.terralian.fastmaj.game.builder.MajongGameBuilder;
import com.github.terralian.fastmaj.game.log.IGameLogger;
import com.github.terralian.fastmaj.game.log.PrintGameLogger;
import com.github.terralian.fastmaj.player.QueueReplayPlayer;
import com.github.terralian.fastmaj.replay.TenhouMajongReplay;
import com.github.terralian.fastmaj.util.StringUtil;
import com.github.terralian.fastmaj.yama.TenhouYamaWorker;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * {@link StreamMajongGame}单元测试
 * 
 * @author terra.lian
 */
public class TenhouStreamMajongGameTest {

    /**
     * 根据{@link QueueReplayPlayer}及{@link TenhouMajongReplay}使用牌谱进行测试
     * 
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014050315gm-0041-0000-0f3f6de5&tw=3.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = new StreamMajongGame(players,
                GameConfig.defaultRule(),
                GameComponent.useTenhou(builder.getSeed()).addLogger(new PrintGameLogger(true)));

        // 东风战
        majongGame.getConfig().setEndBakaze(KazeEnum.DON);

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test2() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014050315gm-0041-0000-06a52f11&tw=2.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(false))//
                .build(players);
        // 东风战
        majongGame.getConfig().setEndBakaze(KazeEnum.DON);

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test3() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014050315gm-0041-0000-7ddece8e&tw=0.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(true))//
                .build(players);
        // 东风战
        majongGame.getConfig().setEndBakaze(KazeEnum.DON);

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test4() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014050706gm-0041-0000-0f2d0390&tw=1.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(true))//
                .build(players);
        // 东风战
        majongGame.getConfig().setEndBakaze(KazeEnum.DON);

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test5() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014050706gm-0041-0000-d961758c&tw=3.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(true))//
                .build(players);
        // 东风战
        majongGame.getConfig().setEndBakaze(KazeEnum.DON);

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test6() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014050707gm-0041-0000-5cf9066d&tw=3.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(false))//
                .build(players);
        // 东风战
        majongGame.getConfig().setEndBakaze(KazeEnum.DON);

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test7() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014050821gm-0041-0000-defb2e94&tw=0.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(true))//
                .build(players);
        // 东风战
        majongGame.getConfig().setEndBakaze(KazeEnum.DON);

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test8() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014050911gm-0041-0000-01181f16&tw=0.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(true))//
                .build(players);
        // 东风战
        majongGame.getConfig().setEndBakaze(KazeEnum.DON);

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test9() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014050912gm-0041-0000-5b9bd6a8&tw=1.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(true))//
                .build(players);
        // 东风战
        majongGame.getConfig().setEndBakaze(KazeEnum.DON);

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test10() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014051204gm-0041-0000-c9ebd8d8&tw=1.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(true))//
                .build(players);
        // 东风战
        majongGame.getConfig().setEndBakaze(KazeEnum.DON);

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test11() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014051313gm-0041-0000-c47daaf7&tw=2.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(true))//
                .build(players);
        // 东风战
        majongGame.getConfig().setEndBakaze(KazeEnum.DON);

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test12() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014051313gm-0041-0000-c7bd6c03&tw=3.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(true))//
                .build(players);
        // 东风战
        majongGame.getConfig().setEndBakaze(KazeEnum.DON);

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test13() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014051419gm-0041-0000-7ec58083&tw=1.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(true))//
                .build(players);
        // 东风战
        majongGame.getConfig().setEndBakaze(KazeEnum.DON);

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test14() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014051420gm-0041-0000-f7f77570&tw=0.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(true))//
                .build(players);
        // 东风战
        majongGame.getConfig().setEndBakaze(KazeEnum.DON);

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    /**
     * 多人和了
     */
    @Test
    public void test15() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014051511gm-0041-0000-a29f5b7b&tw=1.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(true))//
                .build(players);
        // 东风战
        majongGame.getConfig().setEndBakaze(KazeEnum.DON);

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    /**
     * 流局满贯
     */
    @Test
    public void test16() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014051613gm-0041-0000-3366561a&tw=0.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(true))//
                .build(players);
        // 东风战
        majongGame.getConfig().setEndBakaze(KazeEnum.DON);

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test17() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014052605gm-0041-0000-5db7afd1&tw=1.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(false))//
                .build(players);
        // 东风战
        majongGame.getConfig().setEndBakaze(KazeEnum.DON);

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test18() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014052606gm-00c1-0000-6c0dfa74&tw=1.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(true))//
                .build(players);
        // 东风战
        majongGame.getConfig().setEndBakaze(KazeEnum.DON);

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test19() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014052711gm-00c1-0000-82c05143&tw=1.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(true))//
                .build(players);
        // 东风战
        majongGame.getConfig().setEndBakaze(KazeEnum.DON);

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test20() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014060412gm-00c1-0000-abd08a3d&tw=2.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(true))//
                .build(players);
        // 东风战
        majongGame.getConfig().setEndBakaze(KazeEnum.DON);

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    /**
     * 一发被鸣
     */
    @Test
    public void test21() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014060512gm-00c1-0000-59befdce&tw=1.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(true))//
                .build(players);
        // 东风战
        majongGame.setConfig(builder.getConfig());

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test22() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014060512gm-00c1-0000-eb8a80a7&tw=3.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(true))//
                .build(players);
        majongGame.setConfig(builder.getConfig());

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test23() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014060513gm-00c1-0000-84fa529d&tw=1.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(true))//
                .build(players);
        majongGame.setConfig(builder.getConfig());

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    /**
     * 岭上
     */
    @Test
    public void test24() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014060513gm-00c1-0000-b2530541&tw=0.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(true))//
                .build(players);
        majongGame.setConfig(builder.getConfig());

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test25() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014072411gm-0089-0000-37d4cafc&tw=1.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(true))//
                .build(players);
        majongGame.setConfig(builder.getConfig());

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test26() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014072411gm-0089-0000-37d4cafc&tw=1.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(true))//
                .build(players);
        majongGame.setConfig(builder.getConfig());

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test27() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014120309gm-0029-0000-ed40750a&tw=1.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(false))//
                .build(players);
        majongGame.setConfig(builder.getConfig());

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    /**
     * 多人荣和（多本场）
     */
    @Test
    public void test28() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014120511gm-0029-0000-511b245b&tw=0.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<Integer[]> allround = builder.getAllRoundPoints();
        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(true))//
                .addGameLogger(new PointCheckLogger(allround)).build(players);
        majongGame.setConfig(builder.getConfig());

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    /**
     * 多人荣和（多本场）
     */
    @Test
    public void test29() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014120816gm-0029-0000-6c78c8b6&tw=3.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<Integer[]> allround = builder.getAllRoundPoints();
        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(false))//
                .addGameLogger(new PointCheckLogger(allround)).build(players);
        majongGame.setConfig(builder.getConfig());

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test30() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2014051123gm-0041-0000-19ae6ef7&tw=0.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<Integer[]> allround = builder.getAllRoundPoints();
        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(false))//
                .addGameLogger(new PointCheckLogger(allround)).build(players);
        majongGame.setConfig(builder.getConfig());

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test31() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2015011211gm-0029-0000-2dd7ec0f&tw=3.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<Integer[]> allround = builder.getAllRoundPoints();
        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(false))//
                .addGameLogger(new PointCheckLogger(allround)).build(players);
        majongGame.setConfig(builder.getConfig());

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test32() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2015041416gm-0029-0000-5e89a32a&tw=0.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<Integer[]> allround = builder.getAllRoundPoints();
        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(false))//
                .addGameLogger(new PointCheckLogger(allround)).build(players);
        majongGame.setConfig(builder.getConfig());

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test33() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2015050103gm-0029-0000-c8a73922&tw=1.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<Integer[]> allround = builder.getAllRoundPoints();
        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(false))//
                .addGameLogger(new PointCheckLogger(allround)).build(players);
        majongGame.setConfig(builder.getConfig());

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test34() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2015070411gm-00a9-0000-b8b13fcc&tw=0.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<Integer[]> allround = builder.getAllRoundPoints();
        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(false))//
                .addGameLogger(new PointCheckLogger(allround)).build(players);
        majongGame.setConfig(builder.getConfig());

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test35() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2015070614gm-00a9-0000-a76c73b1&tw=2.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<Integer[]> allround = builder.getAllRoundPoints();
        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(false))//
                .addGameLogger(new PointCheckLogger(allround)).build(players);
        majongGame.setConfig(builder.getConfig());

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test36() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2016110202gm-00a9-0000-9bd0c557&tw=3.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<Integer[]> allround = builder.getAllRoundPoints();
        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(false))//
                .addGameLogger(new PointCheckLogger(allround)).build(players);
        majongGame.setConfig(builder.getConfig());

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test37() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2018120719gm-00a9-0000-f41035fe&tw=2.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<Integer[]> allround = builder.getAllRoundPoints();
        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(false))//
                .addGameLogger(new PointCheckLogger(allround)).build(players);
        majongGame.setConfig(builder.getConfig());

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test38() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2019110302gm-00a9-0000-473e5852&tw=3.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<Integer[]> allround = builder.getAllRoundPoints();
        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(false))//
                .addGameLogger(new PointCheckLogger(allround)).build(players);
        majongGame.setConfig(builder.getConfig());

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void test39() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2021072417gm-00a9-0000-bd1e1772&tw=1.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<Integer[]> allround = builder.getAllRoundPoints();
        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(false))//
                .addGameLogger(new PointCheckLogger(allround)).build(players);
        majongGame.setConfig(builder.getConfig());

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    /**
     * 三家和了
     */
    @Test
    public void test40() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2021010917gm-0089-0000-d9225d74&tw=1.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<Integer[]> allround = builder.getAllRoundPoints();
        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(false))//
                .addGameLogger(new PointCheckLogger(allround)).build(players);
        majongGame.setConfig(builder.getConfig());

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    /**
     * 第一巡暗杠后立直
     */
    @Test
    public void test41() throws Exception {
        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        String fileName = "tenhou/2015012707gm-0029-0000-bf2d93df&tw=2.mjlog";
        builder.getPaifuParser().parseStream(this.getClass().getClassLoader().getResourceAsStream(fileName));

        List<Integer[]> allround = builder.getAllRoundPoints();
        List<QueueReplayPlayer> players = builder.getValue();
        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .addGameLogger(new PrintGameLogger().setShortKyokuSummary(false))//
                .addGameLogger(new PointCheckLogger(allround)).build(players);
        majongGame.setConfig(builder.getConfig());

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    private static class PointCheckLogger implements IGameLogger {

        private List<Integer[]> allround;

        public PointCheckLogger(List<Integer[]> allround) {
            this.allround = allround;
        }

        @Override
        public void kyokuEnd(int round, int[] playerPoints) {
            int[] expected = playerPoints;
            Integer[] actual = this.allround.get(round);
            System.out.println("实际值：" + StringUtil.join(",", actual));
            for (int i = 0; i < playerPoints.length; i++) {
                assertEquals(expected[i], (int) actual[i]);
            }
        }
    }
}
