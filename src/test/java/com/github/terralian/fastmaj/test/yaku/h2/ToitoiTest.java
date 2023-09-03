package com.github.terralian.fastmaj.test.yaku.h2;

import java.util.List;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.agari.ITehaiAgariDivider;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.TehaiBuilder;
import com.github.terralian.fastmaj.third.mjscore.MjscoreAdapter;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h2.Toitoi;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * {@link Toitoi} 对对和测试
 *
 * @author terra.lian
 */
public class ToitoiTest {

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
        IYaku yaku = new Toitoi();
        // 不需要场况信息
        PlayerGameContext gameContext = new PlayerGameContext();
        // 手牌
        ITehai tehai = null;
        // 结果
        boolean result = false;

        // 鸣牌对对
        tehai = TehaiBuilder.from("11133355777m") //
                .addPon("2p") //
                .get();
        List<DivideInfo> divideInfos = tehaiAgariDivider.divide(tehai);
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertTrue(result);

        // 四暗刻，自摸
        gameContext.setEndByRon(false);
        tehai = EncodeMark.toTehai("111333555777m22p");
        divideInfos = tehaiAgariDivider.divide(tehai);
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertFalse(result);

        // 四暗刻听牌，非单骑荣和
        gameContext.setEndByRon(true);
        tehai = EncodeMark.toTehai("11133355577m22p7m");
        divideInfos = tehaiAgariDivider.divide(tehai);
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertTrue(result);

        // 四暗刻单骑
        gameContext.setEndByRon(true);
        tehai = EncodeMark.toTehai("111333555777m22p");
        divideInfos = tehaiAgariDivider.divide(tehai);
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertFalse(result);
    }

}
