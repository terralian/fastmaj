package com.github.terralian.fastmaj.test.game;

import java.util.List;
import java.util.stream.Collectors;

import com.github.terralian.fastmaj.game.GameComponent;
import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.StreamMajongGame;
import com.github.terralian.fastmaj.game.log.IGameLogger;
import com.github.terralian.fastmaj.game.log.PrintGameLogger;
import com.github.terralian.fastmaj.paifu.IPaifuParser;
import com.github.terralian.fastmaj.paifu.domain.PaifuGame;
import com.github.terralian.fastmaj.paifu.domain.PaifuKyoku;
import com.github.terralian.fastmaj.paifu.tenhou.TenhouPaifuGameParseHandler;
import com.github.terralian.fastmaj.paifu.tenhou.TenhouPaifuParser;
import com.github.terralian.fastmaj.player.PaifuGameQueueReplayPlayerBuilder;
import com.github.terralian.fastmaj.player.QueueReplayPlayer;
import com.github.terralian.fastmaj.util.StringUtil;
import com.github.terralian.fastmaj.util.TestResourceUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * {@link StreamMajongGame}单元测试
 *
 * @author terra.lian
 */
public class TenhouStreamMajongGameTest {

    private IPaifuParser<PaifuGame> paifuParser;

    @Before
    public void before() {
        paifuParser = new TenhouPaifuParser(new TenhouPaifuGameParseHandler());
    }

    @Test
    public void special_sample_paifu_test() throws Exception {
        simulate_run_game("2014050315gm-0041-0000-0f3f6de5&tw=3.mjlog", true);
        simulate_run_game("2014050315gm-0041-0000-06a52f11&tw=2.mjlog", true);
        simulate_run_game("2014050315gm-0041-0000-7ddece8e&tw=0.mjlog", true);
        simulate_run_game("2014050706gm-0041-0000-0f2d0390&tw=1.mjlog", true);
        simulate_run_game("2014050706gm-0041-0000-d961758c&tw=3.mjlog", true);
        simulate_run_game("2014050707gm-0041-0000-5cf9066d&tw=3.mjlog", true);
        simulate_run_game("2014050821gm-0041-0000-defb2e94&tw=0.mjlog", true);
        simulate_run_game("2014050911gm-0041-0000-01181f16&tw=0.mjlog", true);
        simulate_run_game("2014050912gm-0041-0000-5b9bd6a8&tw=1.mjlog", true);
        simulate_run_game("2014051204gm-0041-0000-c9ebd8d8&tw=1.mjlog", true);
        simulate_run_game("2014051313gm-0041-0000-c47daaf7&tw=2.mjlog", true);
        simulate_run_game("2014051313gm-0041-0000-c7bd6c03&tw=3.mjlog", true);
        simulate_run_game("2014051419gm-0041-0000-7ec58083&tw=1.mjlog", true);
        simulate_run_game("2014051420gm-0041-0000-f7f77570&tw=0.mjlog", true);
        simulate_run_game("2014051511gm-0041-0000-a29f5b7b&tw=1.mjlog", true);
        simulate_run_game("2014051613gm-0041-0000-3366561a&tw=0.mjlog", true);
        simulate_run_game("2014052605gm-0041-0000-5db7afd1&tw=1.mjlog", true);
        simulate_run_game("2014052606gm-00c1-0000-6c0dfa74&tw=1.mjlog", true);
        simulate_run_game("2014052711gm-00c1-0000-82c05143&tw=1.mjlog", true);
        simulate_run_game("2014060412gm-00c1-0000-abd08a3d&tw=2.mjlog", true);
        simulate_run_game("2014060512gm-00c1-0000-59befdce&tw=1.mjlog", true);
        simulate_run_game("2014060512gm-00c1-0000-eb8a80a7&tw=3.mjlog", true);
        simulate_run_game("2014060513gm-00c1-0000-84fa529d&tw=1.mjlog", true);
        simulate_run_game("2014060513gm-00c1-0000-b2530541&tw=0.mjlog", true);
        simulate_run_game("2014072411gm-0089-0000-37d4cafc&tw=1.mjlog", true);
        simulate_run_game("2014120309gm-0029-0000-ed40750a&tw=1.mjlog", true);
        simulate_run_game("2014120511gm-0029-0000-511b245b&tw=0.mjlog", true);
        simulate_run_game("2014120816gm-0029-0000-6c78c8b6&tw=3.mjlog", true);
        simulate_run_game("2014051123gm-0041-0000-19ae6ef7&tw=0.mjlog", true);
        simulate_run_game("2015011211gm-0029-0000-2dd7ec0f&tw=3.mjlog", true);
        simulate_run_game("2015041416gm-0029-0000-5e89a32a&tw=0.mjlog", true);
        simulate_run_game("2015050103gm-0029-0000-c8a73922&tw=1.mjlog", true);
        simulate_run_game("2015070411gm-00a9-0000-b8b13fcc&tw=0.mjlog", true);
        simulate_run_game("2015070614gm-00a9-0000-a76c73b1&tw=2.mjlog", true);
        simulate_run_game("2016110202gm-00a9-0000-9bd0c557&tw=3.mjlog", true);
        simulate_run_game("2018120719gm-00a9-0000-f41035fe&tw=2.mjlog", true);
        simulate_run_game("2019110302gm-00a9-0000-473e5852&tw=3.mjlog", true);
        simulate_run_game("2021072417gm-00a9-0000-bd1e1772&tw=1.mjlog", true);
        simulate_run_game("2021010917gm-0089-0000-d9225d74&tw=1.mjlog", true);
        simulate_run_game("2015012707gm-0029-0000-bf2d93df&tw=2.mjlog", true);
    }

    private void simulate_run_game(String simplePaifuName, boolean shortKyokuSummary) throws Exception {
        PaifuGame paifuGame = paifuParser.parseFile(TestResourceUtil.readToFile("tenhou", simplePaifuName));
        List<QueueReplayPlayer> players = PaifuGameQueueReplayPlayerBuilder.toPlayer(paifuGame);

        List<int[]> allRound = paifuGame.getKyokus()
                .stream()
                .map(PaifuKyoku::getEndPoints)
                .collect(Collectors.toList());
        // 游戏组件
        GameComponent gameComponent = GameComponent.useTenhou(paifuGame.getSeed()) //
                .addLogger(new PrintGameLogger(shortKyokuSummary))
                .addLogger(new PointCheckLogger(allRound));
        // 配置
        GameConfig gameConfig = GameConfig.defaultRule();
        gameConfig.setEndBakaze(paifuGame.getEndBakaze());

        StreamMajongGame majongGame = new StreamMajongGame(players, gameConfig, gameComponent);
        majongGame.startGame();

        int[] expected = paifuGame.getEndPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    private static class PointCheckLogger implements IGameLogger {

        private final List<int[]> allRound;

        public PointCheckLogger(List<int[]> allRound) {
            this.allRound = allRound;
        }

        @Override
        public void kyokuEnd(int round, int[] expected) {
            int[] actual = this.allRound.get(round);
            System.out.println("实际值：" + StringUtil.join(",", actual));
            for (int i = 0; i < expected.length; i++) {
                assertEquals(expected[i], (int) actual[i]);
            }
        }
    }
}
