package com.github.terralian.fastmaj.test.game;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.github.terralian.fastmaj.FastMajong;
import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.GameCore;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.KazeEnum;
import com.github.terralian.fastmaj.game.log.ChainGameLoggerBuilder;
import com.github.terralian.fastmaj.game.log.IGameLogger;
import com.github.terralian.fastmaj.game.log.PrintGameLogger;
import com.github.terralian.fastmaj.tehai.ISyantenCalculator;
import com.github.terralian.fastmaj.yama.IYamaWorker;
import com.github.terralian.fastmaj.yama.SimpleRandomYamaWorker;

/**
 * {@link GameCore}的测试类
 * 
 * @author terra.lian
 */
public class GameCoreTest {

    private IYamaWorker yamaWorker;
    private ISyantenCalculator syantenCalculator;

    @Before
    public void before() {
        yamaWorker = new SimpleRandomYamaWorker();
        syantenCalculator = FastMajong.doGetSyantenCalculator();
    }

    @Test
    public void startGame() {
        GameConfig config = GameConfig.defaultRule();
        IGameLogger gameLogger = ChainGameLoggerBuilder.newBuilder() //
                .addLogger(new PrintGameLogger()) //
                .build();
        IGameCore gameCore = new GameCore(4, new ArrayList<>(), config, yamaWorker, syantenCalculator, gameLogger);
        gameCore.startGame();
        assertEquals(KazeEnum.DON, gameCore.getBakaze());
        assertEquals(0, gameCore.getHonba());
        assertEquals(0, gameCore.getKyotaku());
        assertEquals(-1, gameCore.getOya());
        assertEquals(4, gameCore.getPlayerSize());
        assertEquals(config.getStartPoint().intValue(), gameCore.getPlayerPoints()[0]);
        assertEquals(0, gameCore.getPosition());
        // 未开始对局
        assertEquals(-1, gameCore.getRound());
    }

    @Test
    public void nextKyoku() {
        GameConfig config = GameConfig.defaultRule();
        IGameLogger gameLogger = ChainGameLoggerBuilder.newBuilder() //
                .addLogger(new PrintGameLogger()) //
                .build();
        IGameCore gameCore = new GameCore(4, new ArrayList<>(), config, yamaWorker, syantenCalculator, gameLogger);
        gameCore.startGame();
        assertEquals(0, gameCore.nextKyoku());
    }

    @Test(expected = IllegalStateException.class)
    public void nextKyoku_exception() {
        GameConfig config = GameConfig.defaultRule();
        IGameLogger gameLogger = ChainGameLoggerBuilder.newBuilder() //
                .addLogger(new PrintGameLogger()) //
                .build();
        IGameCore gameCore = new GameCore(4, new ArrayList<>(), config, yamaWorker, syantenCalculator, gameLogger);
        gameCore.nextKyoku();
    }
}
