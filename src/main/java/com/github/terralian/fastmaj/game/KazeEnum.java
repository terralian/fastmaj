package com.github.terralian.fastmaj.game;

/**
 * 风（场风/自风）枚举
 * <p>
 * 该类对风从东到北编码[0, 3]，同编码可以直接转换
 * 
 * @author terra.lian
 */
public enum KazeEnum {
    /**
     * 东风
     */
    DON("东", 0),
    /**
     * 南风
     */
    NAN("南", 1),
    /**
     * 西风
     */
    SYA("西", 2),
    /**
     * 北风（一般规则不会使用）
     */
    PEI("北", 3);

    /**
     * 风名
     */
    private final String kazeName;

    /**
     * 顺序，[0, 3]
     */
    private final Integer order;

    /**
     * 创建枚举
     *
     * @param kazeName 风名
     * @param order 序号
     */
    private KazeEnum(String kazeName, Integer order) {
        this.kazeName = kazeName;
        this.order = order;
    }

    /**
     * 获取风的名称
     */
    public String getKazeName() {
        return kazeName;
    }

    /**
     * 获取风的序号
     */
    public Integer getOrder() {
        return order;
    }

    /**
     * 根据排序获得风
     * 
     * @param order 排序 [0, 3]
     */
    public static KazeEnum getByOrder(int order) {
        for (KazeEnum kaze : KazeEnum.values())
            if (kaze.getOrder() == order)
                return kaze;
        return null;
    }

    /**
     * 获取下一个风，按东南西北顺序
     * 
     * @param kaze 风
     */
    public static KazeEnum next(KazeEnum kaze) {
        int order = kaze.getOrder() + 1;
        if (order > 3)
            order -= 3;
        return getByOrder(order);
    }

    /**
     * 获取自己的自风
     * <p>
     * 根据自身坐席和当前庄家的坐席计算场风，该方法默认牌局开始时的起始东风坐席为0
     * 
     * @param position 自身的坐席
     * @param oya 当前庄家的坐席
     */
    public static KazeEnum jiKaze(int position, int oya) {
        // 本场自风
        // 南（-3） 西（-2） 北（-1） 东（0） 南（1） 西（2） 北（3）
        // <-- 东 南 西 北
        // 计算方式 = 计算目标（如东0） - 当前亲（如南1）
        // = -1 if < 0 then + 4
        // = 3
        int kazeOffset = position - oya;
        if (kazeOffset < 0) {
            kazeOffset += 4;
        }
        return getByOrder(kazeOffset);
    }

    /**
     * 根据自风及庄家获取自风对应的玩家
     * 
     * @param jikazeOrder 自风的序号
     * @param oya 庄家
     */
    public static int getKazePlayer(int jikazeOrder, int oya) {
        int kazeOffset = jikazeOrder + oya;
        if (kazeOffset >= 4) {
            kazeOffset -= 4;
        }
        return kazeOffset;
    }

    @Override
    public String toString() {
        return kazeName;
    }
}
