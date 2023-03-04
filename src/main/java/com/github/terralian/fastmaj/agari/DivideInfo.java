package com.github.terralian.fastmaj.agari;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.util.StringUtil;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 和牌分割，当和牌时分割手牌的面刻雀头及部分役
 * <p/>
 * 用于判断某些役种，及加速判断，如平和等。一个手牌的和牌分割不一定只有一个，需要根据最终打点选择。
 * 
 * @author terra.lian 
 * @see <a href="https://github.com/EndlessCheng/mahjong-helper/blob/master/util/agari.go">参考</a>
 */
@Getter
@Setter
@Accessors(chain = true)
public class DivideInfo {
    /**
     * 雀头
     */
    private IHai jantou;
    /**
     * 手牌内的刻子（手牌内的暗刻或者明刻）
     */
    private Set<IHai> handKozuFirst;
    /**
     * 所有刻子（包含鸣牌或暗杠固定后的，暗刻，明刻，暗杠明杠)
     */
    private Set<IHai> allKanKozuFirst;
    /**
     * 暗刻，通过手牌计算出哪些刻子是暗刻
     */
    private Set<IHai> annkoFirst;
    /**
     * 明刻，通过手牌计算出哪些刻子是明刻 明刻不仅指碰牌，还有听双碰荣和，此时荣和的刻子算明刻.
     */
    private Set<IHai> minkoFirst;
    /**
     * 所有的顺子
     */
    private List<IHai> allShunzuFirst;
    /**
     * 手牌中的顺子的第一枚
     */
    private List<IHai> handShunzuFirst;
    /**
     * 是否是七对子
     */
    private boolean tiitoitu;
    /**
     * 是否是九莲宝灯
     */
    private boolean tyuuren;
    /**
     * 两杯口
     */
    private boolean ryanpeikou;
    /**
     * 一杯口
     */
    private boolean iipeikou;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("{");

        builder.append("雀头：").append(jantou).append(", ");
        builder.append("刻子：[").append(StringUtil.join(",", allKanKozuFirst)).append("], ");
        builder.append("暗刻：[").append(StringUtil.join(",", allKanKozuFirst)).append("],");
        builder.append("顺子：[").append(StringUtil.join(",", allShunzuFirst)).append("],");

        builder.append("部分役：[");
        List<String> yakuNames = new ArrayList<>(4);
        if (tiitoitu) {
            yakuNames.add("七对子");
        }
        if (tyuuren) {
            yakuNames.add("九莲宝灯");
        }
        if (ryanpeikou) {
            yakuNames.add("两杯口");
        }
        if (iipeikou) {
            yakuNames.add("一杯口");
        }
        builder.append(String.join(",", yakuNames));
        builder.append("]");

        builder.append("}");
        return builder.toString();
    }
}
