package com.github.terralian.fastmaj.test.yama;

import com.github.terralian.fastmaj.yama.IYamaArray;
import com.github.terralian.fastmaj.yama.IYamaWorker;
import com.github.terralian.fastmaj.yama.worker.common.SimpleRandomYamaWorker;
import org.junit.Test;

public class SimpleRandomYamaWorkerTest {

    /**
     * 输出100次，看是否正常
     */
    @Test
    public void test100() {
        IYamaWorker yamaWorker = new SimpleRandomYamaWorker();
        for (int i = 0; i < 100; i++) {
            IYamaArray yama = yamaWorker.getNextYama(1);
            for (int k : yama.value()) {
                System.out.print(format(k));
            }
            System.out.println();
        }
    }

    @Test
    public void testEqual() {
        for (int i = 0; i < 100; i++) {
            IYamaWorker yamaWorker = new SimpleRandomYamaWorker(1L);
            IYamaArray yama = yamaWorker.getNextYama(1);
            for (int k : yama.value()) {
                System.out.print(format(k));
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
