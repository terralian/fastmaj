package com.github.terralian.fastmaj.test.yama;

import org.junit.Test;

import com.github.terralian.fastmaj.yama.IYamaWorker;
import com.github.terralian.fastmaj.yama.SimpleRandomYamaWorker;

public class SimpleRandomYamaWorkerTest {

    /**
     * 输出100次，看是否正常
     */
    @Test
    public void test100() {
        IYamaWorker yamaWorker = new SimpleRandomYamaWorker();
        for (int i = 0; i < 100; i++) {
            int[] yama = yamaWorker.getNextYama(1);
            for (int j = 0; j < yama.length; j++) {
                System.out.print(format(yama[j]));
            }
            System.out.println();
        }
    }

    @Test
    public void testEqual() {
        for (int i = 0; i < 100; i++) {
            IYamaWorker yamaWorker = new SimpleRandomYamaWorker(1L);
            int[] yama = yamaWorker.getNextYama(1);
            for (int j = 0; j < yama.length; j++) {
                System.out.print(format(yama[j]));
            }
            System.out.println();
        }
    }

    private String format(int i) {
        if (i < 10) {
            return "  " + String.valueOf(i) + ",";
        } else if (i < 100) {
            return " " + String.valueOf(i) + ",";
        }
        return String.valueOf(i) + ",";
    }
}
