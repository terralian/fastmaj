package com.github.terralian.fastmaj.game;

/**
 * 流式执行游戏，一旦开始游戏就会执行到结束
 * 
 * @author terra.lian
 */
public class StreamMajongGame extends StepMajongGame {

    /**
     * 开始游戏
     */
    public void startGame() {
        // 初始化游戏
        super.startGame();
        // 判断游戏是否结束，未结束则进入下一局
        while (true) {
            // 开始对局
            // 校验对局是否结束，是的话结束游戏
            if (nextKyoku() == GameRunningState.END) {
                break;
            }
        }
    }

    @Override
    public GameRunningState nextKyoku() {
        if (super.nextKyoku() == GameRunningState.END) {
            return GameRunningState.END;
        }

        // 当牌山还有剩余牌
        // 上一轮牌河处理动作不是吃碰时，摸一枚牌
        // 摸牌前，若上一轮玩家动作不是暗杠等，则切换到下一位玩家来摸牌
        while (nextDraw() == KyokuState.CONTINUE) {

            // 调用玩家接口获取操作，并执行。
            // 仅当九种九牌时，跳出当前对局
            if (nextTehaiAction() == KyokuState.END) {
                break;
            }

            // 对手牌河动作处理，如吃碰杠及荣和操作
            // 荣和情况下，结束当前对局
            if (nextRiverAction() == KyokuState.END) {
                break;
            }
            // 牌河动作后的规则可选时点，可以对立直步骤2进行处理，新宝牌处理
            optionAfterRiverAction();

            // 流局判定
            if (resolverRyuukyoku() == KyokuState.END) {
                break;
            }
        }
        return GameRunningState.CONTINUE;
    }
}
