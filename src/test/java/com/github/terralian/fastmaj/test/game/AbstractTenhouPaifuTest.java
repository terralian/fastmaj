package com.github.terralian.fastmaj.test.game;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import com.github.terralian.fastmaj.game.GameComponent;
import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IMajongGame;
import com.github.terralian.fastmaj.game.log.IGameLogger;
import com.github.terralian.fastmaj.game.log.PrintGameLogger;
import com.github.terralian.fastmaj.paifu.IPaifuParser;
import com.github.terralian.fastmaj.paifu.domain.PaifuGame;
import com.github.terralian.fastmaj.paifu.domain.PaifuKyoku;
import com.github.terralian.fastmaj.paifu.source.FileSource;
import com.github.terralian.fastmaj.paifu.source.GZIPSource;
import com.github.terralian.fastmaj.paifu.source.IPaifuSource;
import com.github.terralian.fastmaj.paifu.tenhou.handler.TenhouPaifuGameParseHandler;
import com.github.terralian.fastmaj.paifu.tenhou.TenhouPaifuParser;
import com.github.terralian.fastmaj.paifu.tenhou.TenhouRuleTimelineHandler;
import com.github.terralian.fastmaj.player.IPlayer;
import com.github.terralian.fastmaj.player.PaifuGameQueueReplayPlayerBuilder;
import com.github.terralian.fastmaj.player.QueueReplayPlayer;
import com.github.terralian.fastmaj.util.StringUtil;
import com.github.terralian.fastmaj.util.TestResourceUtil;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

/**
 * @author Terra.Lian
 */
public abstract class AbstractTenhouPaifuTest {

    protected IPaifuParser<PaifuGame> paifuParser;

    protected TenhouRuleTimelineHandler ruleTimelineHandler;

    @Before
    public void before() {
        paifuParser = new TenhouPaifuParser(new TenhouPaifuGameParseHandler());
        ruleTimelineHandler = new TenhouRuleTimelineHandler();
    }

    /**
     * 读取一个记录
     *
     * @param simplePaifuName 牌谱简单名
     * @param shortKyokuSummary 是否简易打印（false为详细打印），在出错时可以选择详细打印
     * @throws Exception
     */
    protected void simulate_run_game(String simplePaifuName, boolean shortKyokuSummary) throws Exception {
        File file = TestResourceUtil.readToFile("tenhou", simplePaifuName);
        LogType logType = shortKyokuSummary ? LogType.SUMMARY : LogType.DETAIL;
        simulate_run_game(simplePaifuName, new GZIPSource(new FileSource(file)), logType);
    }

    /**
     * 读取一个记录进行测试
     *
     * @param simplePaifuName 牌谱简单名
     * @param paifuSource 牌谱数据源
     * @param logType 日志类型
     * @throws Exception
     */
    protected void simulate_run_game(String simplePaifuName, IPaifuSource paifuSource,
            LogType logType) throws Exception {
        PaifuGame paifuGame = paifuParser.parse(paifuSource);
        List<QueueReplayPlayer> players = PaifuGameQueueReplayPlayerBuilder.toPlayer(paifuGame);

        List<int[]> allRound = paifuGame.getKyokus()
                .stream()
                .map(PaifuKyoku::getEndPoints)
                .collect(Collectors.toList());
        // 游戏组件
        GameComponent gameComponent = GameComponent.useTenhou(paifuGame.getSeed())
                .addLogger(new AbstractTenhouSpecialSamplePaifuTest.PointCheckLogger(allRound, logType != LogType.NONE));
        // 日志打印
        if (logType != LogType.NONE) {
            gameComponent.addLogger(new PrintGameLogger(logType == LogType.SUMMARY));
        }
        // 配置
        GameConfig gameConfig = GameConfig.useTenhou(paifuGame);
        ruleTimelineHandler.deduce(gameConfig, paifuGame, simplePaifuName);

        IMajongGame majongGame = createMajongGame(players, gameConfig, gameComponent);
        majongGame.startGame();

        int[] expected = paifuGame.getEndPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    /**
     * 创建一个麻将游戏
     *
     * @param players 玩家
     * @param gameConfig 游戏配置
     * @param gameComponent 游戏组件
     */
    protected abstract IMajongGame createMajongGame(List<? extends IPlayer> players, GameConfig gameConfig,
            GameComponent gameComponent);

    /**
     * 分数校验日志
     */
    protected static class PointCheckLogger implements IGameLogger {

        private final List<int[]> allRound;
        private final boolean enabledPrint;

        public PointCheckLogger(List<int[]> allRound, boolean enabledPrint) {
            this.allRound = allRound;
            this.enabledPrint = enabledPrint;
        }

        @Override
        public void kyokuEnd(int round, int[] expected) {
            int[] actual = this.allRound.get(round);
            if (enabledPrint) {
                System.out.println("实际值：" + StringUtil.join(",", actual));
            }
            for (int i = 0; i < expected.length; i++) {
                assertEquals(expected[i], (int) actual[i]);
            }
        }
    }


    protected enum LogType {
        NONE, SUMMARY, DETAIL
    }
}
