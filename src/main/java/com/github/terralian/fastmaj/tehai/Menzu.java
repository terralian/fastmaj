package com.github.terralian.fastmaj.tehai;

import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.player.RivalEnum;

/**
 * 表示一副面子
 * <p/>
 * 面子为3枚或者4枚牌组成，其中顺子为3枚相近的牌，刻子（明刻暗刻）为3枚相同的牌，杠子为4枚相同的牌。
 * <p/>
 * 该类设计为不可修改类
 *
 * @author terra.lian
 */
public final class Menzu {

    // ------------------------------------
    // 静态属性
    // ------------------------------------

    /** 0 未知类型 */
    public static final int TYPE_UN_KNOW = 0;
    /** 2 顺子 */
    public static final int TYPE_SHUNZU = 0b00010;
    /** 8 明刻 */
    public static final int TYPE_MINKO = 0b01000;
    /** 16 杠子，包含明杠暗杠 */
    public static final int TYPE_KANZU = 0b10000;
    /** 16 明杠 */
    public static final int TYPE_MINKAN = 0b10000;
    /** 17 暗杠 */
    public static final int TYPE_ANNKAN = 0b10001;

    /** 无形状 */
    public static final int SHAPE_NON = 0;

    // ------------------------------------
    // 属性
    // ------------------------------------

    /**
     * 面子的类型，其值为上面的静态定义量
     */
    private final int type;
    /**
     * 面子的第一枚牌
     */
    private final IHai menzuFirst;
    /**
     * 鸣牌的形状
     *
     * @see NakiShape
     */
    private final int shape;

    // ------------------------------------
    // 构造方法
    // ------------------------------------

    /**
     * 根据参数构建一个面子
     *
     * @param type 面子的类型
     * @param firstHai 第一枚牌
     * @param shape 鸣牌的形状
     */
    public Menzu(int type, IHai firstHai, int shape) {
        this.type = type;
        this.menzuFirst = firstHai;
        this.shape = shape;
    }

    /**
     * 从动作吃构建一副面子
     * <p/>
     * 其形状会固定为鸣最小的那枚牌，若需要固定形状，可以使用{@link #fromChi(IHai, int)}
     *
     * @param firstHai 第一枚牌
     */
    public static Menzu fromChi(IHai firstHai) {
        return fromChi(firstHai, NakiShape.NAKI_MIN);
    }

    /**
     * 从吃动作构建一副面子
     *
     * @param firstHai
     * @param nakiSize {@link NakiShape}中的鸣牌大小
     */
    public static Menzu fromChi(IHai firstHai, int nakiSize) {
        return new Menzu(TYPE_SHUNZU, firstHai, NakiShape.encode(RivalEnum.TOP, nakiSize));
    }

    /**
     * 从碰动作构建一副面子
     * <p/>
     * 该牌的形状固定为从上家碰，若需要固定，可以使用{@link #fromPon(IHai, RivalEnum)}
     *
     * @param firstHai 第一枚牌
     */
    public static Menzu fromPon(IHai firstHai) {
        return fromPon(firstHai, RivalEnum.TOP);
    }

    /**
     * 从碰动作构建一副面子
     *
     * @param firstHai 第一枚牌
     * @param fromRival 被鸣牌的对手
     */
    public static Menzu fromPon(IHai firstHai, RivalEnum fromRival) {
        return new Menzu(TYPE_MINKO, firstHai, NakiShape.encode(fromRival, NakiShape.NAKI_MIN));
    }

    /**
     * 从明杠动作构建一副面子
     * <p/>
     * 该牌的形状固定为从上家杠，若需要固定，可以使用{@link #fromMinkan(IHai, RivalEnum)}
     *
     * @param firstHai 第一枚牌
     */
    public static Menzu fromMinkan(IHai firstHai) {
        return fromMinkan(firstHai, RivalEnum.TOP);
    }

    /**
     * 从明杠动作构建一副面子
     *
     * @param firstHai 第一枚牌
     * @param fromRival 被鸣牌的对手
     */
    public static Menzu fromMinkan(IHai firstHai, RivalEnum fromRival) {
        return new Menzu(TYPE_MINKAN, firstHai, NakiShape.encode(fromRival, NakiShape.NAKI_MIN));
    }

    /**
     * 从暗杠动作构建一副面子
     *
     * @param firstHai
     */
    public static Menzu fromAnnkan(IHai firstHai) {
        return new Menzu(TYPE_ANNKAN, firstHai, SHAPE_NON);
    }

    // ------------------------------------
    // 行为
    // ------------------------------------

    /**
     * 将一副刻子加杠为杠子，返回该面子实例
     * <p/>
     * 面子本身设计为固定不可修改，则需要重新生成
     */
    public Menzu kakan() {
        if (TYPE_MINKO != type) {
            throw new IllegalStateException("面子非明刻，不能进行加杠动作");
        }
        return new Menzu(TYPE_MINKAN, this.menzuFirst, this.shape);
    }

    // ----------------------------------------------------------------
    // 判断动作
    // ----------------------------------------------------------------

    /**
     * 是否是顺子
     */
    public boolean isShunzu() {
        return this.type == TYPE_SHUNZU;
    }

    /**
     * 是否不是顺子，可以判断是否为刻子或者杠子
     */
    public boolean isNotShunzu() {
        return this.type != TYPE_SHUNZU;
    }

    /**
     * 是否是刻子（明刻）
     */
    public boolean isKozu() {
        return this.type == TYPE_MINKO;
    }

    /**
     * 是否是杠子，包含明杠和暗杠
     */
    public boolean isKanzu() {
        return (this.type & TYPE_KANZU) == TYPE_KANZU;
    }

    /**
     * 是否是明杠
     */
    public boolean isMinkan() {
        return this.type == TYPE_MINKAN;
    }

    /**
     * 是否是暗杠
     */
    public boolean isAnnkan() {
        return this.type == TYPE_ANNKAN;
    }

    // ------------------------------------------------------
    // 值获取
    // ------------------------------------------------------

    /**
     * 获取类型
     */
    public int getType() {
        return type;
    }

    /**
     * 获取面子的第一枚牌
     */
    public IHai getMenzuFirst() {
        return this.menzuFirst;
    }

    /**
     * 获取形状
     */
    public int getShape() {
        return shape;
    }

    // ------------------------------------------------------
    // 值计算
    // ------------------------------------------------------

    /**
     * 鸣牌的来源玩家
     */
    public RivalEnum fromRival() {
        if (shape == SHAPE_NON) {
            return null;
        }
        return NakiShape.getRival(shape);
    }

}
