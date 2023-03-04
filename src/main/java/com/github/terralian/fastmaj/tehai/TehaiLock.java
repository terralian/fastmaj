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
 * @see Menzu
 */
public class TehaiLock implements ITehaiLock {

    /**
     * 所有固定的面子
     */
    private List<Menzu> value;

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
    public Menzu get(int index) {
        return value.get(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Menzu> getAll() {
        return new ArrayList<>(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IHai> getChiFirst() {
        return getSomeFirst(Menzu::isShunzu);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IHai> getNotChiFirst() {
        return getSomeFirst(k -> !k.isShunzu());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IHai> getPonFirst() {
        return getSomeFirst(Menzu::isKozu);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IHai> getKanzuFirst() {
        return getSomeFirst(Menzu::isKanzu);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IHai> getMinkanFirst() {
        return getSomeFirst(Menzu::isMinkan);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IHai> getAnnkanFirst() {
        return getSomeFirst(Menzu::isAnnkan);
    }

    /**
     * 获取一些被参数匹配的第一枚牌
     * 
     * @param predicate 匹配器
     */
    private List<IHai> getSomeFirst(Predicate<Menzu> predicate) {
        return value.stream() //
                .filter(predicate) //
                .map(Menzu::getMenzuFirst) //
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getKanzuSize() {
        return getKanzuFirst().size();
    }

    // ------------------------------------------------
    // 行为动作
    // ------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Menzu chii(IHai chiHai, IHai tehai1, IHai tehai2) {
        IHai minHai = Encode34.min(chiHai, tehai1, tehai2);
        Menzu chiMenzu = Menzu.fromChi(minHai, NakiShape.calcNakiSize(chiHai, tehai1, tehai2));
        value.add(chiMenzu);
        isNaki = true;
        return chiMenzu;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Menzu pon(IHai ponHai, RivalEnum fromPlayer) {
        Menzu menzu = Menzu.fromPon(ponHai, fromPlayer);
        value.add(menzu);
        isNaki = true;
        return menzu;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Menzu minkan(IHai kanHai, RivalEnum fromPlayer) {
        Menzu menzu = Menzu.fromMinkan(kanHai, fromPlayer);
        value.add(menzu);
        isNaki = true;
        return menzu;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Menzu kakan(IHai hai) {
        for ( int i = 0; i < value.size(); i++) {
            Menzu menzu = value.get(i);
            if (menzu.isKozu() && menzu.getMenzuFirst().valueEquals(hai)) {
                Menzu kakanMenzu = menzu.kakan();
                value.set(i, kakanMenzu);
                return kakanMenzu;
            }
        }
        throw new IllegalStateException("无可加杠固定面子");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Menzu annkan(IHai hai) {
        Menzu menzu = Menzu.fromAnnkan(hai);
        value.add(menzu);
        isAnnkan = true;
        return menzu;
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
    public int kanzuSize() {
        return getKanzuFirst().size();
    }

    // ------------------------------------------------
    // 元素操作
    // ------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Menzu remove(int index) {
        Menzu menzu = value.remove(index);
        this.isNaki = value.stream().anyMatch(k -> !k.isAnnkan());
        this.isAnnkan = value.stream().anyMatch(Menzu::isAnnkan);
        return menzu;
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
