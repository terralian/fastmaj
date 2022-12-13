package com.github.terralian.fastmaj.tehai;

import java.util.Set;

import com.github.terralian.fastmaj.hai.IHai;

/**
 * 有效牌计算
 * <p>
 * 该接口提供对手牌{@link ITehai}的有效牌，支持鸣牌副露，暗杠的场景。
 * <p>
 * 若向听数为0，则返回的有效牌为所听的牌
 * 
 * @author terra.lian 
 */
public interface IYuukouhaiCalculator {

    /**
     * 计算最小向听数的有效待牌
     * 
     * @param tehai 手牌
     */
    Set<IHai> calcMin(ITehai tehai);

    /**
     * 计算七对子的有效待牌
     * 
     * @param tehai 手牌
     */
    Set<IHai> calcTiitoitu(ITehai tehai);

    /**
     * 计算国士的有效待牌
     * 
     * @param tehai 手牌
     */
    Set<IHai> calcKokusi(ITehai tehai);

    /**
     * 计算通常的有效待牌
     * <p>
     * 即除了国士，七对外的通常手型
     * 
     * @param tehai 手牌
     */
    Set<IHai> calcNormal(ITehai tehai);
}
