package com.github.terralian.fastmaj.agari;

import com.github.terralian.fastmaj.river.IHaiRiver;
import com.github.terralian.fastmaj.tehai.ITehai;

/**
 * 振听判定器，TODO 引入到荣和的判定中
 *
 * @author terra.lian
 */
public interface IFuritenChecker {

    /**
     * 判定手牌是否振听
     *
     * @param tehai 手牌
     * @param haiRiver 牌河
     */
    boolean test(ITehai tehai, IHaiRiver haiRiver);
}
