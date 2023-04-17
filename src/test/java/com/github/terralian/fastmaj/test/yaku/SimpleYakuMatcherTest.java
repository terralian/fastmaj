package com.github.terralian.fastmaj.test.yaku;

import com.github.terralian.fastmaj.third.mjscore.MjscoreAdapter;
import com.github.terralian.fastmaj.yaku.SimpleYakuMatcher;

/**
 * @author terra.lian
 * @since 2023-04-17
 */
public class SimpleYakuMatcherTest extends YakuMatcherTest {

    @Override
    public void before() {
        yakuMatcher = new SimpleYakuMatcher();
        tehaiAgariDivider = new MjscoreAdapter();
    }
}
