package com.github.terralian.fastmaj.player;

import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.hai.IHai;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 手牌动作请求参数，如模切暗杠等，该类用于存储玩家所选动作的请求参数返回给游戏。
 *
 * @author terra.lian
 */
@Getter
@Setter
@Accessors(chain = true)
public class TehaiActionCall {

    /**
     * 动作的牌
     */
    private IHai actionHai;

    /**
     * 动作类型
     */
    private TehaiActionType actionType;

    // --------------------------------------------------
    // 工厂方法
    // --------------------------------------------------

    /**
     * 构建一个切牌动作
     * 
     * @param kiriHai 切的牌
     */
    public static TehaiActionCall newKiri(IHai kiriHai) {
        return new TehaiActionCall() //
                .setActionType(TehaiActionType.KIRI) //
                .setActionHai(kiriHai);
    }

    /**
     * 构建一个立直动作
     * 
     * @param reachHai 立直的牌
     */
    public static TehaiActionCall newReach(IHai reachHai) {
        return new TehaiActionCall() //
                .setActionType(TehaiActionType.REACH) //
                .setActionHai(reachHai);
    }

    /**
     * 构建一个暗杠动作参数
     *
     * @param annkanHai 暗杠的牌
     */
    public static TehaiActionCall newAnnkan(IHai annkanHai) {
        return new TehaiActionCall() //
                .setActionType(TehaiActionType.ANNKAN) //
                .setActionHai(annkanHai);
    }

    /**
     * 构建一个加杠动作参数
     *
     * @param kakanHai 加杠的牌
     */
    public static TehaiActionCall newKakan(IHai kakanHai) {
        return new TehaiActionCall() //
                .setActionType(TehaiActionType.KAKAN) //
                .setActionHai(kakanHai);
    }

    /**
     * 构建一个拔北动作参数
     *
     * @param kitaHai 拔北的牌
     */
    public static TehaiActionCall newKita(IHai kitaHai) {
        return new TehaiActionCall() //
                .setActionType(TehaiActionType.KITA) //
                .setActionHai(kitaHai);
    }

    /**
     * 构建一个自摸动作参数
     */
    public static TehaiActionCall newTsumo() {
        return new TehaiActionCall() //
                .setActionType(TehaiActionType.TSUMO);
    }

    /**
     * 构建一个流局动作参数
     */
    public static TehaiActionCall newRyuukyoku() {
        return new TehaiActionCall() //
                .setActionType(TehaiActionType.RYUUKYOKU);
    }

    @Override
    public String toString() {
        return actionType.toString() + actionHai.toString();
    }
}
