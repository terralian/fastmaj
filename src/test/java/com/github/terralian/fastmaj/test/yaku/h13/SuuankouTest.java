package com.github.terralian.fastmaj.test.yaku.h13;

import java.util.List;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.agari.ITehaiAgariDivider;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.TehaiBuilder;
import com.github.terralian.fastmaj.third.mjscore.MjscoreAdapter;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h13.Suuankou;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * {@link Suuankou}四暗刻测试
 *
 * @author terra.lian
 */
public class SuuankouTest {

    /**
     * 和了时，手牌分割器
     */
    private ITehaiAgariDivider tehaiAgariDivider;

    @Before
    public void prepare() {
        // 使用mjscore进行手牌分割
        tehaiAgariDivider = new MjscoreAdapter();
    }

    @Test
    public void test() {
        IYaku yaku = new Suuankou();
        // 需要荣和信息
        PlayerGameContext gameContext = new PlayerGameContext();
        // 需要手牌
        ITehai tehai = null;
        // 结果
        boolean result = false;

        // 未鸣牌的自摸四暗刻
        tehai = EncodeMark.toTehai("11133355577m22p7m");
        List<DivideInfo> divideInfos = tehaiAgariDivider.divide(tehai);
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertTrue(result);

        // 四暗刻单骑
        tehai = EncodeMark.toTehai("111333555777m22p");
        divideInfos = tehaiAgariDivider.divide(tehai);
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertFalse(result);

        // 三暗杠对对和
        tehai = TehaiBuilder.from("11133555777m22p") //
                .pon("3m") //
                .get();
        divideInfos = tehaiAgariDivider.divide(tehai);
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertFalse(result);

        // 四暗刻，三杠子
        tehai = TehaiBuilder.from("11133355577m22p7m") //
                .addOne("1m") //
                .annkan("1m") //
                .addOne("3m") //
                .annkan("3m") //
                .addOne("5m") //
                .annkan("5m") //
                .get();
        divideInfos = tehaiAgariDivider.divide(tehai);
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertTrue(result);

        // 四暗刻，混老头
        tehai = EncodeMark.toTehai("111999m111p99s11z9s");
        divideInfos = tehaiAgariDivider.divide(tehai);
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertTrue(result);
    }
}
