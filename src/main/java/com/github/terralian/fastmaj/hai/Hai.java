package com.github.terralian.fastmaj.hai;

import com.github.terralian.fastmaj.encode.Encode34;

/**
 * {@link IHai}的默认实现，内部使用3种编码格式来表示一张牌
 * 
 * @author terra.lian 
 */
public class Hai implements IHai {

    /**
     * 牌的唯一值，136编码， [0,135]
     */
    private final int id;

    /**
     * 值， 34编码， [0, 33]
     */
    private final int value;

    /**
     * 牌的字面量，记号法编码，[0-9] | [1, 9] | [1-7]
     */
    private final int literal;

    /**
     * 牌类型，记号法编码的类型记号
     */
    private final HaiTypeEnum type;

    /**
     * 是否是红宝牌
     */
    private final boolean isRedDora;

    /**
     * 是否是中张牌
     */
    private final boolean isMiddleHai;

    /**
     * 中文形式的名称
     */
   private static final String[] NAME_ZH =  {
       "一万", "二万", "三万", "四万", "五万", "六万", "七万", "八万", "九万", 
       "一筒", "二筒", "三筒", "四筒", "五筒", "六筒", "七筒", "八筒", "九筒",
       "一索", "二索", "三索", "四索", "五索", "六索", "七索", "八索", "九索",
       "东", "南", "西", "北", "白", "发", "中"
   };

    /**
     * 初始化牌，该牌非宝牌
     * 
     * @param id 唯一值
     * @param value 值
     */
    public Hai(int id, int value) {
        this(id, value, false);
    }

    /**
     * 初始化牌，并判断牌是否宝牌
     * 
     * @param id 唯一值
     * @param value 值
     * @param isRedDora 是否是红宝牌
     */
    public Hai(int id, int value, boolean isRedDora) {
        this.id = id;
        this.value = value;
        this.isRedDora = isRedDora;

        // 计算牌类型，中张信息
        this.literal = Encode34.toMarkValue(value, isRedDora);
        this.type = Encode34.toMarkType(value);
        this.isMiddleHai = Encode34.isMiddleHai(value);
    }

    /**
     * 工厂构造牌方法
     * 
     * @param id 唯一值
     * @param value 值
     * @param isRedDora 是否是红宝牌
     */
    public static Hai from(int id, int value, boolean isRedDora) {
        return new Hai(id, value, isRedDora);
    }

    /**
     * 牌的值，<b>该值使用34编码法。</b>
     * 
     * @return 返回一个整形数字，其范围为[0, 33]
     * @see Encode34
     */
    @Override
    public int getValue() {
        return value;
    }
    
    /**
     * 获取牌的唯一ID，<b>该值使用136编码法。</b>
     * 
     * @return [0, 135]
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * 获取牌类型
     */
    public HaiTypeEnum geHaiType() {
        return type;
    }

    /**
     * 获取牌的字面量，<b>该值使用记号法</b>
     * <p/>
     * 如一万一索一筒返回1，对于东南西北白中发，从1开始编码
     * 
     * @return [0, 9] | [1, 9] | [1, 7]
     */
    @Override
    public int getLiteral() {
        return literal;
    }

    // -----------------------------------------------------
    //                      牌属性判断
    // -----------------------------------------------------

    /**
     * 牌是否为万子牌
     */
    @Override
    public boolean isManzu() {
        return type == HaiTypeEnum.M;
    }

    /**
     * 牌是否为筒子牌
     */
    @Override
    public boolean isPinzu() {
        return type == HaiTypeEnum.P;
    }

    /**
     * 牌是否为索子牌
     */
    @Override
    public boolean isSouzu() {
        return type == HaiTypeEnum.S;
    }

    /**
     * 牌是否为字牌
     */
    @Override
    public boolean isJiHai() {
        return type == HaiTypeEnum.Z;
    }

    /**
     * 是否是风牌
     */
    @Override
    public boolean isKazeHai() {
        return value >= Encode34.TON && value <= Encode34.BEI;
    }

    /**
     * 是否是三元牌
     */
    @Override
    public boolean isSanGenHai() {
        return value >= Encode34.HAKU && value <= Encode34.TYUN;
    }

    /**
     * 是否是幺九牌
     */
    @Override
    public boolean isYaotyuHai() {
        return isJiHai() || !isMiddleHai;
    }


    /**
     * 是否为中张牌
     */
    @Override
    public boolean isMiddleHai() {
        return isMiddleHai;
    }

    /**
     * 是否是红宝牌
     */
    @Override
    public boolean isRedDora() {
        return this.isRedDora;
    }

    // -----------------------------------------------------
    //                      其他
    // -----------------------------------------------------
    
    @Override
    public int hashCode() {
        return value + literal;
    }

    /**
     * 比较方法
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof IHai && equals((IHai) obj);
    }

    /**
     * 进行牌的值比较，若两张牌的值（如3万和3万，5万和红5万）相同，则返回true
     */
    @Override
    public boolean equals(IHai hai) {
        return this.value == hai.getValue() && this.literal == hai.getLiteral();
    }

    /**
     * 通过比较ID判断两者是否为同一张牌，可用于从集合中移除该牌
     */
    @Override
    public boolean idEquals(IHai hai) {
        return this.id == hai.getId();
    }

    /**
     * 通过{@link #getValue}获取的值进行比较
     */
    @Override
    public boolean valueEquals(IHai hai) {
        return this.value == hai.getValue();
    }

    /**
     * 使用中文打印，如1万，3索，东南西北等
     */
    @Override
    public String toString() {
        return isRedDora ? "红" + NAME_ZH[value] : NAME_ZH[value];
    }
}
