package com.github.terralian.fastmaj.paifu.tenhou;

import com.github.terralian.fastmaj.encode.Encode136;
import com.github.terralian.fastmaj.game.KazeEnum;
import com.github.terralian.fastmaj.game.event.ActionEvent;
import com.github.terralian.fastmaj.game.event.DrawEvent;
import com.github.terralian.fastmaj.game.event.river.ChiiEvent;
import com.github.terralian.fastmaj.game.event.river.MinkanEvent;
import com.github.terralian.fastmaj.game.event.river.PonEvent;
import com.github.terralian.fastmaj.game.event.river.RonEvent;
import com.github.terralian.fastmaj.game.event.tehai.AnnkanEvent;
import com.github.terralian.fastmaj.game.event.tehai.KakanEvent;
import com.github.terralian.fastmaj.game.event.tehai.KiriEvent;
import com.github.terralian.fastmaj.game.event.tehai.KitaEvent;
import com.github.terralian.fastmaj.game.event.tehai.ReachEvent;
import com.github.terralian.fastmaj.game.event.tehai.Ryuukyoku99Event;
import com.github.terralian.fastmaj.game.event.tehai.TsumoEvent;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.paifu.domain.PaifuGame;
import com.github.terralian.fastmaj.paifu.domain.PaifuKyoku;
import com.github.terralian.fastmaj.tehai.ITehai;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private PaifuGame paifuGame;

    /**
     * 当前对局
     */
    private PaifuKyoku currentKyoku;

    /**
     * 是否立直步骤1
     */
    protected boolean reachStep1;

    /**
     * 构建{@link TenhouPaifuDecodeHandler}
     */
    public TenhouPaifuGameParseHandler() {
    }

    @Override
    public void shuffleMeta(String seed) {
        paifuGame = new PaifuGame();
        currentKyoku = null;
        reachStep1 = false;
        paifuGame.setSeed(seed);
    }

    @Override
    public void startGame(boolean isSanma, int taku, boolean isTonnan, boolean isSoku, boolean isUseAka, boolean isAriAri,
                          String[] playerNames, int[] playerRates, String[] playerDans) {
        paifuGame.setRoom(TenhouPaifuStringPool.TAKU[taku]) //
                .setPlatform(TenhouPaifuStringPool.PLATFORM) //
                .setPlayerSize(isSanma ? 3 : 4) //
                .setEndBakaze(isTonnan ? KazeEnum.NAN : KazeEnum.DON) //
                .setUseRed(isUseAka) //
                .setPlayerNames(playerNames) //
                .setKyokus(new ArrayList<>());
    }

    @Override
    public void endGame(int[] playerPoints, int[] playerScores) {
        paifuGame.setEndPoints(playerPoints);
    }

    @Override
    public void startKyoku(int[] playerPoints, List<List<Integer>> playerHaipais, int oya, int bakaze, int kyoku, int honba, int kyotaku,
                           int firstDoraDisplay, int[] yama) {
        List<ITehai> startTehais = playerHaipais.stream() //
                .map(k -> Encode136.toTehai(k, paifuGame.isUseRed())) //
                .collect(Collectors.toList());

        currentKyoku = new PaifuKyoku();
        currentKyoku.setStartPoints(playerPoints)//
                .setOya(oya)//
                .setBakaze(KazeEnum.getByOrder(bakaze))//
                .setHonba(honba)//
                .setKyotaku(kyotaku) //
                .setYamas(yama) //
                .setStartTehais(startTehais) //
                .setFirstDoraDisplay(HaiPool.getById(firstDoraDisplay))
                .setActions(new ArrayList<>());
    }

    @Override
    public void endKyoku(int[] playerPoints) {
        currentKyoku.setRound(paifuGame.getKyokus().size()) //
                .setEndPoints(playerPoints);
        paifuGame.getKyokus().add(currentKyoku);
        currentKyoku = null;
    }

    @Override
    public void draw(int position, int tsumoHai) {
        DrawEvent drawEvent = new DrawEvent() //
                .setDrawHai(getHaiById(tsumoHai)) //
                .setPosition(position);
        currentKyoku.getActions().add(drawEvent);
    }

    @Override
    public void reach1(int position) {
        reachStep1 = true;
    }

    @Override
    public void kiri(int position, int kiriHai) {
        // 天凤的牌谱立直分为3个步骤
        // 1. 立直宣言，看reach1
        // 2. 打出一枚牌
        // 3. 放置立直棒，看reach2.
        // 其中2、3位原子操作，所以在这里处理事件。
        if (reachStep1) {
            reachStep1 = false;
            ReachEvent reachEvent = new ReachEvent()
                    .setReachHai(getHaiById(kiriHai))
                    .setPosition(position);
            currentKyoku.getActions().add(reachEvent);
        } else {
            // 非立直
            KiriEvent kiriEvent = new KiriEvent() //
                    .setKiriHai(getHaiById(kiriHai)) //
                    .setPosition(position); //
            currentKyoku.getActions().add(kiriEvent);
        }
    }

    @Override
    public void chi(int position, int from, int[] selfHai, int nakiHai) {
        ChiiEvent chiiEvent = new ChiiEvent() //
                .setFrom(from) //
                .setPosition(position) //
                .setSelfHais(getHaiByIds(selfHai)) //
                .setFromHai(getHaiById(nakiHai)); //
        currentKyoku.getActions().add(chiiEvent);
    }

    @Override
    public void pon(int position, int from, int[] selfHai, int nakiHai) {
        PonEvent ponEvent = new PonEvent() //
                .setFrom(from) //
                .setPosition(position) //
                .setSelfHais(getHaiByIds(selfHai)) //
                .setFromHai(getHaiById(nakiHai)); //
        currentKyoku.getActions().add(ponEvent);
    }

    @Override
    public void annkan(int position, int[] selfHai) {
        AnnkanEvent annkanEvent = new AnnkanEvent() //
                .setPosition(position) //
                .setAnnkanHai(getHaiByIds(selfHai)); //
        currentKyoku.getActions().add(annkanEvent);
    }

    @Override
    public void minkan(int position, int from, int[] selfHai, int nakiHai) {
        MinkanEvent minkanEvent = new MinkanEvent()
                .setFrom(from)
                .setPosition(position)
                .setSelfHais(getHaiByIds(selfHai))
                .setFromHai(getHaiById(nakiHai));
        currentKyoku.getActions().add(minkanEvent);
    }

    @Override
    public void kakan(int position, int from, int[] selfHai, int nakiHai, int addHai) {
        KakanEvent kakanEvent = new KakanEvent()
                .setPosition(position)
                .setKakanHai(getHaiById(addHai));
        currentKyoku.getActions().add(kakanEvent);
    }

    @Override
    public void kita(int position) {
        KitaEvent kitaEvent = new KitaEvent()
                .setPosition(position);
        currentKyoku.getActions().add(kitaEvent);
    }

    @Override
    public void agari(int position, int from, List<String> yaku, int han, int hu, int score,
                      int[] increaseAndDecrease) {
        if (position != from) {
            RonEvent ronEvent = new RonEvent()
                    .setFrom(from)
                    .setPosition(position);
            currentKyoku.getActions().add(ronEvent);
        } else {
            TsumoEvent tsumoEvent = new TsumoEvent()
                    .setPosition(position);
            currentKyoku.getActions().add(tsumoEvent);
        }
    }

    @Override
    public void ryuukyoku(int[] increaseAndDecrease, String type) {
        List<ActionEvent> actionEvents = currentKyoku.getActions();
        // 九种九牌
        if (TenhouPaifuStringPool.RYUUKYOKU_TYPE_99.equals(type)) {
            // 摸牌事件
            ActionEvent lastEvent = actionEvents.get(actionEvents.size() - 1);
            Ryuukyoku99Event event = new Ryuukyoku99Event();
            event.setPosition(lastEvent.getPosition());
            currentKyoku.getActions().add(event);
        }
        // 三家和了的场合，牌谱上仅记录了流局，并不会触发三个荣和事件
        if (TenhouPaifuStringPool.RYUUKYOKU_TYPE_RON_3.equals(type)) {
            ActionEvent lastPlayerEvent = actionEvents.get(actionEvents.size() - 2);
            for (int i = 0; i < paifuGame.getPlayerSize(); i++) {
                if (i == lastPlayerEvent.getPosition()) {
                    continue;
                }
                RonEvent event = new RonEvent();
                event.setFrom(lastPlayerEvent.getPosition());
                event.setPosition(i);
                currentKyoku.getActions().add(event);
            }
        }
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
        return HaiPool.getById(hai, paifuGame.isUseRed());
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
