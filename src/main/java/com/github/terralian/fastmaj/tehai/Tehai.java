package com.github.terralian.fastmaj.tehai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.player.RivalEnum;

/**
 * {@link ITehai}的默认实现
 * <p/>
 * 该牌的手牌顺序是按摸牌顺序排序的
 * 
 * @author terra.lian 
 */
public class Tehai implements ITehai {

    /**
     * 手牌（所有），包含进张顺序
     */
    private List<IHai> all;
    /**
     * 手内牌（当前可操作部分），吃碰杠暗杠拔北外的牌
     */
    private List<IHai> hand;
    /**
     * 使用种类记录的手牌的可操作部分，可用于快速排序，计算向听
     */
    private int[] hand34;
    /**
     * 手牌固定区
     */
    private ITehaiLock lock;

    /**
     * 拔北数统计
     */
    private int kitaCount;

    // --------------------------------
    // 初始化
    // --------------------------------

    /**
     * 实例化手牌，但是手牌里没有一张牌
     */
    public Tehai() {
        initial();
    }

    /**
     * 根据当前手牌实例化手牌
     * 
     * @param value 值
     */
    public Tehai(List<IHai> value) {
        this();
        for (IHai hai : value) {
            addHai(hai, true);
        }
    }

    /**
     * 初始化手牌，清除所有信息
     */
    public void initial() {
        all = new ArrayList<>();
        hand = new ArrayList<>();
        // 34类型可操作部分初始化
        if (hand34 == null) {
            hand34 = new int[34];
        }
        Arrays.fill(hand34, 0);
        // 固定区初始化
        lock = new TehaiLock();
        // 拔北记录
        kitaCount = 0;
    }

    // --------------------------------
    // 值获取
    // --------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IHai> getAll() {
        return new ArrayList<>(all);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITehaiLock getLock() {
        return lock;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IHai> getHand() {
        return new ArrayList<>(hand);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IHai getDrawHai() {
        return all.size() >= 14 ? all.get(all.size() - 1) : null;
    }

    // --------------------------------
    // 手牌操作
    // --------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(IHai hai) {
        addHai(hai, true);
    }

    /**
     * 弃牌动作，将牌从手牌中打出的动作
     * 
     * @param hai 弃的牌
     */
    @Override
    public boolean kiri(IHai hai) {
        boolean handKiri = !getDrawHai().valueEquals(hai);

        // 移除值List
        all.remove(hai);
        // 减少可操作牌
        reduceOperables(hai);

        return handKiri;
    }

    @Override
    public IHai kiriLast() {
        if (hand.size() % 3 != 2) {
            throw new IllegalStateException("手牌枚数不为3n+2形式，不能模切最后一枚");
        }
        IHai hai = all.remove(all.size() - 1);
        // 减少可操作牌
        hand.remove(hand.size() - 1);
        hand34[hai.getValue()]--;
        return hai;
    }

    /**
     * 副露[吃]操作
     * <p/>
     * 通过指定打搭子牌，完成吃牌的操作，该操作会首先调用{@link #draw(IHai)}将牌加入手中
     * 
     * @param chiHai 吃的牌
     * @param tehai1 搭子牌1
     * @param tehai2 搭子牌2
     */
    @Override
    public void chii(IHai chiHai, IHai tehai1, IHai tehai2) {
        // 增加手牌
        addHai(chiHai, false);
        // 鸣牌
        lock.chi(chiHai, tehai1, tehai2);
        // 副露后可操作牌减少
        reduceOperables(tehai1, tehai2);
    }

    /**
     * 副露[碰]操作
     * <p/>
     * 该操作会首先调用{@link #draw(IHai)}将牌加入手中
     * 
     * @param ponHai 碰的牌
     * @param redFirst 当有红宝牌时，优先使用红宝牌做搭子
     * @param fromPlayer 来源玩家
     */
    @Override
    public void pon(IHai ponHai, boolean redFirst, RivalEnum fromPlayer) {
        IHai[] selfHais = getNakiSelfHais(ponHai.getValue(), redFirst);
        // 增加手牌
        addHai(ponHai, false);
        // 鸣牌
        lock.pon(ponHai, fromPlayer);
        // 副露后可操作牌减少
        reduceOperables(selfHais[0], selfHais[1]);
    }

    /**
     * 明杠
     * <p/>
     * 该操作会首先调用{@link #draw(IHai)}将牌加入手中
     * 
     * @param hai 鸣的牌
     * @param fromPlayer 来源玩家
     */
    @Override
    public void minkan(IHai hai, RivalEnum fromPlayer) {
        // 获取手中的搭子
        IHai[] tehais = getHaiByValue(hai.getValue(), 3);
        // 增加手牌
        addHai(hai, false);
        // 鸣牌
        lock.minkan(hai, fromPlayer);
        // 副露后可操作牌减少
        reduceOperables(tehais);
    }

    /**
     * 加杠
     * 
     * @param hai 加杠的牌
     * @see {@link #canKakan()}
     */
    @Override
    public void kakan(IHai hai) {
        // 鸣牌
        lock.kakan(hai);
        // 副露后可操作牌减少
        reduceOperables(hai);
    }

    /**
     * [非进张操作] 暗杠
     * <p/>
     * 当手上由4枚牌时，通过该操作进行暗杠，暗杠的牌会固定，不可进行后续操作
     * 
     * @param hai 暗杠的牌
     */
    @Override
    public void annkan(IHai hai) {
        IHai[] hais = getHaiByValue(hai.getValue(), 4);
        // 鸣牌
        lock.annkan(hai);
        // 减少可操作牌
        reduceOperables(hais);
    }

    /**
     * [非进张操作] 拔北（限三麻）
     * <p/>
     * 从手中拔北，将北从手牌里去除，仅作为宝牌记录
     * 
     * @param hai 北
     */
    @Override
    public void kita(IHai hai) {
        // 将牌从手牌移除
        if (!all.remove(hai)) {
            throw new IllegalArgumentException("手牌不含可拔北的牌");
        }
        // 拔北的牌计数增加
        kitaCount++;
        // 减少可操作性的牌
        reduceOperables(hai);
    }

    // --------------------------------
    // 操作校验
    // --------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canKiri(IHai hai) {
        return hand34[hai.getValue()] > 0;
    }

    /**
     * 校验某张牌是否可以吃
     * <p/>
     * 校验手牌中是否存在可以吃目标牌的搭子，这个搭子应该是可操作的，该方法也会校验字牌（false）.
     * <p/>
     * 该方法不会校验玩家的坐席，上下家的判断应当由该方法的使用者自行判断.
     * 
     * @param hai 牌
     * @return 若可以吃 true，否则 false.
     */
    @Override
    public boolean canChii(IHai hai) {
        if (hai.isJiHai())
            return false;

        int literal = EncodeMark.clearRed(hai.getLiteral());
        int value34 = hai.getValue();

        // 允许吃7搭子89形式，或者右侧两面吃
        if (literal < 8 && hand34[value34 + 1] > 0 && hand34[value34 + 2] > 0) {
            return true;
        }
        // 允许吃3搭子12形式，或者左侧两面吃
        if (literal > 2 && hand34[value34 - 1] > 0 && hand34[value34 - 2] > 0) {
            return true;
        }
        // 允许坎搭吃
        if (literal != 1 && literal != 9 && hand34[value34 - 1] > 0 && hand34[value34 + 1] > 0) {
            return true;
        }
        return false;
    }

    /**
     * 校验某张牌是否可以碰
     * <p/>
     * 校验手牌中是否存在可用碰的搭子，在一般规则内碰的优先级要大于吃，调用时需要注意。
     * 
     * @param hai 牌
     */
    @Override
    public boolean canPon(IHai hai) {
        return hand34[hai.getValue()] >= 2;
    }

    /**
     * 校验某张牌是否可以明杠
     * <p/>
     * 校验手牌中是否存在可用杠的搭子，在一般规则内杠的优先级要大于吃，调用时需要注意。
     * 
     * @param hai 牌
     */
    @Override
    public boolean canMinkan(IHai hai) {
        return hand34[hai.getValue()] == 3;
    }

    /**
     * 手牌是否能够加杠
     * <p/>
     * 对手牌进行判断，是否存在可操作加杠的牌
     * 
     * @return 若存在则返回true，若不存在则返回false
     */
    @Override
    public boolean canKakan() {
        if (!isNaki())
            return false;
        for (IHai hai : lock.getPonFirst())
            if (hand34[hai.getValue()] > 0)
                return true;
        return false;
    }

    /**
     * 手牌中的某一枚牌是否能够加杠
     * 
     * @param hai 牌
     */
    @Override
    public boolean canKakan(IHai hai) {
        if (!isNaki())
            return false;
        for (IHai ponHai : lock.getPonFirst())
            if (ponHai.valueEquals(hai))
                return true;
        return false;
    }

    /**
     * 手牌是否能够开暗杠
     * <p/>
     * 对手牌进行判断，是否存在可以开暗杠的牌，即手牌中可操作的牌大于4枚的情况。
     */
    @Override
    public boolean canAnkan() {
        for (IHai hai : hand)
            if (hand34[hai.getValue()] > 3)
                return true;
        return false;
    }

    /**
     * 手牌中的某一枚牌是否能够暗杠
     * 
     * @param hai 牌
     */
    @Override
    public boolean canAnkan(IHai hai) {
        return hand34[hai.getValue()] > 3;
    }

    /**
     * 手牌是否能够拔北（限三麻）
     * <p/>
     * 三麻中，校验手牌中是否存在可操作的北，但是本方法不会校验是否在三麻。
     */
    @Override
    public boolean canKita() {
        return hand34[Encode34.PEI] > 0;
    }

    // --------------------------------
    // 状态获取
    // --------------------------------

    /**
     * 手牌是否鸣牌
     */
    @Override
    public boolean isNaki() {
        return lock.isNaki();
    }

    /**
     * 手牌是否暗杠
     */
    @Override
    public boolean isAnnkan() {
        return lock.isAnnkan();
    }

    /**
     * 获取拔北的数量
     */
    @Override
    public int countKita() {
        return kitaCount;
    }

    // --------------------------------
    // 公共方法
    // --------------------------------

    @Override
    public ITehai deepClone() {
        Tehai tehai = new Tehai();
        tehai.all = new ArrayList<>(all);
        tehai.hand = new ArrayList<>(hand);
        tehai.hand34 = Arrays.copyOf(hand34, hand34.length);
        tehai.lock = lock.deepClone();
        tehai.kitaCount = kitaCount;
        return tehai;
    }

    // -----------------------------------------------------
    // 私有方法
    // -----------------------------------------------------

    /**
     * 增加牌
     * <p/>
     * 该方法用于原子增加一张牌，该方法会处理内部的值List及34编码数组，该方法也会同步给可操作List
     * 
     * @param hai 牌
     * @param operable 是否可操作
     */
    private void addHai(IHai hai, boolean operable) {
        all.add(hai);
        if (operable) {
            hand.add(hai);
            hand34[hai.getValue()]++;
        }
    }

    /**
     * 减少可操作牌
     * <p/>
     * 该方法会根据参数移除可操作性List中的牌
     */
    private void reduceOperables(IHai... hais) {
        for (IHai hai : hais) {
            if (!hand.remove(hai)) {
                throw new IllegalStateException("从手牌移除牌失败：" + hai);
            }
            hand34[hai.getValue()]--;
        }
    }

    /**
     * 根据值获取对应的牌，该方法默认手牌中需要的牌数量足够（但不限制超过）
     * 
     * @param value 值
     * @param size 获取的枚数
     */
    private IHai[] getHaiByValue(int value, int size) {
        IHai[] targets = new IHai[size];
        int i = 0;
        for (IHai hai : this.all) {
            if (hai.getValue() == value) {
                targets[i] = hai;
                i++;
            }
        }
        return targets;
    }

    /**
     * 获取鸣牌的自身搭子
     * <p/>
     * 默认返回所有的牌，若优先使用红宝牌，则红宝牌在第一枚
     * 
     * @param value 牌的值
     * @param redFirst 是否优先使用红宝牌作为搭子
     */
    private IHai[] getNakiSelfHais(int value, boolean redFirst) {
        IHai[] targets = new IHai[4];
        int i = 0;
        int redHaiIndex = -1;
        for (IHai hai : this.hand) {
            if (hai.getValue() == value) {
                targets[i] = hai;
                // 红宝牌优先，若符合条件则将红宝牌和第一枚进行交换
                if (hai.isRedDora()) {
                    redHaiIndex = i;
                }
                i++;
            }
        }

        // 含红宝牌的情况，需要计算红宝牌是否优先处理
        if (redHaiIndex >= 0) {
            // 优先的情况放在第一枚，非优先的情况放在最后一枚
            int swapTarget = redFirst ? 0 : i - 1;
            IHai tmp = targets[swapTarget];
            targets[swapTarget] = targets[redHaiIndex];
            targets[redHaiIndex] = tmp;
        }

        return targets;
    }

}
