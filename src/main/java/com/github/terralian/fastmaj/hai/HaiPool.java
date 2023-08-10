package com.github.terralian.fastmaj.hai;

import com.github.terralian.fastmaj.encode.Encode136;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.encode.EncodeMark;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 牌池
 * <p/>
 * 基于使用便利静态类设计，内部缓存所有日式麻将使用的136枚牌的实例
 * 
 * @author terra.lian 
 */
public abstract class HaiPool {

    /**
     * 总牌数
     */
    private static final Integer HAI_LENGTH = 136;

    /**
     * 红宝牌所在的位置，默认为同类型的第四枚，下标从0开始
     */
    private static final Integer DEFAULT_RED_INDEX = 0;

    /**
     * 136枚普通麻将牌实例，并进行初始化操作
     */
    private static final IHai[] HAIS = new IHai[HAI_LENGTH];
    /**
     * 3种数牌的红5宝牌
     * <p/>
     * 这里存储了红宝牌在同类型牌的所有下标情况，如在4枚5中的第一枚，或者第4枚
     */
    private static final IHai[][] RED_HAIS = new IHai[3][4];
    /**
     * 红宝牌基于id存储的Map，用于快速查询
     */
    private static final Map<Integer, IHai> RED_HAI_MAP = new HashMap<>();
    /**
     * 存储34编码值的红五宝牌的值
     */
    private static final int[] RED_ENCODE34_ARRAY = new int[] {Encode34.MAN_5, Encode34.PIN_5, Encode34.SO_5};

    static {
        generate();
        generateRed();
    }

    /**
     * 获取牌的总数，为136枚
     */
    public static int size() {
        return HAI_LENGTH;
    }

    /**
     * 根据牌的唯一值获取牌的实例
     * <p/>
     * 基于该方法获取的牌都是唯一的一枚，并且都是通常牌，不含红宝牌
     * <p/>
     * <b>136编码格式</b>
     * 
     * @param id 136编码的牌唯一值，[0,135]
     * @return 返回获取到的牌
     * @throws IndexOutOfBoundsException 若值超过编码编码范围，抛出该异常
     * @see IHai
     */
    public static IHai getById(int id) {
        checkOutOfBounds(id, 0, HAIS.length);
        return HAIS[id];
    }

    /**
     * 根据牌的唯一值获取牌的实例
     * <p/>
     * 基于该方法获取的牌都是唯一的一枚，根据是否含红宝牌来获取对应的牌，其红宝牌位置为{@link #DEFAULT_RED_INDEX}
     * <p/>
     * <b>136编码格式</b>
     * 
     * @param id 主键
     * @param useRed 是否含红宝牌
     */
    public static IHai getById(int id, boolean useRed) {
        return getById(id, useRed, DEFAULT_RED_INDEX);
    }

    /**
     * 根据牌的唯一值获取牌的实例
     * <p/>
     * 基于该方法获取的牌都是唯一的一枚，根据是否含红宝牌来获取对应的牌
     * <p/>
     * <b>136编码格式</b>
     * 
     * @param id 主键
     * @param useRed 是否含红宝牌
     * @param redIndex 红宝牌在同类型牌的第几枚 [0, 3]
     */
    public static IHai getById(int id, boolean useRed, int redIndex) {
        checkOutOfBounds(id, 0, HAIS.length);
        checkOutOfBounds(redIndex, 0, 3);
        IHai hai = HAIS[id];
        // 使用红宝牌，且牌值为5，且非字牌，且牌的id为红宝牌
        if (useRed && hai.getLiteral() == 5 && !hai.isJiHai() //
                && (hai.getValue() * 4 + redIndex == id)) {
            return RED_HAI_MAP.get(id);
        }
        return hai;
    }

    /**
     * 根据牌的值获取牌的实例
     * <p/>
     * 基于该方法获取到的牌默认都是同一枚
     * <p/>
     * <b>34编码格式</b>
     * 
     * @param value 34编码的牌值，[0, 33]
     * @return 返回获取到的牌
     * @throws IndexOutOfBoundsException 若值超过编码编码范围，抛出该异常
     * @see Encode34
     * @see IHai
     */
    public static IHai getByValue(int value) {
        checkOutOfBounds(value, Encode34.min(), Encode34.max());
        return getById(value * 4);
    }

    /**
     * 根据记号法获取牌的实例
     * <p/>
     * 基于该方法获取到的牌默认都是同一枚
     * <p/>
     * <b>记号法格式</b>
     * 
     * @param literal 记号法的值（字面量） [0, 9] | [1, 7]
     * @param type 记号法的记号类型
     * @throws IndexOutOfBoundsException 若值超过编码编码范围，抛出该异常
     * @see HaiTypeEnum
     */
    public static IHai getByMark(int literal, HaiTypeEnum type) {
        checkOutOfBounds(literal, 0, EncodeMark.maxValue(type));
        if (EncodeMark.isRedHai(literal)) {
            return RED_HAI_MAP.get(EncodeMark.toEncode34(literal, type) * 4 + DEFAULT_RED_INDEX);
        }
        return getById(EncodeMark.toEncode34(literal, type) * 4);
    }

    /**
     * 根据字面量获取万子牌
     * 
     * @param literal 字面量 [0, 9]
     * @throws IndexOutOfBoundsException 若值超过编码编码范围，抛出该异常
     */
    public static IHai m(int literal) {
        return getByMark(literal, HaiTypeEnum.M);
    }

    /**
     * 根据字面量获取筒牌
     * 
     * @param literal 字面量 [0, 9]
     * @throws IndexOutOfBoundsException 若值超过编码编码范围，抛出该异常
     */
    public static IHai p(int literal) {
        return getByMark(literal, HaiTypeEnum.P);
    }

    /**
     * 根据字面量获取索子牌
     * 
     * @param literal 字面量 [0, 9]
     * @throws IndexOutOfBoundsException 若值超过编码编码范围，抛出该异常
     */
    public static IHai s(int literal) {
        return getByMark(literal, HaiTypeEnum.S);
    }

    /**
     * 根据字面量获取字牌
     * 
     * @param literal 字面量 [1, 7]
     * @throws IndexOutOfBoundsException 若值超过编码编码范围，抛出该异常
     */
    public static IHai z(int literal) {
        return getByMark(literal, HaiTypeEnum.Z);
    }
    
    /**
     * 获取第一枚牌（1m）
     * <p/>
     * 常用于填充某个测试用例
     */
    public static IHai firstHai() {
        return HAIS[0];
    }

    /**
     * 将136枚牌转为数组，对其修改不会影响该牌池内部
     * <p/>
     * 该方法获取到的为无红宝牌的牌数组
     */
    public static IHai[] toArray() {
        return toArray(false, DEFAULT_RED_INDEX);
    }

    /**
     * 将136枚牌转为数组，对其修改不会影响该牌池内部
     * <p/>
     * 该方法根据参数获取是否含红宝牌的牌数组，若含红宝牌，则红宝牌在同类型牌的第{@link #DEFAULT_RED_INDEX}枚
     * 
     * @param hasRed 是否含红宝牌
     */
    public static IHai[] toArray(boolean hasRed) {
        return toArray(hasRed, DEFAULT_RED_INDEX);
    }

    /**
     * 将136枚牌转为数组，对其修改不会影响该牌池内部
     * <p/>
     * 该方法根据参数获取是否含红宝牌的牌数组，若含红宝牌，根据指定的红宝牌下标替换普通牌
     * 
     * @param hasRed 是否含红宝牌
     * @param redIndex 红宝牌在同类型牌的第几枚 [0, 3]
     */
    public static IHai[] toArray(boolean hasRed, int redIndex) {
        checkOutOfBounds(redIndex, 0, 3);
        IHai[] newArray = Arrays.copyOf(HAIS, HAIS.length);
        // 含红宝牌的数组，将普通牌替换为红宝牌
        if (hasRed) {
            for (int i = 0; i < RED_ENCODE34_ARRAY.length; i++)
                newArray[i] = RED_HAIS[i][redIndex];
        }
        return newArray;
    }

    /**
     * 获取种类意义上的下一枚牌
     * <p/>
     * <li>除字牌外，表示当前种类的下一枚牌，会循环（如 5 -> 6, 9 -> 1）
     * <li>对于字牌，需要区分四喜牌和三元牌作为种类，内部循环
     * 
     * @param hai 牌
     * @see {@link Encode34#nextHai(int)}
     */
    public static IHai nextHai(IHai hai) {
        int nextEncode34 = Encode34.nextHai(hai.getValue());
        return getByValue(nextEncode34);
    }

    /**
     * 随机获取一枚牌
     */
    public static IHai random() {
        return getById(ThreadLocalRandom.current().nextInt(Encode136.length()));
    }

    // -------------------------------------------------------
    // 私有方法
    // -------------------------------------------------------

    /**
     * 初始化生成牌实例
     * <p/>
     * 其牌种类共{@link Encode34#max()}（34）种，所有牌都是通常牌（非红宝牌）.
     * 每一枚牌的id为按{@link Encode34}顺序编码
     */
    private static void generate() {
        int encode34Max = Encode34.max();
        int id = 0;
        for (int i = 0; i <= encode34Max; i++) {
            int haiSize = 0;
            while (haiSize < 4) {
                IHai hai = new Hai(id, i, false);
                HAIS[id] = hai;
                id++;
                haiSize++;
            }
        }
    }

    /**
     * 生成所有类型所有场景的红5宝牌
     */
    private static void generateRed() {
        for (int i = 0; i < RED_ENCODE34_ARRAY.length; i++) {
            int haiValue = RED_ENCODE34_ARRAY[i];
            for (int j = 0; j < 4; j++) {
                int haiId = haiValue * 4 + j;
                IHai redHai = new Hai(haiId, haiValue, true);
                RED_HAIS[i][j] = redHai;
                RED_HAI_MAP.put(haiId, redHai);
            }
        }
    }

    /**
     * 检查是否越界
     * 
     * @param value 参数
     * @param min 最小值
     * @param max 最大值
     */
    private static void checkOutOfBounds(int value, int min, int max) {
        if (value < min || value > max) {
            throw new IndexOutOfBoundsException("参数越界：" + value);
        }
    }
}
