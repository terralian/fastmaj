package com.github.terralian.fastmaj.test.yama;

import java.util.List;

import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.hai.HaiTypeEnum;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.util.CollectionUtil;
import com.github.terralian.fastmaj.yama.DoraHelper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * {@link DoraHelper}的测试
 * 
 * @author terra.lian 
 */
public class DoraHelperTest {

    /**
     * 测试{@link DoraHelper#getDora(IHai)}方法
     */
    @Test
    public void testGetDora() {
        // 1万 -> 2万
        testGetDora0(1, 2, HaiTypeEnum.M);
        // 4万 -> 5万
        testGetDora0(4, 5, HaiTypeEnum.M);
        // 红5 -> 6万
        testGetDora0(0, 6, HaiTypeEnum.M);
        // 9万 -> 1万
        testGetDora0(9, 1, HaiTypeEnum.M);

        // 1P -> 2P
        testGetDora0(1, 2, HaiTypeEnum.P);
        // 3P -> 4P
        testGetDora0(3, 4, HaiTypeEnum.P);
        // 0P -> 6P
        testGetDora0(0, 6, HaiTypeEnum.P);
        // 9P -> 1P
        testGetDora0(9, 1, HaiTypeEnum.P);

        // 1S -> 2S
        testGetDora0(1, 2, HaiTypeEnum.S);
        // 6S -> 7S
        testGetDora0(6, 7, HaiTypeEnum.S);
        // 0S -> 6S
        testGetDora0(0, 6, HaiTypeEnum.S);
        // 9S -> 1S
        testGetDora0(9, 1, HaiTypeEnum.S);

        // 1z -> 2z ... 4z -> 1z
        testGetDora0(1, 2, HaiTypeEnum.Z);
        testGetDora0(2, 3, HaiTypeEnum.Z);
        testGetDora0(3, 4, HaiTypeEnum.Z);
        testGetDora0(4, 1, HaiTypeEnum.Z);
        // 5z -> 6z ...
        testGetDora0(5, 6, HaiTypeEnum.Z);
        testGetDora0(6, 7, HaiTypeEnum.Z);
        testGetDora0(7, 5, HaiTypeEnum.Z);
    }

    /**
     * 测试{@link DoraHelper#getDora(IHai)}方法
     * 
     * @param displayLiteral 指示牌字面量
     * @param doraLiteral 宝牌字面量
     * @param haiType 牌类型
     */
    private void testGetDora0(int displayLiteral, int doraLiteral, HaiTypeEnum haiType) {
        IHai hai = DoraHelper.getDora(HaiPool.getByMark(displayLiteral, haiType));
        assertTrue(HaiPool.getByMark(doraLiteral, haiType).valueEquals(hai));
    }

    /**
     * 测试{@link DoraHelper#getDora(List)}
     */
    @Test
    public void testGetDoraList() {
        List<IHai> doras = DoraHelper.getDora(CollectionUtil.newArrayList(HaiPool.m(5), HaiPool.m(0)));
        assertEquals(6, doras.get(0).getLiteral());
        assertEquals(6, doras.get(1).getLiteral());
    }

    /**
     * 测试{@link DoraHelper#countDoraSize}
     */
    @Test
    public void testCountDoraSize() {
        // 4麻
        ITehai tehai = EncodeMark.toTehai("1249m1479p7s1235z");
        int doraSize = DoraHelper.countAllDoraSizeByDisplays(tehai, CollectionUtil.newArrayList(HaiPool.p(6)));
        assertEquals(1, doraSize);

        tehai = EncodeMark.toTehai("345789m40p11222s");
        doraSize = DoraHelper.countAllDoraSizeByDisplays(tehai, CollectionUtil.newArrayList(HaiPool.p(6)));
        assertEquals(1, doraSize);

        tehai = EncodeMark.toTehai("78m34789p440677s");
        doraSize = DoraHelper.countAllDoraSizeByDisplays(tehai, CollectionUtil.newArrayList(HaiPool.p(6)));
        assertEquals(2, doraSize);
        //
        tehai = EncodeMark.toTehai("1056m4678p34478s");
        doraSize = DoraHelper.countAllDoraSizeByDisplays(tehai, CollectionUtil.newArrayList(HaiPool.m(9)));
        assertEquals(2, doraSize);
        //
        tehai = EncodeMark.toTehai("067m122334p1167s2z");
        doraSize = DoraHelper.countAllDoraSizeByDisplays(tehai, CollectionUtil.newArrayList(HaiPool.z(EncodeMark.DONG), HaiPool.s(2)));
        assertEquals(2, doraSize);

        // 3麻
        tehai = EncodeMark.toTehai("34p135567889s477z");
        tehai.kita(HaiPool.z(EncodeMark.BEI));
        tehai.draw(HaiPool.z(EncodeMark.NAN));
        doraSize = DoraHelper.countAllDoraSizeByDisplays(tehai, CollectionUtil.newArrayList(HaiPool.z(EncodeMark.DONG)));
        assertEquals(2, doraSize);
    }
}
