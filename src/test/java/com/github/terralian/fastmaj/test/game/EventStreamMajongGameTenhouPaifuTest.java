package com.github.terralian.fastmaj.test.game;

import java.util.List;

import com.github.terralian.fastmaj.game.EventStreamMajongGame;
import com.github.terralian.fastmaj.game.GameComponent;
import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IMajongGame;
import com.github.terralian.fastmaj.player.IPlayer;

/**
 * 对{@link EventStreamMajongGame}进行天凤牌谱冒烟测试
 *
 * @author Terra.Lian
 */
public class EventStreamMajongGameTenhouPaifuTest extends AbstractTenhouSpecialSamplePaifuTest {

    @Override
    protected IMajongGame createMajongGame(List<? extends IPlayer> players, GameConfig gameConfig,
            GameComponent gameComponent) {
        return new EventStreamMajongGame(players, gameConfig, gameComponent);
    }
}
