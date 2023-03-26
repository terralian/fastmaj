package com.github.terralian.fastmaj.test.tehai.syaten;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.github.terralian.fastmaj.tehai.FastSyatenCalculator;

/**
 * {@link com.github.terralian.fastmaj.tehai.FastSyatenCalculator} 测试
 *
 * @author terra.lian
 */
public class FastSyatenCalculatorTest extends SyatenCalculatorTest {

    @Before
    @Override
    public void before() {
        super.calculator = new FastSyatenCalculator();
    }

    @Test
    public void fastSampleTest() {
        int[] testCase = new int[] {1, 1, 1, 0, 0, 0, 0, 0, 0, // manzu
                0, 1, 0, 1, 1, 0, 2, 0, 1, // pinzu
                0, 0, 0, 0, 0, 0, 0, 0, 0, // souzu
                1, 0, 1, 0, 3, 0, 0// jihai
        };
        int syaten = calculator.calcNormal(testCase);
        assertEquals(2, syaten);
    }
}
