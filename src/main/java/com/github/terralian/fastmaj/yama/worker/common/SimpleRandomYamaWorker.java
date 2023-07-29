package com.github.terralian.fastmaj.yama.worker.common;

import java.util.Random;

import com.github.terralian.fastmaj.yama.IYamaArray;
import com.github.terralian.fastmaj.yama.IYamaWorker;
import com.github.terralian.fastmaj.yama.YamaArray;
import com.github.terralian.fastmaj.yama.seed.random.IdWorker;

/**
 * 一个简易随机的牌山生成器
 *
 * @author terra.lian
 */
public class SimpleRandomYamaWorker implements IYamaWorker {

    private final Random r;

    private long seed;

    public SimpleRandomYamaWorker() {
        seed = IdWorker.getId();
        r = new Random(seed);
    }

    public SimpleRandomYamaWorker(long seed) {
        this.seed = seed;
        r = new Random(seed);
    }

    @Override
    public IYamaArray getNextYama(int nextSize) {
        // 144
        int[] rnd = new int[136];
        // SHA512 hash
        for (int j = 0; j < nextSize; j++) {
            for (int i = 0; i < rnd.length; i++) {
                rnd[i] = r.nextInt(136);
            }
        }
        int[] yama = new int[136];
        for (int i = 0; i < 136; i++) {
            yama[i] = i;
        }
        for (int i = 135; i > 0; i--) {
            int j = rnd[i];
            int tmp = yama[i];
            yama[i] = yama[j];
            yama[j] = tmp;
        }
        return new YamaArray(yama);
    }

    @Override
    public String getSeed() {
        return Long.toString(seed);
    }

    @Override
    public void setSeed(String seed) {
        this.seed = Long.parseLong(seed);
    }
}
