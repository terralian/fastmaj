package com.github.terralian.fastmaj.test.hai;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.hai.IHai;

public class HaiTest {

    @Test
    public void equals() {
        // 相等
        assertTrue(HaiPool.firstHai().equals(HaiPool.firstHai()));

        // SET下相等
        Set<IHai> hais = new HashSet<>();
        hais.add(HaiPool.getById(28));
        assertTrue(hais.contains(HaiPool.getById(29)));
    }
}
