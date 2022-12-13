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
 * 空的游戏日志记录器，
 * 
 * @author terra.lian
 * @since 2022-10-28
 */
public class EmptyGameLogger implements IGameLogger {
    /**
     * 通用实例
     */
    private static final EmptyGameLogger EMPTY = new EmptyGameLogger();

    /**
     * 返回一个空的日志接口
     */
    public static EmptyGameLogger empty() {
        return EMPTY;
    }

    /**
     * 不可构造
     */
    private EmptyGameLogger() {}

    @Override
    public void gameStart(List<IPlayer> players, IGameCore gameCore) {

    }

    @Override
    public void kyokuStart(int round, KazeEnum kaze, int oya, int honba, List<ITehai> haipais) {

    }

    @Override
    public void kyokuEnd(int round, int[] playerPoints) {

    }

    @Override
    public void gameEnd() {

    }

    @Override
    public void draw(int position, IHai hai, DrawFrom drawFrom) {

    }

    @Override
    public void kiri(int position, IHai hai, boolean reach, boolean handKiri) {

    }

    @Override
    public void reach2(int[] increaseAndDecrease, int kyotaku) {

    }

    @Override
    public void chii(int position, int fromPosition, IHai chiiHai, IHai selfHai1, IHai selfHai2) {

    }

    @Override
    public void pon(int position, int fromPosition, IHai ponHai) {

    }

    @Override
    public void minkan(int position, int fromPosition, IHai kanHai) {

    }

    @Override
    public void kakan(int position, IHai kanHai) {

    }

    @Override
    public void annkan(int position, IHai annkanHai) {

    }

    @Override
    public void kita(int position, IHai hai) {

    }

    @Override
    public void ron(int position, int fromPosition, List<IYaku> yakus, int ban, int fu, int score, int[] increaseAndDecrease) {

    }

    @Override
    public void tsumo(int position, List<IYaku> yaku, int ban, int fu, int score, int[] increaseAndDecrease) {

    }

    @Override
    public void ryuukyoku(String ryuukyokuName, int[] increaseAndDecrease) {

    }

    @Override
    public void switchPlayer(int prevPlayer, int nextPlayer) {

    }

    @Override
    public void nextDoraDisplay(IHai doraDisplay) {

    }
}
