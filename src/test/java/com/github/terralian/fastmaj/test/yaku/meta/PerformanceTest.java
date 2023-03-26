package com.github.terralian.fastmaj.test.yaku.meta;

import java.lang.annotation.Annotation;

import com.github.terralian.fastmaj.util.StopWatch;
import com.github.terralian.fastmaj.yaku.h1.Pinfu;
import lombok.Data;

/**
 * 性能测试
 */
public class PerformanceTest {

    // 验证使用
    //@Test
    public void test() {
        int loopSize = 1000_000_000;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("反射");
        int size = 0;
        for (int i = 0; i < loopSize; i++) {
            Annotation[] annotations = Pinfu.class.getDeclaredAnnotations();
            size += annotations.length;
        }
        System.out.println(size);
        stopWatch.stop();

        stopWatch.start("保存");
        Annotation[] annotations = Pinfu.class.getDeclaredAnnotations();
        TestContainer container = new TestContainer();
        container.setSize(annotations.length);
        int sizeB = 0;
        for (int i = 0; i < loopSize; i++) {
            sizeB += container.getSize();
        }
        System.out.println(sizeB);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());

        // 结论，反射的性能消耗10亿次内下达到了9秒，而普通的获取下仅33ms，这部分无端的消耗不是很能接受。
    }

    @Data
    private static class TestContainer {
        private int size;
    }
}
