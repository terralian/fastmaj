package com.github.terralian.fastmaj.tehai;

import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.player.RivalEnum;

/**
 * 鸣牌形状
 * <p>
 * 鸣牌形状通过bitmap进行位存储，一般场景并不需要关注鸣牌的形状，则通过bitmap可节省存储空间
 * <ul>
 * <li>鸣牌依照对象有三种，[上家，下家，对家]
 * <li>鸣牌依照搭子有三种，[最小，中间，最大] 如搭子79，鸣8，为鸣中间那枚
 * </ul>
 * 将这6种按位进行编码，每种占2位。
 * 
 * @author terra.lian 
 */
public abstract class NakiShape {

    /**
     * 玩家的坐席映射
     * <p>
     * 该数组的索引为 被鸣牌玩家编码，其值为 自身玩家的坐席 到目标坐席的差
     * <p>
     * 即 [ 自身，下家，上家，对家 ]，除自身外的下标，与bitmap的玩家编码一一对应
     */
    private final static Integer[] PLAYER_IDNEX_MAP = new Integer[] {0, -1, 1, 2};

    /**
     * 上家
     */
    public final static int PLAYER_TOP = 0b01;
    /**
     * 下家
     */
    public final static int PLAYER_BOTTOM = 0b10;
    /**
     * 对家
     */
    public final static int PLAYER_OPPO = 0b11;

    /**
     * 鸣的牌为最小的那枚，若碰杠的情况，规定为最小
     */
    public final static int NAKI_MIN = 0b0100;
    /**
     * 鸣的牌为中间的那枚
     */
    public final static int NAKI_MIDDLE = 0b1000;
    /**
     * 鸣的牌为最大的那枚
     */
    public final static int NAKI_MAX = 0b1100;
    
    /**
     * 计算鸣牌大小（仅吃操作的情况下成立）
     * <p>
     * 当被鸣的牌是最小的牌，返回{@link #NAKI_MIN}; 为中间的牌，返回{@link #NAKI_MIDDLE};
     * 否则返回{@link #NAKI_MAX}
     * 
     * @param chiHai 吃的牌
     * @param tehai1 搭子1
     * @param tehai2 搭子2
     */
    public static Integer calcNakiSize(IHai chiHai, IHai tehai1, IHai tehai2) {
        int compareValue = 0;
        compareValue += Encode34.isBigger(chiHai, tehai1) ? 1 : -1;
        compareValue += Encode34.isBigger(chiHai, tehai2) ? 1 : -1;
        return compareValue < 0 ? NAKI_MIN : compareValue == 0 ? NAKI_MIDDLE : NAKI_MAX;
    }

    /**
     * 将玩家和鸣牌类型编码为整形存储
     * 
     * @param fromPlayer 来源玩家，[{@link #PLAYER_TOP}, {@link #PLAYER_BOTTOM},
     *        {@link #PLAYER_OPPO}]
     * @param nakiSize 鸣牌大小，[{@link #NAKI_MIN}, {@link #NAKI_MIDDLE},
     *        {@link #NAKI_MAX}]
     */
    public static Integer encode(int fromPlayer, int nakiSize) {
        return fromPlayer | nakiSize;
    }

    /**
     * 根据玩家坐席和来源玩家的坐席，及鸣牌类型编码为整形存储
     * 
     * @param selfIndex 玩家坐席 [0, 3]
     * @param fromIndex 鸣牌来源玩家坐席 [0, 3]
     * @param nakiSize 鸣牌大小，[{@link #NAKI_MIN}, {@link #NAKI_MIDDLE}, {@link #NAKI_MAX}]
     */
    public static Integer encode(Integer selfIndex, Integer fromIndex, byte nakiSize) {
        int offset = selfIndex - fromIndex;

        // 上家
        if(offset == -3 || offset == 1) 
            return encode(PLAYER_TOP, nakiSize);
        // 对家
        else if (offset == -2 || offset == 2)
            return encode(PLAYER_OPPO, nakiSize);
        // 下家
        else if (offset == -1 || offset == 3)
            return encode(PLAYER_BOTTOM, nakiSize);

        throw new IllegalStateException("player index is error: " + selfIndex + ", " + fromIndex);
    }

    /**
     * 根据来源玩家及鸣牌类型编码为整形存储
     * 
     * @param fromPlayer 来源玩家枚举
     * @param nakiSize 鸣牌大小，[{@link #NAKI_MIN}, {@link #NAKI_MIDDLE}, {@link #NAKI_MAX}]
     */
    public static Integer encode(RivalEnum fromPlayer, int nakiSize) {
        if (fromPlayer == RivalEnum.TOP)
            return encode(PLAYER_TOP, nakiSize);
        else if (fromPlayer == RivalEnum.OPPO)
            return encode(PLAYER_OPPO, nakiSize);
        return encode(PLAYER_BOTTOM, nakiSize);
    }

    /**
     * 获取被鸣牌方玩家的坐席
     * <p>
     * 通过自身的坐席来换算被鸣牌玩家的坐席
     * 
     * @param nakiShape 鸣牌的形状编码
     * @param selfIndex 自己的坐席 [0, 3]
     * @return 被鸣牌玩家的坐席 [0, 3]
     */
    public static Integer getPlayerIndex(int nakiShape, Integer selfIndex) {
        int index = selfIndex + PLAYER_IDNEX_MAP[nakiShape & 0b11];
        if (index < 0)
            index += 3;
        else if (index > 3)
            index -= 3;
        return index;
    }

    /**
     * 获取对手的枚举
     * 
     * @param nakiShape 鸣牌形状
     */
    public static RivalEnum getRival(int nakiShape) {
        int playerValue = nakiShape & 0b11;
        if (playerValue == PLAYER_TOP) {
            return RivalEnum.TOP;
        } else if (playerValue == PLAYER_OPPO) {
            return RivalEnum.OPPO;
        } else if (playerValue == PLAYER_BOTTOM) {
            return RivalEnum.BOTTOM;
        }
        return null;
    }

    /**
     * 返回鸣牌形状编码中，鸣牌的那枚的索引
     * <p>
     * 该方法返回的值，可以与副露第一枚集合组合，查找被鸣的那枚牌
     * 
     * @param nakiShape 鸣牌的形状编码
     * @return 若为最小返回0，若为最大返回2 [0, 2]
     */
    public static Integer getNakiIndex(int nakiShape) {
        return (nakiShape >> 2 & 0b11) - 1;
    }
}
