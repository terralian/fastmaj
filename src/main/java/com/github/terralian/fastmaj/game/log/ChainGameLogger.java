package com.github.terralian.fastmaj.game.log;

import java.util.List;

import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.KazeEnum;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.player.IPlayer;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yama.DrawFrom;

/**
 * {@link ChainGameLogger}用于需要多个打印器的场景，每个打印器处理不同的日志处理需求
 *
 * @author terra.lian
 * @since 2022-10-28
 */
class ChainGameLogger implements IGameLogger {

    /**
     * 游戏日志处理器集合
     */
    private List<IGameLogger> loggers;

    /**
     * 构建一个链式日志处理器
     *
     * @param loggers 日志处理器集合
     */
    public ChainGameLogger(List<IGameLogger> loggers) {
        this.loggers = loggers;
    }

    @Override
    public void gameStart(List<IPlayer> players, IGameCore gameCore) {
        for (IGameLogger logger : loggers) {
            logger.gameStart(players, gameCore);
        }
    }

    @Override
    public void kyokuStart(int round, KazeEnum bakaze, int oya, int honba, List<ITehai> haipais) {
        for (IGameLogger logger : loggers) {
            logger.kyokuStart(round, bakaze, oya, honba, haipais);
        }
    }

    @Override
    public void kyokuEnd(int round, int[] playerPoints) {
        for (IGameLogger logger : loggers) {
            logger.kyokuEnd(round, playerPoints);
        }
    }

    @Override
    public void gameEnd() {
        for (IGameLogger logger : loggers) {
            logger.gameEnd();
        }
    }

    @Override
    public void draw(int position, IHai hai, DrawFrom drawFrom) {
        for (IGameLogger logger : loggers) {
            logger.draw(position, hai, drawFrom);
        }
    }

    @Override
    public void kiri(int position, IHai hai, boolean reach, boolean handKiri) {
        for (IGameLogger logger : loggers) {
            logger.kiri(position, hai, reach, handKiri);
        }
    }

    @Override
    public void reach2(int[] increaseAndDecrease, int kyotaku) {
        for (IGameLogger logger : loggers) {
            logger.reach2(increaseAndDecrease, kyotaku);
        }
    }

    @Override
    public void chii(int position, int fromPosition, IHai chiiHai, IHai selfHai1, IHai selfHai2) {
        for (IGameLogger logger : loggers) {
            logger.chii(position, fromPosition, chiiHai, selfHai1, selfHai2);
        }
    }

    @Override
    public void pon(int position, int fromPosition, IHai ponHai) {
        for (IGameLogger logger : loggers) {
            logger.pon(position, fromPosition, ponHai);
        }
    }

    @Override
    public void minkan(int position, int fromPosition, IHai kanHai) {
        for (IGameLogger logger : loggers) {
            logger.minkan(position, fromPosition, kanHai);
        }
    }

    @Override
    public void kakan(int position, IHai kanHai) {
        for (IGameLogger logger : loggers) {
            logger.kakan(position, kanHai);
        }
    }

    @Override
    public void annkan(int position, IHai annkanHai) {
        for (IGameLogger logger : loggers) {
            logger.annkan(position, annkanHai);
        }
    }

    @Override
    public void kita(int position, IHai hai) {
        for (IGameLogger logger : loggers) {
            logger.kita(position, hai);
        }
    }

    @Override
    public void ron(int position, int fromPosition, List<IYaku> yakus, int ban, int fu, int score,
            int[] increaseAndDecrease) {
        for (IGameLogger logger : loggers) {
            logger.ron(position, fromPosition, yakus, ban, fu, score, increaseAndDecrease);
        }
    }

    @Override
    public void tsumo(int position, List<IYaku> yakus, int ban, int fu, int score, int[] increaseAndDecrease) {
        for (IGameLogger logger : loggers) {
            logger.tsumo(position, yakus, ban, fu, score, increaseAndDecrease);
        }
    }

    @Override
    public void ryuukyoku(String ryuukyokuName, int[] increaseAndDecrease) {
        for (IGameLogger logger : loggers) {
            logger.ryuukyoku(ryuukyokuName, increaseAndDecrease);
        }
    }

    @Override
    public void nextDoraDisplay(IHai doraDisplay) {
        for (IGameLogger logger : loggers) {
            logger.nextDoraDisplay(doraDisplay);
        }
    }
}
