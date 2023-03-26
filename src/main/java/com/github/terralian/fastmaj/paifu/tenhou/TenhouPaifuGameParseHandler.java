package com.github.terralian.fastmaj.paifu.tenhou;

import java.util.ArrayList;
import java.util.List;

import com.github.terralian.fastmaj.game.KazeEnum;
import com.github.terralian.fastmaj.paifu.domain.PaifuGame;
import com.github.terralian.fastmaj.paifu.domain.PaifuKyoku;

/**
 * 天凤牌谱解析器实例，用于将牌谱解析为{@link PaifuGame}
 *
 * @author terra.lian
 * @see PaifuGame
 * @since 2023-03-25
 */
public class TenhouPaifuGameParseHandler implements ITenhouPaifuParseHandler {

    /**
     * 游戏
     */
    private final PaifuGame paifuGame;

    /**
     * 当前对局
     */
    private PaifuKyoku currentKyoku;

    /**
     * 构建{@link TenhouPaifuDecodeHandler}
     */
    public TenhouPaifuGameParseHandler() {
        paifuGame = new PaifuGame();
        currentKyoku = null;
    }

    @Override
    public void shuffleMeta(String seed) {
        paifuGame.setSeed(seed);
    }

    @Override
    public void startGame(boolean isSanma, int taku, boolean isTonnan, boolean isSoku, boolean isUseAka, boolean isAriAri, String[] playerNames, int[] playerRates, String[] playerDans) {
        paifuGame.setRoom(TenhouPaifuStringPool.TAKU[taku]).setPlatform(TenhouPaifuStringPool.PLATFORM).setPlayerSize(isSanma ? 3 : 4).setEndBakaze(isTonnan ? KazeEnum.NAN : KazeEnum.DON).setUseRed(isUseAka).setPlayerNames(playerNames).setKyokus(new ArrayList<>());
    }

    @Override
    public void endGame(int[] playerPoints, int[] playerScores) {
        paifuGame.setEndPoints(playerPoints);
    }

    @Override
    public void startKyoku(int[] playerPoints, List<List<Integer>> playerHaipais, int oya, int bakaze, int kyoku, int honba, int kyotaku, int firstDoraDisplay, int[] yama) {
        currentKyoku = new PaifuKyoku();
        currentKyoku.setStartPoints(playerPoints).setOya(oya).setBakaze(KazeEnum.getByOrder(bakaze)).setHonba(honba).setKyotaku(kyotaku).setYamas(yama);
    }

    @Override
    public void endKyoku(int[] playerPoints) {
        paifuGame.getKyokus().add(currentKyoku);
        currentKyoku = null;
    }

    @Override
    public PaifuGame getParseData() {
        return paifuGame;
    }
}
