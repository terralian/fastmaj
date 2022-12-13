package com.github.terralian.fastmaj.tehai;

import java.util.List;

import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.player.RivalEnum;

/**
 * 表示固定部分手牌的接口
 * <p>
 * 鸣牌和暗杠都会固定手牌，将手牌将鸣牌方和搭子作为条件按特定形状放入自己的右桌角。这里的区域没有找到特定的称呼，
 * 我将其称之为<code>Tehai Lock</code>，即手牌锁。当鸣牌时，手牌会调用{@link ITehaiLock}将鸣牌信息存入管理。
 * <p>
 * 该接口提供对鸣牌及暗杠的操作查询管理，可以便利的获取或设置一副手牌的鸣牌信息，并减少{@link ITehai}的方法数量。
 * 这部分信息对于所有玩家是公开的，则在设置玩家游戏上下文时，其他家的{@link ITehaiLock}可以直接给与其他玩家查看。
 * 
 * @author terra.lian 
 */
public interface ITehaiLock {

    // ------------------------------------------------
    // 值获取
    // ------------------------------------------------

    /**
     * 获取某一个面子
     * <p>
     * 面子的存储顺序为加入顺序
     * 
     * @param index 面子的索引
     */
    Mentsu get(int index);

    /**
     * 获取固定手牌的所有面子
     * <p>
     * 面子的存储顺序为加入顺序
     */
    List<Mentsu> getAll();

    /**
     * 获取手牌中吃固定的部分第一枚牌
     */
    List<IHai> getChiFirst();

    /**
     * 获取手牌中除吃外的第一枚牌
     * <p>
     * 包含碰，明杠，暗杠部分
     */
    List<IHai> getNotChiFirst();

    /**
     * 获取手牌中碰固定的部分第一枚牌
     */
    List<IHai> getPonFirst();

    /**
     * 获取手牌中固定的明杠+暗杠的第一枚牌
     */
    List<IHai> getKantsuFirst();

    /**
     * 获取手牌中杠固定的部分第一枚牌
     */
    List<IHai> getMinkanFirst();

    /**
     * 获取手牌中暗杠固定的部分第一枚牌
     */
    List<IHai> getAnnkanFirst();

    /**
     * 获取杠子的数量
     */
    int getKantsuSize();

    // ------------------------------------------------
    // 行为动作
    // ------------------------------------------------

    /**
     * 副露[吃]操作
     * 
     * @param chiHai 吃的牌
     * @param tehai1 搭子牌1
     * @param tehai2 搭子牌2
     */
    Mentsu chi(IHai chiHai, IHai tehai1, IHai tehai2);

    /**
     * 副露[碰]操作
     * 
     * @param ponHai 碰的牌
     * @param fromPlayer 来源玩家
     */
    Mentsu pon(IHai ponHai, RivalEnum fromPlayer);

    /**
     * 副露[明杠]操作
     * 
     * @param kanHai 杠的牌
     * @param fromPlayer 来源玩家
     */
    Mentsu minkan(IHai kanHai, RivalEnum fromPlayer);

    /**
     * 加杠操作
     * 
     * @param hai 加杠的牌
     */
    Mentsu kakan(IHai hai);

    /**
     * 暗杠操作
     * 
     * @param hai 暗杠的牌
     */
    Mentsu annkan(IHai hai);

    // ------------------------------------------------
    // 状态判断
    // ------------------------------------------------

    /**
     * 是否为空，即不含鸣牌或者暗杠
     */
    boolean isEmpty();

    /**
     * 是否鸣牌
     */
    boolean isNaki();

    /**
     * 是否暗杠
     */
    boolean isAnnkan();

    // ------------------------------------------------
    // 数量统计
    // ------------------------------------------------

    /**
     * 返回固定的手牌面子数
     * <p>
     * 鸣牌或暗杠通过面子进行，一个面子可能有3枚或4枚（暗杠/明杠）手牌，这里将面子作为{@link ITehaiLock}的数量单位。
     * 
     * @return 返回对应固定的面子数
     */
    int size();

    /**
     * 返回固定手牌中明杠和暗杠的面子总数
     * <p>
     * 当明杠和暗杠都为0时，返回0，其中一个不为0则返回对应值
     */
    int kantsuSize();

    // ------------------------------------------------
    // 元素操作
    // ------------------------------------------------

    /**
     * 移除某个面子
     * 
     * @param index 索引
     */
    Mentsu remove(int index);

    /**
     * 清空固定区
     */
    void clear();

    /**
     * 深克隆
     */
    ITehaiLock deepClone();
}
