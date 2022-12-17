package com.github.terralian.fastmaj.yama;

import java.util.ArrayList;
import java.util.List;

import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;

/**
 * 宝牌帮助类
 * 
 * @author terra.lian 
 */
public abstract class DoraHelper {

    /**
     * 从宝牌表示牌获取实际的宝牌
     * 
     * @param doraDisplay 宝牌表示牌
     */
    public static IHai getDora(IHai doraDisplay) {
        return HaiPool.nextHai(doraDisplay);
    }

    /**
     * 从宝牌表示牌获取实际的宝牌
     * 
     * @param doraDisplays 宝牌表示牌
     */
    public static List<IHai> getDora(List<IHai> doraDisplays) {
        List<IHai> doras = new ArrayList<>();
        for (IHai hai : doraDisplays) {
            doras.add(HaiPool.nextHai(hai));
        }
        return doras;
    }

    /**
     * 统计手牌的宝牌所有数量
     * 
     * @param tehai 手牌
     * @param doraHais 宝牌
     */
    public static int countAllDoraSize(ITehai tehai, List<IHai> doraHais) {
        int doraSize = 0;
        // 宝牌相同
        List<IHai> values = tehai.getAll();
        for (IHai hai : values) {
            for (IHai doraHai : doraHais) {
                if (hai.valueEquals(doraHai)) {
                    doraSize++;
                }
            }
            // 红宝牌
            if (hai.isRedDora()) {
                doraSize++;
            }
        }
        // 拔北数量
        doraSize += tehai.countKita();

        return doraSize;
    }

    /**
     * 根据宝牌指示牌统计手牌的宝牌数量
     * <p>
     * 若为三麻，则拔北数量也会增加宝牌数量
     * 
     * @param tehai 手牌
     * @param doraDisplays 宝牌表示牌
     */
    public static int countAllDoraSizeByDisplays(ITehai tehai, List<IHai> doraDisplays) {
        List<IHai> doraHais = getDora(doraDisplays);
        return countAllDoraSize(tehai, doraHais);
    }

    /**
     * 仅统计宝牌的数量
     * 
     * @param tehai 手牌
     * @param doraHais 宝牌
     */
    public static int countDoraSize(ITehai tehai, List<IHai> doraHais) {
        int doraSize = 0;
        // 宝牌相同
        List<IHai> values = tehai.getAll();
        for (IHai hai : values) {
            for (IHai doraHai : doraHais) {
                if (hai.valueEquals(doraHai)) {
                    doraSize++;
                }
            }
        }
        return doraSize;
    }

    /**
     * 统计红宝牌的数量
     * 
     * @param tehai 手牌
     */
    public static int countRedDoraSize(ITehai tehai) {
        int doraSize = 0;
        List<IHai> values = tehai.getAll();
        for (IHai hai : values) {
            if (hai.isRedDora()) {
                doraSize++;
            }
        }
        return doraSize;
    }
}
