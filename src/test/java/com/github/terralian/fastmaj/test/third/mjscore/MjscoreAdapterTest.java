package com.github.terralian.fastmaj.test.third.mjscore;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.agari.ITehaiAgariDivider;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.third.mjscore.MjscoreAdapter;
import com.github.terralian.fastmaj.util.CollectionUtil;

/**
 * 
 * @author terra.lian
 * @since 2022-12-02
 */
public class MjscoreAdapterTest {

    private ITehaiAgariDivider agariDivider;

    @Before
    public void before() {
        agariDivider = new MjscoreAdapter();
    }

    @Test
    public void test() {
        // 暗杠下的一杯口
        ITehai tehai = EncodeMark.toTehai("223344m99p77s8888m");
        tehai.annkan(HaiPool.m(8));
        tehai.draw(HaiPool.p(9));
        List<DivideInfo> divideInfos = agariDivider.divide(tehai, true, HaiPool.p(9));
        assertEquals(1, CollectionUtil.size(divideInfos));
        assertEquals(true, divideInfos.get(0).isIipeikou());
    }
}
