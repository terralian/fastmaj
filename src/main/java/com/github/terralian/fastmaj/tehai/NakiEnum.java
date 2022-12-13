package com.github.terralian.fastmaj.tehai;

/**
 * 鸣牌类型枚举
 * 
 * @author terra.lian 
 */
public enum NakiEnum {
    /**
     * 吃
     */
    CHI(0, "吃"),

    /**
     * 碰
     */
    PON(1, "碰"),

    /**
     * 杠
     */
    KAN(2, "杠");

    /**
     * 通过数组存储索引信息
     */
    private static final NakiEnum[] VALUE_ARRAY = new NakiEnum[] {CHI, PON, KAN};

    /**
     * 索引，用于在手牌中获取鸣牌的集合
     */
    private Integer index;
    /**
     * 鸣牌的操作名称
     */
    private String name;

    /**
     * 初始化枚举
     * 
     * @param index 索引
     * @param name 名称
     */
    private NakiEnum(Integer index, String name) {
        this.index = index;
        this.name = name;
    }

    /**
     * 鸣牌的索引
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * 获取鸣牌的操作名称
     */
    public String getName() {
        return name;
    }

    /**
     * 根据鸣牌类型的索引获取鸣牌枚举
     * 
     * @param index 鸣牌索引
     */
    public static NakiEnum getByIndex(int index) {
        return VALUE_ARRAY[index];
    }
}
