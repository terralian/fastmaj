package com.github.terralian.fastmaj.agari;

import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.river.IHaiRiver;
import com.github.terralian.fastmaj.tehai.ISyatenCalculator;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.IYuukouhaiCalculator;

import java.util.Set;

/**
 * 振听判定的默认实现
 * 
 * @author terra.lian 
 */
public class FuritenChecker implements IFuritenChecker {

    /**
     * 向听计算器
     */
    private final ISyatenCalculator syatenCalculator;
    /**
     * 有效牌计算器
     */
    private final IYuukouhaiCalculator yuukouhaiCalculator;

    public FuritenChecker(ISyatenCalculator syatenCalculator, IYuukouhaiCalculator yuukouhaiCalculator) {
        this.syatenCalculator = syatenCalculator;
        this.yuukouhaiCalculator = yuukouhaiCalculator;
    }

    /**
     * 判定手牌是否振听
     *
     * @param tehai 手牌
     * @param haiRiver 牌河
     */
    @Override
    public boolean test(ITehai tehai, IHaiRiver haiRiver) {
        if (syatenCalculator.calcMin(tehai.getHand()) > 0) {
            return false;
        }
        Set<IHai> yuukous = yuukouhaiCalculator.calcMin(tehai);
        return yuukous.stream().anyMatch(haiRiver::hasKiri);
    }
}
