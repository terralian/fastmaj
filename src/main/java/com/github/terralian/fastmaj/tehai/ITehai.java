package com.github.terralian.fastmaj.tehai;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.player.RivalEnum;

/**
 * 表示一副手牌
 * <p/>
 * 这里的手牌指的是广义上的手牌，包含非副露部分和副露部分及最后的自摸部分。另外为了区分非副露的部分，
 * 这里将非副露部分的手牌称之为<b>手内牌</b>，而副露或者暗杠的牌称之为<b>副露牌</b>，副露牌将会固定在副露区。
 * <p/>
 * 一副手牌，在不考虑杠牌情形时，牌的数量范围为[1, 14]，当为14枚时，表示为摸进一枚牌，但是还未选择打出的状态。
 * 当考虑暗杠明杠情况时，牌的数量范围为[1, 18]，最多包含4副杠子 + 1个对子。
 * <p/>
 * 手牌的基本元素为{@link IHai}，当鸣牌时还需要记录鸣牌的形状及鸣牌类型，用于还原出从哪家鸣的牌，及鸣牌的搭子。
 * 对于拔北场景，一般拔北牌都会做移除处理，仅记录拔北的数量。
 * <p/>
 * 根据实现类的不同，初始化{@link ITehai}的方法不同，接口本身不提供初始化相关的方法，则一般通过构造器进行，如:
 * 
 * <pre>
 * ITehai = new Tehai();
 * </pre>
 * 
 * 表示为初始化一副空手牌，后续再往里增加牌。该接口提供了为了便利的查询方法，及手牌对副露区的操作方法。
 * <p/>
 * 接口本身并没有指定实现类的编码形式，常用的有{@link Encode34}及{@link EncodeMark}。
 * 
 * @author terra.lian 
 * @see Encode34
 * @see EncodeMark
 * @see IHai
 * @see Tehai
 */
public interface ITehai {

    // --------------------------------
    // 值获取
    // --------------------------------

    /**
     * 获取手牌的所有牌，包含手内牌和副露牌，但是不包含拔北牌。
     * <p/>
     * 当手牌未进行鸣牌时，该方法获取的值与{@link ITehai#getHand()}一致。获取到的牌集合的排序为入手顺序，所以是乱序情况。
     * 需要排序的场合可以使用{@link #sort(List)}手动对手牌进行排序。
     * <p/>
     * 对于需要修改牌形状，如打出某枚牌，鸣牌等，直接修改该返回集合不会对内部值产生影响，请使用对应的操作方法。
     * <p/>
     * <b>note:</b>
     * 对于拔北操作来讲，将这类悬赏牌排除出手牌之外是最佳的选择，此时{@link #getAll()}中也将不再出现被拔北的牌。
     * 而拔北的统计可以通过{@link #countKita()}获取
     * 
     * @return 按牌入手的先后顺序，返回牌的集合
     * @see #getHand()
     */
    List<IHai> getAll();

    /**
     * 获取手内牌，可操作弃牌鸣牌的部分。
     * <p/>
     * 若手牌未鸣牌，暗杠等固定部分手牌操作时，该方法与{@link #getAll()}等效。 若手牌进行了鸣牌，
     * 暗杠后，该方法仅获取其中未进行鸣牌暗杠的部分。
     * <p/>
     * 获取到的牌集合的排序为入手顺序，所以是乱序情况。要排序的场合可以使用{@link #sort(List)}手动对手牌进行排序。
     * 对于需要修改牌形状，如打出某枚牌，鸣牌等，直接修改该返回集合不会对内部值产生影响，请使用对应的操作方法。
     * <p/>
     * <b>note:</b> 对于拔北操作来讲，将这类悬赏牌排除出手牌之外是最佳的选择，此时{@link #getAll()}中也将不再出现被拔北的牌。
     * 而拔北的统计可以通过{@link #countKita()}获取
     * 
     * @return 按牌入手的先后顺序，返回牌的集合
     */
    List<IHai> getHand();

    /**
     * 获取手牌固定区
     * <p/>
     * 固定区为鸣牌或者暗杠后将牌固定的桌右角，可通过该区域查询固定信息
     */
    ITehaiLock getLock();

    /**
     * 获取加入手牌的第14枚牌
     * <p/>
     * 当手牌为14枚时，获取当前从牌山加入的牌，若手牌不为14枚时，返回null。
     * 该方法常用于需要知晓最后摸进的是什么牌的场景，如自摸时，需要判断自摸牌是否两面的待牌。
     * 
     * @return IHai | null
     */
    IHai getDrawHai();

    // --------------------------------
    // 手牌操作
    // --------------------------------

    /**
     * 摸牌动作，将牌加入到手中的操作。
     * <p/>
     * 摸牌会发生在从牌山/王牌区摸进一枚手牌或者鸣牌的情况。对于从牌山进行的摸牌，需要手动调用该方法将牌加入手牌。
     * 而对于{@link #chii}等鸣牌方法，会由对应方法自行处理加入手牌行为，此时无需使用该方法加入手牌。
     *
     * @param hai 摸的牌
     */
    void draw(IHai hai);

    /**
     * 弃牌动作，将牌从手牌中打出的动作
     * 
     * @param hai 弃的牌
     * @return 是否手切（true手切，false模切）
     */
    boolean kiri(IHai hai);

    /**
     * 把摸到的最后一枚牌进行弃牌，需要手牌为3n+2的状态
     * 
     * @return 默认为false，模切
     */
    IHai kiriLast();

    /**
     * 副露[吃]操作
     * <p/>
     * 通过指定打搭子牌，完成吃牌的操作，该操作会首先调用{@link #draw(IHai)}将牌加入手中
     * 
     * @param chiHai 吃的牌
     * @param tehai1 搭子牌1
     * @param tehai2 搭子牌2
     */
    void chii(IHai chiHai, IHai tehai1, IHai tehai2);

    /**
     * 副露[碰]操作
     * <p/>
     * 该操作会首先调用{@link #draw(IHai)}将牌加入手中
     * 
     * @param ponHai 碰的牌
     * @param redFirst 当有红宝牌时，优先拿红宝牌做搭子
     * @param fromPlayer 来源玩家
     */
    void pon(IHai ponHai, boolean redFirst, RivalEnum fromPlayer);

    /**
     * 副露[碰]操作
     * <p/>
     * 该操作会首先调用{@link #draw(IHai)}将牌加入手中
     * <p/>
     * 该操作默认不优先使用红宝牌
     * 
     * @param ponHai 碰的牌
     * @param fromPlayer 来源玩家
     */
    default void pon(IHai ponHai, RivalEnum fromPlayer) {
        this.pon(ponHai, false, fromPlayer);
    }

    /**
     * 明杠
     * <p/>
     * 该操作会首先调用{@link #draw(IHai)}将牌加入手中
     * 
     * @param hai 鸣的牌
     * @param fromPlayer 来源玩家
     */
    void minkan(IHai hai, RivalEnum fromPlayer);

    /**
     * 加杠
     * <p/>
     * 该操作会首先调用{@link #draw(IHai)}将牌加入手中
     * 
     * @param hai 加杠的牌
     * @see {@link #canKakan()}
     */
    void kakan(IHai hai);

    /**
     * [非进张操作] 暗杠
     * <p/>
     * 当手上由4枚牌时，通过该操作进行暗杠，暗杠的牌会固定，不可进行后续操作
     * 
     * @param hai 暗杠的牌
     */
    void annkan(IHai hai);

    /**
     * [非进张操作] 拔北（限三麻）
     * <p/>
     * 从手中拔北，将北从手牌里去除，仅作为宝牌记录
     * 
     * @param hai 北
     */
    void kita(IHai hai);

    // --------------------------------
    // 操作校验
    // --------------------------------

    /**
     * 判断是否能够弃某枚牌
     * 
     * @param hai 牌
     */
    boolean canKiri(IHai hai);

    /**
     * 校验某张牌是否可以吃
     * <p/>
     * 校验手牌中是否存在可以吃目标牌的搭子，这个搭子应该是可操作的，该方法也会校验字牌（false）.
     * <p/>
     * 该方法不会校验玩家的坐席，上下家的判断应当由该方法的使用者自行判断.
     * 
     * @param hai 牌
     * @return 若可以吃 true，否则 false.
     */
    boolean canChii(IHai hai);

    /**
     * 校验某张牌是否可以碰
     * <p/>
     * 校验手牌中是否存在可用碰的搭子，在一般规则内碰的优先级要大于吃，调用时需要注意。
     * 
     * @param hai 牌
     */
    boolean canPon(IHai hai);

    /**
     * 校验某张牌是否可以明杠
     * <p/>
     * 校验手牌中是否存在可用杠的搭子，在一般规则内杠的优先级要大于吃，调用时需要注意。
     * 
     * @param hai 牌
     */
    boolean canMinkan(IHai hai);
    
    /**
     * 手牌是否能够加杠
     * <p/>
     * 对手牌进行判断，是否存在可操作加杠的牌
     * 
     * @return 若存在则返回true，若不存在则返回false
     */
    boolean canKakan();

    /**
     * 手牌中的某一枚牌是否能够加杠
     * 
     * @param hai 牌
     */
    boolean canKakan(IHai hai);

    /**
     * 手牌是否能够开暗杠
     * <p/>
     * 对手牌进行判断，是否存在可以开暗杠的牌，即手牌中可操作的牌大于4枚的情况。
     */
    boolean canAnkan();

    /**
     * 手牌中的某一枚牌是否能够暗杠
     * 
     * @param hai 牌
     */
    boolean canAnkan(IHai hai);

    /**
     * 手牌是否能够拔北（限三麻）
     * <p/>
     * 三麻中，校验手牌中是否存在可操作的北，但是本方法不会校验是否在三麻。
     */
    boolean canKita();

    // --------------------------------
    // 状态获取
    // --------------------------------

    /**
     * 手牌是否鸣牌
     */
    boolean isNaki();

    /**
     * 手牌是否暗杠
     */
    boolean isAnnkan();

    /**
     * 获取拔北的数量
     */
    int countKita();

    // --------------------------------
    // 公共方法
    // --------------------------------

    /**
     * 深克隆该手牌
     */
    ITehai deepClone();

    /**
     * 对手牌进行排序
     * <p/>
     * 排序依照id大小进行降序（万，筒，索，字），其中红宝牌的位置将根据其在同类型位置排列，如第4枚
     * 
     * @param hais 所有枚牌
     */
    static void sort(List<IHai> hais) {
        hais.sort(Comparator.comparingInt(IHai::getId));
    }
}
