package com.github.terralian.fastmaj.hai;

import java.util.List;

import com.github.terralian.fastmaj.encode.Encode34;

/**
 * 表示一张牌
 * <p/>
 * 通过封装，牌会更加易用。接口提供了一组牌的常用属性判断。 一般创建牌实例，应当使用工厂类进行享元，因为牌的数量是固定的136枚，仅需要实例化一次。
 * <p/>
 * 牌的编码格式有3种，<b>一般进行判断时是以34编码法</b>：
 * <ul>
 * <li>34编码法. 总共有34种牌，按万筒索字的顺序对0-33编码，不区分红宝牌，如一万=0，一索=18.
 * <li>136编码法. 牌总计136枚，按万筒索字的顺序对0-135进行编码，每个牌都含一个唯一值.
 * <li>记号法. 使用天凤牌理相同的记号法，数牌1-9，字牌1-7，万(m)，筒(p)，索(s)，字(z)，1万=1m，东=1z，0表示红宝牌.
 * </ul>
 * 该接口同时使用到上述三种表示法.
 * <p/>
 * {@link #getValue()}所获取到的值为牌谱在34编码法上的值，会丢失是否红宝牌信息，当仅关心牌的类型及值时，
 * 通过<code>=</code>比较可行，但是更推荐使用{@link #valueEquals(IHai)}方法。
 * 而在关心牌类型及值，又关心是否红宝牌时，可通过{@link #equals(IHai)}进行比较。
 *
 * @author terra.lian
 * @see <a href=
 * "https://ja.wikipedia.org/wiki/%E9%BA%BB%E9%9B%80%E7%89%8C#%E7%89%8C%E3%81%AE%E8%A1%A8%E8%A8%98%E6%B3%95">记号法wiki</a>
 * @see <a href="https://tenhou.net/2/" >天凤牌理</a>
 * @see Hai
 */
public interface IHai {

    // ---------------------------------------------
    // 牌的属性获取
    // ---------------------------------------------

    /**
     * 牌的值，<b>该值使用34编码法。</b>
     * <p/>
     * 一般我们关心一张牌，如三索，关心的是牌的类型及其值（记号法），但是由于记号法不易计算，所以34编码是最佳的选择。
     * <p/>
     * 编码法：万[0, 8] 筒[9, 17] 索[18, 26] 字[27 - 33]，其中字的顺序为 东南西北白中发
     *
     * @return 返回一个整形数字，其范围为[0, 33]
     * @see Encode34
     */
    int getValue();

    /**
     * 获取牌的唯一ID，<b>该值使用136编码法。</b>
     * <p/>
     * 136编码法. 按万筒索字（东南西北白中发）的顺序，从0开始对每一枚牌进行编码，每种牌占4个单位，如[0, 3]都是1万
     * <p/>
     * 对于红5，由工厂类决定是哪一枚，该接口的实现内部一般会维护一个字段用于记录是否是红宝牌，通过{@link #isRedDora()}判断即可
     *
     * @return [0, 135]
     */
    int getId();

    /**
     * 获取牌的字面量，<b>该值使用记号法</b>
     * <p/>
     * 如一万一索一筒返回1，对于东南西北白中发，从1开始编码
     *
     * @return [0, 9] | [1, 9] | [1, 7]
     */
    int getLiteral();

    /**
     * 获取牌类型的枚举，<b>该值使用记号法的记号部分</b>
     * <p/>
     * 对于字牌不再进行细分，若需要判断是四风牌还是三元牌，可以使用该接口的其他方法
     *
     * @see HaiTypeEnum
     */
    HaiTypeEnum geHaiType();

    // ---------------------------------------------
    // 牌的属性判断
    // ---------------------------------------------

    /**
     * 是否为万子牌
     */
    boolean isManzu();

    /**
     * 是否为筒子牌
     */
    boolean isPinzu();

    /**
     * 是否为索子牌
     */
    boolean isSouzu();

    /**
     * 是否是字牌
     * <p/>
     * 字牌是东南西北白发中
     */
    boolean isJiHai();

    /**
     * 是否是四风牌，东南西北
     */
    boolean isKazeHai();

    /**
     * 是否是三元牌，白发中
     */
    boolean isSanGenHai();

    /**
     * 是否为幺九牌
     * <p/>
     * 幺九牌包含数牌的19，和所有的字牌，幺九牌会影响部分役的判断
     */
    boolean isYaotyuHai();

    /**
     * 是否是中张牌 [2 , 8]
     */
    boolean isMiddleHai();

    /**
     * 判断该牌是否红宝牌
     * <p/>
     * 一般规则会将各种类型的数牌中的一枚5，作为红宝牌，颜色为红色，会对番数产生影响。 也有规则是不使用红宝牌的，由实际工厂创建时判断
     */
    boolean isRedDora();

    // -----------------------------------------------------
    // 其他
    // -----------------------------------------------------

    /**
     * 根据{@link #getValue()} + {@link #getLiteral()} 组合进行比较
     * <p/>
     * 当两枚牌值相同，且是否红宝牌也相同时，则判断这两张牌是相同的
     * <p/>
     * 默认用于{@link #equals(IHai)}比较
     *
     * @param hai 牌
     */
    boolean equals(IHai hai);

    /**
     * 进行牌的唯一值比较，通过{@link #getId()}获取的值进行比较
     * <p/>
     * 仅在两枚牌是同一枚时，返回true，有可能存在3万与3万不相等的情况
     *
     * @param hai 牌
     */
    boolean idEquals(IHai hai);

    /**
     * 进行牌的值比较，通过{@link #getValue}获取的值进行比较
     * <p/>
     * 若两张牌的值（如3万和3万，5万和红5万）相同，则返回true
     * <p/>
     * 注：java在集合的{@link List#contains(Object)}方法也是通过equals进行判断。
     * 若调用{@link Object#equals(Object)}时，由于类型不一致，其实现是Object的默认实现
     * <p/>
     * 若需要根据<b>136编码</b>的唯一值判断是否相等，可以使用{@link #idEquals(IHai)}
     *
     * @param hai 牌
     */
    boolean valueEquals(IHai hai);

    /**
     * 将牌作为字符串输出，按实现中文默认是一万，东，发等
     *
     * @return 返回牌的字符串表示
     */
    String toString();

    /**
     * 输出中文以及ID，按照一万(1)进行打印
     */
    String toIdString();
}
