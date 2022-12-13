package com.github.terralian.fastmaj.river;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.NakiEnum;

/**
 * 牌河的默认实现
 * <p>
 * 一个牌河的最小单位是{@link IKiriHai} 表示一枚弃牌，但是其上有更多的信息：
 * <ul>
 * <li>吃碰杠，及对应的玩家
 * <li>立直
 * <li>牌在牌河中的位置
 * </ul>
 * 
 * @author terra.lian 
 */
public class HaiRiver implements IHaiRiver {
    /**
     * 牌河的实际值
     */
    private List<IKiriHai> value;
    /**
     * 34编码法
     */
    private int[] value34;

    /**
     * 哪个玩家的牌河
     */
    private int playerIndex;
    /**
     * 是否被鸣牌过
     */
    private boolean hasNaki;
    /**
     * 是否立直
     */
    private boolean reach;
    /**
     * 是否两立直
     */
    private boolean doubleReach;

    /**
     * 是否同巡
     */
    private boolean sameJun;
    /**
     * 第一巡是否同巡
     */
    private boolean sameFirstJun;

    /**
     * 构建一个牌河
     * 
     * @param playerIndex 玩家的坐席
     */
    public HaiRiver(int playerIndex) {
        this.playerIndex = playerIndex;
        value = new ArrayList<>();
        value34 = Encode34.newArray();
        hasNaki = false;
        reach = false;
        doubleReach = false;
        sameJun = true;
        sameFirstJun = true;
    }

    /**
     * 返回当前牌河的数量，被鸣的牌也会包含在统计内
     */
    @Override
    public int size() {
        return value.size();
    }

    /**
     * 返回牌河是否为空，当牌河为空时返回<tt>true</tt>
     * 
     * @return 空返回<tt>true</tt>，否则返回<tt>false</tt>
     */
    @Override
    public boolean isEmpty() {
        return value.isEmpty();
    }

    /**
     * 丢弃某张牌
     * 
     * @param hai 牌
     */
    @Override
    public void kiri(IHai hai) {
        IKiriHai kiriHai = new KiriHai(hai, value.size());
        value.add(kiriHai);
        value34[hai.getValue()]++;
    }

    /**
     * 获取某张牌
     * 
     * @param index 牌河索引
     */
    @Override
    public IKiriHai get(int index) {
        return value.size() > index ? value.get(index) : null;
    }

    @Override
    public IKiriHai getLast() {
        return isEmpty() ? null : get(value.size() - 1);
    }

    /**
     * 立直宣言时丢弃的牌
     * <p>
     * 牌会以横向表示
     * 
     * @param hai 牌
     */
    @Override
    public void reach(IHai hai, boolean isDoubleReach) {
        IKiriHai kiriHai = new KiriHai(hai, value.size());
        kiriHai.setReachDisplay(true);
        value.add(kiriHai);
        value34[hai.getValue()]++;
        this.reach = true;
        this.doubleReach = isDoubleReach;
    }

    /**
     * 鸣牌河最末尾的那张牌
     * <p>
     * 内部会对该牌进行鸣牌标识，但是不会将牌从牌河中移除。展示时可以通过{@link IKiriHai}的属性进行判断
     * 
     * @param player 鸣牌玩家的坐席
     * @param nakiType 鸣牌类型
     */
    @Override
    public IHai naki(int player, NakiEnum nakiType) {
        IKiriHai lastHai = value.get(value.size() - 1);
        lastHai.setNaki(playerIndex, nakiType);
        hasNaki = true;
        return lastHai.getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSameJun(boolean sameJun) {
        this.sameJun = sameJun;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSameFirstJun(boolean sameFirstJun) {
        this.sameFirstJun = sameFirstJun;
    }

    /**
     * 清空牌河，移除牌河内的元素
     */
    @Override
    public void clear() {
        value.clear();
        Arrays.fill(value34, 0);
        hasNaki = false;
        reach = false;
        doubleReach = false;
        sameJun = true;
        sameFirstJun = true;
    }

    /**
     * 牌河是否有被鸣牌
     */
    @Override
    public boolean hasNaki() {
        return hasNaki;
    }

    /**
     * 是否弃过某张牌
     * 
     * @param hai 手牌
     */
    @Override
    public boolean hasKiri(IHai hai) {
        return value34[hai.getValue()] > 0;
    }

    /**
     * 玩家的坐席
     */
    public int getPlayerIndex() {
        return playerIndex;
    }

    @Override
    public boolean isReach() {
        return reach;
    }

    @Override
    public boolean isDoubleReach() {
        return doubleReach;
    }

    @Override
    public boolean isSameJun() {
        return sameJun;
    }

    @Override
    public boolean isSameFirstJun() {
        return sameFirstJun;
    }

    /**
     * 打印
     */
    @Override
    public String toString() {
        List<String> hsList = new ArrayList<>();
        for (IKiriHai hai : value) {
            String haiMessage = MessageFormat.format("{0}{1}{2}", //
                    hai.toString(), //
                    hai.isReachDisplay() ? "[立直]" : "", //
                    hai.isNaki() ? "[鸣牌]" : ""
            );
            hsList.add(haiMessage);
        }
        return "[" + String.join(",", hsList) + "]";
    }
}
