package com.github.terralian.fastmaj.river;

import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.NakiEnum;

/**
 * 一枚牌河的牌{@link IKiriHai}的默认实现
 * 
 * @author terra.lian 
 */
public class KiriHai implements IKiriHai {
    /**
     * 牌的实际值
     */
    private IHai value;
    /**
     * 该牌在牌河的枚数
     */
    private int kiriIndex;
    /**
     * 使用bitmap存储的弃牌信息
     * <p/>
     * <ul>
     * <li>1 ：立直标识
     * <li>2-3 : 吃碰明杠
     * <li>5-6 : 鸣牌的玩家坐席[0, 3]
     * </ul>
     * 大部分时候应该并不关心这些弃牌的立直鸣牌信息，使用bitmap有助于节省内存
     */
    private byte kiriHeader;

    /**
     * 构造一个弃牌实例
     * 
     * @param value 实际牌
     * @param kiriIndex 弃牌位置
     */
    public KiriHai(IHai value, int kiriIndex) {
        this.value = value;
        this.kiriHeader = 0;
        this.kiriIndex = kiriIndex;
    }

    /**
     * 通过牌构建一个弃牌实例
     * <p/>
     * 该方法为工厂方法
     * 
     * @param value 实际牌
     * @param kiriIndex 弃牌位置
     */
    public static KiriHai from(IHai value, int kiriIndex) {
        return new KiriHai(value, kiriIndex);
    }

    /**
     * 获取牌的实际信息
     */
    @Override
    public IHai getValue() {
        return value;
    }

    /**
     * 弃牌在牌河的位置
     * 
     * @return [0, ~]
     */
    @Override
    public int kiriIndex() {
        return kiriIndex;
    }

    /**
     * 设置鸣牌类型
     * 
     * @param playerIndex 玩家枚举
     * @param nakiType 鸣牌类型
     */
    @Override
    public void setNaki(int playerIndex, NakiEnum nakiType) {
        this.resetNaki();
        this.kiriHeader = (byte) (this.kiriHeader | (playerIndex << 4) | (nakiType.getIndex() + 1 << 1));
    }

    /**
     * 是否被鸣牌
     * 
     * @see #getNakiType()
     * @see #getNakiPlayer()
     */
    @Override
    public boolean isNaki() {
        return (0b110 & kiriHeader) > 0;
    }

    /**
     * 返回被鸣牌的行为，若未被鸣牌，返回null
     * 
     * @return null | NakiEnum
     * @see #isNaki()
     */
    @Override
    public NakiEnum getNakiType() {
        int nakiIndex = (0b110 & kiriHeader) >> 1;
        return nakiIndex == 0 ? null : NakiEnum.getByIndex(nakiIndex - 1);
    }

    /**
     * 返回鸣该牌的玩家坐席，若未被鸣牌，返回null
     * 
     * @return null | Integer([0, 3])
     * @see #isNaki()
     */
    @Override
    public Integer getNakiPlayer() {
        int playerIndex = 0b11000 & kiriHeader >> 3;
        return playerIndex == 0 ? null : playerIndex - 1;
    }

    /**
     * 清空鸣牌相关字段
     */
    @Override
    public void resetNaki() {
        this.kiriHeader = (byte) (this.kiriHeader | 0b000001);
    }

    /**
     * 设置是否立直标识
     * 
     * @param isReach 是否立直
     */
    @Override
    public void setReachDisplay(boolean isReach) {
        if (isReach)
            this.kiriHeader = (byte) (this.kiriHeader | 0b1);
        else
            this.kiriHeader = (byte) (this.kiriHeader | 0b111110);
    }

    /**
     * 是否立直标识牌
     * <p/>
     * 立直牌会被横向放置表示
     */
    @Override
    public boolean isReachDisplay() {
        return (kiriHeader & 0b1) == 1;
    }
}
