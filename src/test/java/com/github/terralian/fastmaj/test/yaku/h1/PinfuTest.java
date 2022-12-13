package com.github.terralian.fastmaj.test.yaku.h1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.agari.ITehaiAgariDivider;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.KazeEnum;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.third.mjscore.MjscoreAdapter;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h1.Pinfu;

/**
 * {@link Pinfu}平和测试
 * 
 * @author terra.lian 
 */
public class PinfuTest {

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
        IYaku yaku = new Pinfu();
        // 需要场风，自风信息
        PlayerGameContext gameContext = new PlayerGameContext();
        // 手牌
        ITehai tehai = null;
        // 结果
        boolean result = false;

        // 场风东，自风南
        gameContext.setBakaze(KazeEnum.DON);
        gameContext.setJikaze(KazeEnum.NAN);

        // 非鸣牌，无字牌平和，和了牌为两面
        tehai = EncodeMark.toTehai("123456m789p78999s");
        gameContext.setAgariHai(HaiPool.s(9));
        List<DivideInfo> divideInfos = tehaiAgariDivider.divide(tehai);
        assertEquals(1, divideInfos.size());
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertTrue(result);

        // 非鸣牌，无字牌似平和，但是和了牌为坎张
        tehai = EncodeMark.toTehai("123456m789p7999s8s");
        divideInfos = tehaiAgariDivider.divide(tehai);
        assertEquals(1, divideInfos.size());
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertFalse(result);

        // 鸣牌，无字牌，似平和
        tehai = EncodeMark.toTehai("123456m789p8999s");
        tehai.chii(HaiPool.s(7), HaiPool.s(8), HaiPool.s(9));
        divideInfos = tehaiAgariDivider.divide(tehai);
        assertEquals(1, divideInfos.size());
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertFalse(result);

        // 非鸣牌，无字牌，但是非平和
        tehai = EncodeMark.toTehai("111456m789p78999s");
        divideInfos = tehaiAgariDivider.divide(tehai);
        assertEquals(1, divideInfos.size());
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertFalse(result);

        // 似平和，含字牌，雀头是风牌
        tehai = EncodeMark.toTehai("123456m789p789s11z");
        gameContext.setAgariHai(HaiPool.s(9));
        divideInfos = tehaiAgariDivider.divide(tehai);
        assertEquals(1, divideInfos.size());
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertFalse(result);

        // 似平和，含字牌，雀头是风牌
        tehai = EncodeMark.toTehai("123456m789p789s22z");
        gameContext.setAgariHai(HaiPool.s(9));
        divideInfos = tehaiAgariDivider.divide(tehai);
        assertEquals(1, divideInfos.size());
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertFalse(result);

        // 似平和，含字牌，雀头是三元牌
        tehai = EncodeMark.toTehai("123456m789p789s55z");
        gameContext.setAgariHai(HaiPool.s(9));
        divideInfos = tehaiAgariDivider.divide(tehai);
        assertEquals(1, divideInfos.size());
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertFalse(result);

        // 平和，含字牌，雀头不是风牌，和了为2面
        tehai = EncodeMark.toTehai("123456m789p78s33z9s");
        divideInfos = tehaiAgariDivider.divide(tehai);
        assertEquals(1, divideInfos.size());
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertTrue(result);

        // 似平和，平和，含字牌，雀头不是风牌，和了为坎张
        tehai = EncodeMark.toTehai("123456m789p789s33z");
        gameContext.setAgariHai(HaiPool.s(8));
        divideInfos = tehaiAgariDivider.divide(tehai);
        assertEquals(1, divideInfos.size());
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertFalse(result);

        // 似平和，平和，含字牌，雀头不是风牌，和了为边张
        tehai = EncodeMark.toTehai("123456m789p789s33z");
        gameContext.setAgariHai(HaiPool.s(7));
        divideInfos = tehaiAgariDivider.divide(tehai);
        assertEquals(1, divideInfos.size());
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertFalse(result);

        // 还没和牌
        tehai = EncodeMark.toTehai("27m067p12s112447z");
        result = yaku.match(tehai, null, gameContext);
        assertFalse(result);

        // 虽然是平和，但是缺少必要信息
        tehai = EncodeMark.toTehai("123456m789p789s33z");
        divideInfos = tehaiAgariDivider.divide(tehai);
        assertEquals(1, divideInfos.size());
        result = yaku.match(tehai, divideInfos.get(0), new PlayerGameContext());
        assertFalse(result);
    }
}
