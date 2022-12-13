package com.github.terralian.fastmaj.agari;

import java.util.Set;

import com.github.terralian.fastmaj.FastMajong;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.river.IHaiRiver;
import com.github.terralian.fastmaj.tehai.ISyantenCalculator;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.IYuukouhaiCalculator;

/**
 * 振听判定的默认实现
 * 
 * @author terra.lian 
 */
public class FuritenChecker implements IFuritenChecker {

    /**
     * 向听计算器
     */
    private ISyantenCalculator syantenCalculator;
    /**
     * 有效牌计算器
     */
    private IYuukouhaiCalculator yuukouhaiCalculator;

    public FuritenChecker() {
        syantenCalculator = FastMajong.doGetSyantenCalculator();
        yuukouhaiCalculator = FastMajong.doGetYuukouhaiCalculator();
    }

    /**
     * 判定手牌是否振听
     * 
     * @param tehai 手牌
     * @param haiRiver 牌河
     */
    @Override
    public boolean test(ITehai tehai, IHaiRiver haiRiver) {
        if (syantenCalculator.calcMin(tehai.getHand()) > 0) {
            return false;
        }
        Set<IHai> yuukous = yuukouhaiCalculator.calcMin(tehai);
        return yuukous.stream().anyMatch(k -> haiRiver.hasKiri(k));
    }

}
