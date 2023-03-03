package com.github.terralian.fastmaj.tehai;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.player.RivalEnum;

/**
 * {@link ITehaiLock} 的固定实现
 * <p/>
 * 该实现通过一个集合存储所有的面子，在获取对应集合时手动查询，由于集合大小最大为4，这样的实现性能不会小太多。
 * 
 * @author terra.lian 
 * @see ITehaiLock
 * @see Mentsu
 */
public class TehaiLock implements ITehaiLock {

    /**
     * 所有固定的面子
     */
    private List<Mentsu> value;

    /**
     * 是否鸣牌
     */
    private boolean isNaki;

    /**
     * 是否暗杠
     */
    private boolean isAnnkan;

    /**
     * 初始化空固定区
     */
    public TehaiLock() {
        this.value = new ArrayList<>(4);
        this.isNaki = false;
        this.isAnnkan = false;
    }

    // ------------------------------------------------
    // 值获取
    // ------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Mentsu get(int index) {
        return value.get(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Mentsu> getAll() {
        return new ArrayList<>(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IHai> getChiFirst() {
        return getSomeFirst(Mentsu::isShuntsu);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IHai> getNotChiFirst() {
        return getSomeFirst(k -> !k.isShuntsu());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IHai> getPonFirst() {
        return getSomeFirst(Mentsu::isKotsu);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IHai> getKantsuFirst() {
        return getSomeFirst(Mentsu::isKantsu);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IHai> getMinkanFirst() {
        return getSomeFirst(Mentsu::isMinkan);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IHai> getAnnkanFirst() {
        return getSomeFirst(Mentsu::isAnnkan);
    }

    /**
     * 获取一些被参数匹配的第一枚牌
     * 
     * @param predicate 匹配器
     */
    private List<IHai> getSomeFirst(Predicate<Mentsu> predicate) {
        return value.stream() //
                .filter(predicate) //
                .map(Mentsu::getMentuFirst) //
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getKantsuSize() {
        return getKantsuFirst().size();
    }

    // ------------------------------------------------
    // 行为动作
    // ------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Mentsu chi(IHai chiHai, IHai tehai1, IHai tehai2) {
        IHai minHai = Encode34.min(chiHai, tehai1, tehai2);
        Mentsu chiMentsu = Mentsu.fromChi(minHai, NakiShape.calcNakiSize(chiHai, tehai1, tehai2));
        value.add(chiMentsu);
        isNaki = true;
        return chiMentsu;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mentsu pon(IHai ponHai, RivalEnum fromPlayer) {
        Mentsu mentsu = Mentsu.fromPon(ponHai, fromPlayer);
        value.add(mentsu);
        isNaki = true;
        return mentsu;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mentsu minkan(IHai kanHai, RivalEnum fromPlayer) {
        Mentsu mentsu = Mentsu.fromMinkan(kanHai, fromPlayer);
        value.add(mentsu);
        isNaki = true;
        return mentsu;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mentsu kakan(IHai hai) {
        for ( int i = 0; i < value.size(); i++) {
            Mentsu mentsu = value.get(i);
            if (mentsu.isKotsu() && mentsu.getMentuFirst().valueEquals(hai)) {
                Mentsu kakanMentsu = mentsu.kakan();
                value.set(i, kakanMentsu);
                return kakanMentsu;
            }
        }
        throw new IllegalStateException("无可加杠固定面子");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mentsu annkan(IHai hai) {
        Mentsu mentsu = Mentsu.fromAnnkan(hai);
        value.add(mentsu);
        isAnnkan = true;
        return mentsu;
    }

    // ------------------------------------------------
    // 状态判断
    // ------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return value.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNaki() {
        return isNaki;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAnnkan() {
        return isAnnkan;
    }

    // ------------------------------------------------
    // 数量统计
    // ------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return value.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int kantsuSize() {
        return getKantsuFirst().size();
    }

    // ------------------------------------------------
    // 元素操作
    // ------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Mentsu remove(int index) {
        Mentsu mentsu = value.remove(index);
        this.isNaki = value.stream().anyMatch(k -> !k.isAnnkan());
        this.isAnnkan = value.stream().anyMatch(Mentsu::isAnnkan);
        return mentsu;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        value.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITehaiLock deepClone() {
        TehaiLock tehaiLock = new TehaiLock();
        tehaiLock.isAnnkan = isAnnkan;
        tehaiLock.isNaki = isNaki;
        tehaiLock.value = new ArrayList<>(value);
        return tehaiLock;
    }

}
