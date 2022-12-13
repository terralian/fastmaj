package com.github.terralian.fastmaj.river;

import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.NakiEnum;

/**
 * 默认的链式牌河实现
 * 
 * @author terra.lian
 */
public class ChainHaiRiver implements IChainHaiRiver {

    /**
     * 牌河
     */
    private IHaiRiver haiRiver;

    /**
     * 根据牌河构建一个链式牌河
     * 
     * @param haiRiver 牌河
     */
    public ChainHaiRiver(IHaiRiver haiRiver) {
        this.haiRiver = haiRiver;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChainHaiRiver kiri(IHai hai) {
        haiRiver.kiri(hai);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IChainHaiRiver reach(IHai hai) {
        haiRiver.reach(hai);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChainHaiRiver reach(IHai hai, boolean isDoubleReach) {
        haiRiver.reach(hai, isDoubleReach);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChainHaiRiver naki(int player, NakiEnum nakiType) {
        haiRiver.naki(player, nakiType);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChainHaiRiver setSameJun(boolean sameJun) {
        haiRiver.setSameJun(sameJun);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChainHaiRiver setSameFirstJun(boolean sameFirstJun) {
        haiRiver.setSameFirstJun(sameFirstJun);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChainHaiRiver clear() {
        haiRiver.clear();
        return this;
    }
}
