package com.github.terralian.fastmaj.replay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.terralian.fastmaj.encode.Encode136;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.log.EmptyGameLogger;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.paifu.tenhou.TenhouPaifuStringPool;
import com.github.terralian.fastmaj.player.QueueReplayPlayer;
import com.github.terralian.fastmaj.player.RiverActionCall;
import com.github.terralian.fastmaj.player.TehaiActionCall;

/**
 * 用于构建{@link QueueReplayPlayer}，生成牌谱个数的包含所有玩家动作的队列回放玩家
 * 
 * @author terra.lian
 */
public class TenhouQueueReplayPlayerBuilder extends TenhouMajongReplay {

    /**
     * 需要构建的集合
     */
    private List<QueueReplayPlayer> value;
    /**
     * 所有对局结束时，玩家分数
     */
    private List<Integer[]> allRoundPoints;

    /**
     * 初始化构建{@link TenhouQueueReplayPlayerBuilder}
     */
    public TenhouQueueReplayPlayerBuilder() {
        super(GameConfig.defaultRule(), EmptyGameLogger.empty());
        allRoundPoints = new ArrayList<>();
    }

    /**
     * 获取已完成初始化的队列回放玩家
     */
    public List<QueueReplayPlayer> getValue() {
        return value;
    }

    /**
     * 获取某个对局玩家的分数信息
     * 
     * @param round 对局数
     */
    public Integer[] getRoundPoint(int round) {
        return allRoundPoints.get(round);
    }

    /**
     * 获取所有
     * 
     * @return
     */
    public List<Integer[]> getAllRoundPoints() {
        return allRoundPoints;
    }

    /**
     * 开始游戏，初始化{@link QueueReplayPlayer}集合
     */
    @Override
    public void startGame(boolean isSanma, int taku, boolean isTonnan, boolean isSoku, boolean isUseAka, boolean isAriAri,
            String[] playerNames, int[] playerRates, String[] playerDans) {
        super.startGame(isSanma, taku, isTonnan, isSoku, isUseAka, isAriAri, playerNames, playerRates, playerDans);
        value = new ArrayList<>();
        for (int i = 0; i < config.getPlayerSize(); i++) {
            value.add(new QueueReplayPlayer());
        }
    }

    @Override
    public void endKyoku(int[] playerPoints) {
        allRoundPoints.add(Arrays.stream(playerPoints).boxed().toArray(Integer[]::new));
        super.endKyoku(playerPoints);
    }

    /**
     * 弃牌，分为立直和非立直，为对应玩家设置手牌动作
     */
    @Override
    public void kiri(int position, int kiriHai) {
        IHai hai = HaiPool.getById(kiriHai, useRed);
        // 立直
        if (reachStep1) {
            TehaiActionCall actionCall = TehaiActionCall.newReach(hai);
            value.get(position).addTehaiAction(gameCore.getRound(), actionCall);
        } else {
            // 非立直
            TehaiActionCall actionCall = TehaiActionCall.newKiri(hai);
            value.get(position).addTehaiAction(gameCore.getRound(), actionCall);
        }

        super.kiri(position, kiriHai);
    }

    /**
     * 玩家牌河动作吃记录
     */
    @Override
    public void chi(int position, int from, int[] selfHai, int nakiHai) {
        IHai[] selfHais = Encode136.toHais(selfHai, useRed);
        RiverActionCall actionCall = RiverActionCall.newChii(selfHais);
        value.get(position).addRiverAction(gameCore.getRound(), gameCore.getActionCount(), actionCall);

        super.chi(position, from, selfHai, nakiHai);
    }

    /**
     * 玩家牌河动作碰记录
     */
    @Override
    public void pon(int position, int from, int[] selfHai, int nakiHai) {
        IHai[] selfHais = Encode136.toHais(selfHai, useRed);
        RiverActionCall actionCall = RiverActionCall.newPon(selfHais);
        value.get(position).addRiverAction(gameCore.getRound(), gameCore.getActionCount(), actionCall);

        super.pon(position, from, selfHai, nakiHai);
    }

    /**
     * 玩家牌河动作明杠记录
     */
    @Override
    public void minkan(int position, int from, int[] selfHai, int nakiHai) {
        IHai[] selfHais = Encode136.toHais(selfHai, useRed);
        RiverActionCall actionCall = RiverActionCall.newMinkan(selfHais);
        value.get(position).addRiverAction(gameCore.getRound(), gameCore.getActionCount(), actionCall);

        super.minkan(position, from, selfHai, nakiHai);
    }

    @Override
    public void annkan(int position, int[] selfHai) {
        TehaiActionCall actionCall = TehaiActionCall.newAnnkan(HaiPool.getById(selfHai[0], useRed));
        value.get(position).addTehaiAction(gameCore.getRound(), actionCall);

        super.annkan(position, selfHai);
    }

    @Override
    public void kakan(int position, int from, int[] selfHai, int nakiHai, int addHai) {
        TehaiActionCall actionCall = TehaiActionCall.newKakan(HaiPool.getById(addHai, useRed));
        value.get(position).addTehaiAction(gameCore.getRound(), actionCall);

        super.kakan(position, from, selfHai, nakiHai, addHai);
    }

    @Override
    public void kita(int position) {
        TehaiActionCall actionCall = TehaiActionCall.newKita(HaiPool.getByValue(Encode34.BEI));
        value.get(position).addTehaiAction(gameCore.getRound(), actionCall);

        super.kita(position);
    }

    @Override
    public void agari(int position, int from, List<String> yaku, int han, int hu, int score, int[] increaseAndDecrease) {
        if (position != from) {
            RiverActionCall actionCall = RiverActionCall.RON;
            value.get(position).addRiverAction(gameCore.getRound(), gameCore.getActionCount(), actionCall);
        } else {
            TehaiActionCall actionCall = TehaiActionCall.newTsumo();
            value.get(position).addTehaiAction(gameCore.getRound(), actionCall);
        }

        super.agari(position, from, yaku, han, hu, score, increaseAndDecrease);
    }

    @Override
    public void ryuukyoku(int[] increaseAndDecrease, String type) {
        // 九种九牌
        if (TenhouPaifuStringPool.RYUUKYOKU_TYPE_99.equals(type)) {
            TehaiActionCall actionCall = TehaiActionCall.newRyuukyoku();
            value.get(gameCore.getPosition()).addTehaiAction(gameCore.getRound(), actionCall);
        } else if (TenhouPaifuStringPool.RYUUKYOKU_TYPE_RON_3.equals(type)) {
            // 三家和了，牌谱上没有存哪个三家请求的和了，需要根据最后动作的玩家来增加动作
            int currentPlayer = gameCore.getPosition();
            for (int i = 0; i < 4; i++) {
                if (i == currentPlayer) {
                    continue;
                }
                RiverActionCall actionCall = RiverActionCall.RON;
                value.get(i).addRiverAction(gameCore.getRound(), gameCore.getActionCount(), actionCall);
            }
        }

        super.ryuukyoku(increaseAndDecrease, type);
    }
}
