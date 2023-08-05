package com.github.terralian.fastmaj.player;

/**
 * 对手玩家枚举
 * <p/>
 * 该枚举类基于自身视角，依照坐席，来定义其他玩家的叫法
 *
 * @author terra.lian
 */
public enum RivalEnum {

    /**
     * 上家
     */
    TOP,

    /**
     * 对家
     */
    OPPO,

    /**
     * 下家
     */
    BOTTOM,

    ;

    /**
     * 根据自身坐席和对手坐席计算上下家
     *
     * @param selfPosition 自身坐席 [0,3]
     * @param rivalPosition 对手坐席 [0,3]
     */
    public static RivalEnum calc(int selfPosition, int rivalPosition) {
        int offset = selfPosition - rivalPosition;
        // 上家
        if (offset == -3 || offset == 1)
            return RivalEnum.TOP;
        // 对家
        if (offset == -2 || offset == 2)
            return RivalEnum.OPPO;
        // 下家
        if (offset == -1 || offset == 3)
            return RivalEnum.BOTTOM;

        return null;
    }

    /**
     * 通过自身坐席和对手枚举反算出对手的坐席
     *
     * @param selfPosition 自身坐席
     * @param rival 对手枚举
     */
    public static int calc(int selfPosition, RivalEnum rival) {
        int offset = selfPosition;
        if (rival == TOP)
            offset -= 1;
        else if (rival == OPPO)
            offset += 2;
        else if (rival == BOTTOM)
            offset += 1;

        if (offset < 0) offset += 4;
        else if (offset > 3) offset -= 4;

        return offset;
    }

    /**
     * 对手优先级计算，使用通常规则即：按下家，对家，上家的顺序优先处理。越优先返回的值越小
     *
     * @param selfPosition 自己的坐席
     * @param rivalPosition 对手的坐席
     */
    public static int calcPriority(int selfPosition, int rivalPosition) {
        int offset = selfPosition - rivalPosition;
        // 上家
        if (offset == -3 || offset == 1)
            return 3;
        // 对家
        if (offset == -2 || offset == 2)
            return 2;
        // 下家
        if (offset == -1 || offset == 3)
            return 1;
        return 0;
    }

    /**
     * 以自身的视角对比其他玩家处理自己手牌动作的优先度。使用通常规则即：按下家，对家，上家的顺序优先处理。
     *
     * @param self 自己的坐席
     * @param rivalA 对手A的坐席
     * @param rivalB 对手B的坐席
     */
    public static int compare(int self, int rivalA, int rivalB) {
        int PA = calcPriority(self, rivalA);
        int PB = calcPriority(self, rivalB);
        return PA - PB;
    }
}
