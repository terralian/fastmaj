package com.github.terralian.fastmaj.agari;

import java.util.List;

import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;

/**
 * 手牌和了时分割器
 * <p>
 * 提取和了时的部分通用信息{@link DivideInfo}
 * 
 * @author terra.lian 
 */
public interface ITehaiAgariDivider {

    /**
     * 对一副已和了的手牌进行分割，获取分割的信息. 该方法默认手牌为自摸，其自摸牌为最后一枚牌
     * 
     * @param tehai 手牌
     */
    List<DivideInfo> divide(ITehai tehai);

    /**
     * 对一副已和了的手牌进行分割，获取分割的信息，荣和时对荣和进行二次判定，某些牌是否满足暗刻条件
     * 
     * @param tehai 手牌
     * @param isRon 是否荣和
     * @param agariHai 荣和的牌
     */
    List<DivideInfo> divide(ITehai tehai, boolean isRon, IHai agariHai);
}
