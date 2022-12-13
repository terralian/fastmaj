package com.github.terralian.fastmaj.test.tehai;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.terralian.fastmaj.tehai.NakiShape;


public class NakiShapeTest {

    // 测试问题集（所有场景）
    private Integer[] testProblems = new Integer[] {
        // 上家的三种
        0b0101, 0b1001, 0b1101,
        // 下家，仅有一种，即碰/杠
        0b0110,
        // 对家，仅有一种，即碰/杠
        0b0111
    };

    // 测鸣牌索引答案
    private Integer[] nakiIndexAnswer = new Integer[] {
        // 上家的三种
        0, 1, 2,
        // 下家，仅有一种，即碰/杠
        0,
        // 对家，仅有一种，即碰/杠
        0
    };
    
    @Test
    public void testGetNakiIndex() {
        for (int i = 0; i < testProblems.length; i++) {
            assertEquals(nakiIndexAnswer[i], NakiShape.getNakiIndex(testProblems[i]));
        }
    }
}
