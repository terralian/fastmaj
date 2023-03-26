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
     * 传入所需解析的内容，并构建{@link TenhouPaifuDecodeHandler}
     * <p/>
     * 在类似SAXParser中，在解析的过程中难以获取原始信息，但是在解析开始时则比较容易获取。
     *
     * @param fileName 文件名
     * @param origin 原始数据
     */
    public TenhouPaifuGameParseHandler(String fileName, String origin) {
        this.paifuGame = new PaifuGame();
        this.paifuGame.setOrigin(origin);
        currentKyoku = null;
    }

    @Override
    public void shuffleMeta(String seed) {
        this.paifuGame.setSeed(seed);
    }

    @Override
    public void startGame(boolean isSanma, int taku, boolean isTonnan, boolean isSoku, boolean isUseAka, boolean isAriAri, String[] playerNames, int[] playerRates, String[] playerDans) {
        this.paifuGame.setRoom(TenhouPaifuStringPool.TAKU[taku])
                .setPlatform(TenhouPaifuStringPool.PLATFORM)
                .setPlayerSize(isSanma ? 3 : 4)
                .setEndBakaze(isTonnan ? KazeEnum.NAN : KazeEnum.DON)
                .setUseRed(isUseAka)
                .setPlayerNames(playerNames)
                .setKyokus(new ArrayList<>());
    }

    @Override
    public void endGame(int[] playerPoints, int[] playerScores) {
        this.paifuGame.setEndPoints(playerPoints);
    }

    @Override
    public void startKyoku(int[] playerPoints, List playerHaipais, int oya, int bakaze, int kyoku, int honba, int kyotaku, int firstDoraDisplay, int[] yama) {
        currentKyoku = new PaifuKyoku();
    }

    @Override
    public void endKyoku(int[] playerPoints) {
        this.paifuGame.getKyokus().add(currentKyoku);
        currentKyoku = null;
    }
}
