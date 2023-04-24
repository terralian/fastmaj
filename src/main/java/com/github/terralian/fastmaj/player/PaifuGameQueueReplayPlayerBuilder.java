package com.github.terralian.fastmaj.player;

import java.util.ArrayList;
import java.util.List;

import com.github.terralian.fastmaj.game.event.ActionEvent;
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
import com.github.terralian.fastmaj.paifu.domain.PaifuGame;
import com.github.terralian.fastmaj.paifu.domain.PaifuKyoku;

/**
 * {@link QueueReplayPlayer}转换帮助类，将{@link PaifuGame}转换为QueueReplayPlayer
 *
 * @author terra.lian
 * @since 2023-04-24
 */
public abstract class PaifuGameQueueReplayPlayerBuilder {

    /**
     * 将牌谱解析的结果转换为队列回放的玩家实例
     *
     * @param paifuGame 牌谱解析结果
     */
    public static List<QueueReplayPlayer> toPlayer(PaifuGame paifuGame) {
        List<QueueReplayPlayer> list = new ArrayList<>(paifuGame.getPlayerSize());
        // 初始化
        for (int i = 0; i < paifuGame.getPlayerSize(); i++) {
            list.add(new QueueReplayPlayer());
        }

        for (PaifuKyoku paifuKyoku : paifuGame.getKyokus()) {
            int round = paifuKyoku.getRound();
            List<ActionEvent> events = paifuKyoku.getActions();
            int actionCount = -1;
            for (int i = 0; i < events.size(); i++) {
                ActionEvent abstractEvent = events.get(i);
                actionCount += 1;
                switch (abstractEvent.getType()) {
                    case CHII:
                        addChii((ChiiEvent) abstractEvent, list, round, actionCount);
                        break;
                    case MINKAN:
                        addMinkan((MinkanEvent) abstractEvent, list, round, actionCount);
                        break;
                    case PON:
                        addPon((PonEvent) abstractEvent, list, round, actionCount);
                        break;
                    case RON:
                        addRon((RonEvent) abstractEvent, list, round, actionCount);
                        break;
                    case ANNKAN:
                        addAnnkan((AnnkanEvent) abstractEvent, list, round);
                        break;
                    case KAKAN:
                        addKakan((KakanEvent) abstractEvent, list, round);
                        break;
                    case KIRI:
                        addKiri((KiriEvent) abstractEvent, list, round);
                        break;
                    case KITA:
                        addKita((KitaEvent) abstractEvent, list, round);
                        break;
                    case REACH:
                        addReach((ReachEvent) abstractEvent, list, round);
                        break;
                    case RYUUKYOKU99:
                        addRyuukyoku99((Ryuukyoku99Event) abstractEvent, list, round);
                        break;
                    case TSUMO:
                        addTsumo((TsumoEvent) abstractEvent, list, round);
                        break;
                    case DRAW:
                        // 无需处理
                        break;
                }
            }
        }

        return list;
    }

    private static void addChii(ChiiEvent event, List<QueueReplayPlayer> list, int round, int actionCount) {
        QueueReplayPlayer targetPlayer = list.get(event.getPosition());
        RiverActionCall riverActionCall = RiverActionCall.newChii(event.getSelfHais());
        targetPlayer.addRiverAction(round, actionCount, riverActionCall);
    }

    private static void addMinkan(MinkanEvent event, List<QueueReplayPlayer> list, int round, int actionCount) {
        QueueReplayPlayer targetPlayer = list.get(event.getPosition());
        RiverActionCall riverActionCall = RiverActionCall.newMinkan(event.getSelfHais());
        targetPlayer.addRiverAction(round, actionCount, riverActionCall);
    }

    private static void addPon(PonEvent event, List<QueueReplayPlayer> list, int round, int actionCount) {
        QueueReplayPlayer targetPlayer = list.get(event.getPosition());
        RiverActionCall riverActionCall = RiverActionCall.newPon(event.getSelfHais());
        targetPlayer.addRiverAction(round, actionCount, riverActionCall);
    }

    private static void addRon(RonEvent event, List<QueueReplayPlayer> list, int round, int actionCount) {
        QueueReplayPlayer targetPlayer = list.get(event.getPosition());
        RiverActionCall riverActionCall = RiverActionCall.RON;
        targetPlayer.addRiverAction(round, actionCount, riverActionCall);
    }

    private static void addAnnkan(AnnkanEvent event, List<QueueReplayPlayer> list, int round) {
        QueueReplayPlayer targetPlayer = list.get(event.getPosition());
        TehaiActionCall call = TehaiActionCall.newAnnkan(event.getAnnkanHai()[0]);
        targetPlayer.addTehaiAction(round, call);
    }

    private static void addKakan(KakanEvent event, List<QueueReplayPlayer> list, int round) {
        QueueReplayPlayer targetPlayer = list.get(event.getPosition());
        TehaiActionCall call = TehaiActionCall.newKakan(event.getKakanHai());
        targetPlayer.addTehaiAction(round, call);
    }

    private static void addKiri(KiriEvent event, List<QueueReplayPlayer> list, int round) {
        QueueReplayPlayer targetPlayer = list.get(event.getPosition());
        TehaiActionCall call = TehaiActionCall.newKiri(event.getKiriHai());
        targetPlayer.addTehaiAction(round, call);
    }

    private static void addKita(KitaEvent event, List<QueueReplayPlayer> list, int round) {
        QueueReplayPlayer targetPlayer = list.get(event.getPosition());
        TehaiActionCall call = TehaiActionCall.newKita(HaiPool.z(4));
        targetPlayer.addTehaiAction(round, call);
    }

    private static void addReach(ReachEvent event, List<QueueReplayPlayer> list, int round) {
        QueueReplayPlayer targetPlayer = list.get(event.getPosition());
        TehaiActionCall call = TehaiActionCall.newReach(event.getReachHai());
        targetPlayer.addTehaiAction(round, call);
    }

    private static void addRyuukyoku99(Ryuukyoku99Event event, List<QueueReplayPlayer> list, int round) {
        QueueReplayPlayer targetPlayer = list.get(event.getPosition());
        TehaiActionCall call = TehaiActionCall.newRyuukyoku();
        targetPlayer.addTehaiAction(round, call);
    }

    private static void addTsumo(TsumoEvent event, List<QueueReplayPlayer> list, int round) {
        QueueReplayPlayer targetPlayer = list.get(event.getPosition());
        TehaiActionCall call = TehaiActionCall.newTsumo();
        targetPlayer.addTehaiAction(round, call);
    }
}
