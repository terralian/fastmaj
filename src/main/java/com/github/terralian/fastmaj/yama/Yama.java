package com.github.terralian.fastmaj.yama;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.Tehai;
import com.github.terralian.fastmaj.yama.worker.tenhou.TenhouYamaVersionEnum;

/**
 * 牌山的默认实现
 * <p/>
 * 其逻辑为根据天凤的牌山处理逻辑:
 * <ul>
 * <li>顺序为倒序，即数组最后是摸牌的第一枚，而数组的第一枚则是王牌区
 * <li>牌山的上下两枚牌，在数组是线性表示，由于倒序的关系，放置也是倒序
 * <li>其红宝牌为同类型的第1枚
 * </ul>
 *
 * @author terra.lian
 */
public class Yama implements IYama {

    /**
     * 玩家人数
     */
    private final int playerSize;

    /**
     * 原始数据
     */
    private final List<IHai> raw;

    /**
     * 当前牌山偏移
     */
    private int offset;
    /**
     * 当前王牌区偏移
     */
    private int trumpOffset;

    /**
     * 牌山的剩余牌数
     */
    private int countdown;

    /**
     * 宝牌数量
     */
    private int doraSize;

    /**
     * 宝牌指示牌位置
     */
    private int[] doraDisplayIndex = DORA_INDEX_A;

    /**
     * 里宝牌指示牌位置，和宝牌指示牌为位置互换
     */
    private int[] uraDoraDisplayIndex = DORA_INDEX_B;

    /**
     * 王牌区顺序索引
     */
    private static final int[] KING_HAI_INDEX = {1, 0, 3, 2};

    /**
     * 红宝牌所在的位置，天凤为同类型的第1枚，下标为0
     */
    private static final Integer RED_INDEX = 0;

    /**
     * 两组宝牌指示牌位置，在不同时期所使用的不一致
     */
    private static final int[] DORA_INDEX_A = {5, 7, 9, 11, 13};
    private static final int[] DORA_INDEX_B = {4, 6, 8, 10, 12};

    /**
     * 通过136编码的牌山数组来构建牌
     *
     * @param yama 136编码的牌山数组
     * @param useRedHai 是否用红色的牌
     * @param playerSize 玩家人数
     */
    public Yama(int[] yama, boolean useRedHai, int playerSize) {
        if (yama == null || yama.length == 0) {
            throw new IllegalArgumentException("未支持的牌山数组");
        }
        List<IHai> raw = new ArrayList<>(136);
        for (int i : yama) {
            raw.add(HaiPool.getById(i, useRedHai, RED_INDEX));
        }
        this.raw = raw;
        this.playerSize = playerSize;

        reset();
    }

    /**
     * 根据牌山数组来构建牌山
     *
     * @param yamaArray 牌山数组
     * @param useRedHai 是否用红色的牌
     * @param playerSize 玩家人数
     */
    public Yama(IYamaArray yamaArray, boolean useRedHai, int playerSize) {
        this(yamaArray.value(), useRedHai, playerSize);
        // 使用旧牌山
        if (TenhouYamaVersionEnum.T2009_BEFORE.name().equals(yamaArray.version())) {
            this.doraDisplayIndex = DORA_INDEX_B;
            this.uraDoraDisplayIndex = DORA_INDEX_A;
        }
    }

    /**
     * 初始配牌，按玩家数分发
     * <p/>
     * 模拟牌山搭好后，玩家从牌山拿牌的场景
     */
    @Override
    public List<ITehai> deals(int oya) {
        LinkedList<ITehai> tehais = new LinkedList<>();
        for (int p = 0; p < playerSize; p++) {
            tehais.add(new Tehai());
        }

        // 按东南西北顺序，每人拿4枚，共3轮
        for (int i = 0; i < 3; i++) {
            for (int p = 0; p < playerSize; p++) {
                ITehai tehai = tehais.get(p);
                for (int h = 0; h < 4; h++) {
                    IHai hai = nextHai();
                    tehai.draw(hai);
                }
            }
        }

        // 最后一轮，各拿一枚
        for (int p = 0; p < playerSize; p++) {
            ITehai tehai = tehais.get(p);
            IHai hai = nextHai();
            tehai.draw(hai);
        }

        // 上面的顺序默认为庄家起手再到其他家的顺序。
        // 但是和实际坐席映射时就会出现错位，需要调换手牌
        if (oya > 0) {
            for (int i = 0; i < oya; i++) {
                tehais.addFirst(tehais.removeLast());
            }
        }

        return tehais;
    }

    /**
     * 初始化
     */
    @Override
    public void reset() {
        // 136总牌数 -> 14王牌 + 70牌山 + 52(13*4)初始手牌
        this.countdown = 70 + 52;
        this.doraSize = 1;
        this.offset = raw.size() - 1;
        this.trumpOffset = 0;
    }

    /**
     * 从牌山摸下一枚牌
     */
    @Override
    public IHai nextHai() {
        if (countdown == 0) {
            return null;
        }
        int currentOffset = offset;
        offset -= 1;
        countdown -= 1;
        return raw.get(currentOffset);
    }

    /**
     * 从岭上摸下一枚牌
     */
    @Override
    public IHai nextTrumpHai() {
        if (trumpOffset >= 4) {
            return null;
        }

        int currentKingOffset = trumpOffset;
        trumpOffset += 1;
        countdown -= 1;
        // 倒2，倒1，倒4，倒3
        return raw.get(KING_HAI_INDEX[currentKingOffset]);
    }

    /**
     * 翻开下一枚宝牌指示牌
     */
    @Override
    public IHai nextDoraDisplay() {
        if (doraSize >= 5) {
            return null;
        }
        doraSize += 1;
        return raw.get(doraDisplayIndex[doraSize - 1]);
    }

    /**
     * 获取牌山剩余枚数
     */
    @Override
    public int getCountdown() {
        return countdown;
    }

    /**
     * 获取宝牌集合
     */
    @Override
    public List<IHai> getDoraDisplay() {
        List<IHai> doraDisplay = new ArrayList<>();
        for (int i = 0; i < doraSize; i++) {
            doraDisplay.add(raw.get(doraDisplayIndex[i]));
        }
        return doraDisplay;
    }

    /**
     * 获取里宝牌集合
     */
    @Override
    public List<IHai> getUraDoraDisplay() {
        List<IHai> uraDoraDisplay = new ArrayList<>();
        for (int i = 0; i < doraSize; i++) {
            uraDoraDisplay.add(raw.get(uraDoraDisplayIndex[i]));
        }
        return uraDoraDisplay;
    }

    /**
     * 获取牌山内的所有牌
     */
    @Override
    public List<IHai> getRaw() {
        return new ArrayList<>(raw);
    }

    /**
     * 获取所有的宝牌
     */
    @Override
    public List<IHai> getDoras() {
        List<IHai> doras = new ArrayList<>();
        List<IHai> doraDisplays = getDoraDisplay();
        // 返回宝牌
        for (IHai doraDisplay : doraDisplays) {
            IHai dora = HaiPool.nextHai(doraDisplay);
            doras.add(dora);
        }
        return doras;
    }

    @Override
    public List<IHai> getUraDoras() {
        List<IHai> uraDoras = new ArrayList<>();
        List<IHai> uraDoraDisplays = getUraDoraDisplay();
        for (IHai uraDoraDisplay : uraDoraDisplays) {
            IHai dora = HaiPool.nextHai(uraDoraDisplay);
            uraDoras.add(dora);
        }
        return uraDoras;
    }

    /**
     * 当前牌山偏移
     */
    @Override
    public int getOffset() {
        return this.offset;
    }

    /**
     * 当前王牌区偏移
     */
    @Override
    public int getTrumpOffset() {
        return this.trumpOffset;
    }
}
