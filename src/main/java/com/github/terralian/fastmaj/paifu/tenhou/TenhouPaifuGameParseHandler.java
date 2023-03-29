package com.github.terralian.fastmaj.paifu.tenhou;

import java.util.ArrayList;
import java.util.List;

import com.github.terralian.fastmaj.game.KazeEnum;
import com.github.terralian.fastmaj.game.event.DrawEvent;
import com.github.terralian.fastmaj.game.event.river.ChiiEvent;
import com.github.terralian.fastmaj.game.event.river.PonEvent;
import com.github.terralian.fastmaj.game.event.tehai.AnnkanEvent;
import com.github.terralian.fastmaj.game.event.tehai.KiriEvent;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.hai.IHai;
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
    public void startGame(boolean isSanma, int taku, boolean isTonnan, boolean isSoku, boolean isUseAka, boolean isAriAri,
            String[] playerNames, int[] playerRates, String[] playerDans) {
        paifuGame.setRoom(TenhouPaifuStringPool.TAKU[taku])  //
                .setPlatform(TenhouPaifuStringPool.PLATFORM) //
                .setPlayerSize(isSanma ? 3 : 4) //
                .setEndBakaze(isTonnan ? KazeEnum.NAN : KazeEnum.DON) //
                .setUseRed(isUseAka) //
                .setPlayerNames(playerNames) //
                .setKyokus(new ArrayList<>()); //
    }

    @Override
    public void endGame(int[] playerPoints, int[] playerScores) {
        paifuGame.setEndPoints(playerPoints);
    }

    @Override
    public void startKyoku(int[] playerPoints, List<List<Integer>> playerHaipais, int oya, int bakaze, int kyoku, int honba, int kyotaku,
            int firstDoraDisplay, int[] yama) {
        currentKyoku = new PaifuKyoku();
        currentKyoku.setStartPoints(playerPoints) //
                .setOya(oya) //
                .setBakaze(KazeEnum.getByOrder(bakaze)) //
                .setHonba(honba) //
                .setKyotaku(kyotaku) //
                .setYamas(yama) //
                .setActions(new ArrayList<>());
    }

    @Override
    public void endKyoku(int[] playerPoints) {
        paifuGame.getKyokus().add(currentKyoku);
        currentKyoku = null;
    }

    @Override
    public void draw(int position, int tsumoHai) {
        DrawEvent drawEvent = new DrawEvent() //
                .setDrawHai(getHaiById(tsumoHai)) //
                .setPosition(position);
        // TODO
        currentKyoku.getActions().add(drawEvent);
    }

    @Override
    public void kiri(int position, int kiriHai) {
        KiriEvent kiriEvent = new KiriEvent() //
                .setKiriHai(getHaiById(kiriHai)) //
                .setPosition(position); //
        currentKyoku.getActions().add(kiriEvent);
    }

    @Override
    public void chi(int position, int from, int[] selfHai, int nakiHai) {
        ChiiEvent chiiEvent = new ChiiEvent() //
                .setFrom(from) //
                .setPosition(position) //
                .setSelfHais(getHaiByIds(selfHai)) //
                .setNakiHai(getHaiById(nakiHai)); //
        currentKyoku.getActions().add(chiiEvent);
    }

    @Override
    public void pon(int position, int from, int[] selfHai, int nakiHai) {
        PonEvent ponEvent = new PonEvent() //
                .setFrom(from) //
                .setPosition(position) //
                .setSelfHais(getHaiByIds(selfHai)) //
                .setNakiHai(getHaiById(nakiHai)); //
        currentKyoku.getActions().add(ponEvent);
    }

    @Override
    public void annkan(int position, int[] selfHai) {
        AnnkanEvent annkanEvent = new AnnkanEvent() //
                .setPosition(position) //
                .setAnnkanHai(getHaiByIds(selfHai)); //
        currentKyoku.getActions().add(annkanEvent);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PaifuGame getParseData() {
        return paifuGame;
    }

    /**
     * 根据id编码获取牌对象
     *
     * @param hai 牌id
     */
    private IHai getHaiById(int hai) {
        return HaiPool.getById(hai, paifuGame.isUseRed(), 1);
    }

    /**
     * 根据id编码获取牌数组对象
     *
     * @param hais 牌id
     */
    private IHai[] getHaiByIds(int[] hais) {
        IHai[] h = new IHai[hais.length];
        for (int i = 0; i < hais.length; i++) {
            h[i] = getHaiById(hais[i]);
        }
        return h;
    }
}
