package com.github.terralian.fastmaj.test.yaku;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.agari.ITehaiAgariDivider;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.KazeEnum;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.game.context.PlayerGameContextFactory;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.player.RivalEnum;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.third.mjscore.MjscoreAdapter;
import com.github.terralian.fastmaj.util.CollectionUtil;
import com.github.terralian.fastmaj.util.EmptyUtil;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.IYakuMatcher;
import com.github.terralian.fastmaj.yaku.YakuMatcher;
import com.github.terralian.fastmaj.yaku.h1.Bakaze;
import com.github.terralian.fastmaj.yaku.h1.Haitei;
import com.github.terralian.fastmaj.yaku.h1.Haku;
import com.github.terralian.fastmaj.yaku.h1.Hatu;
import com.github.terralian.fastmaj.yaku.h1.Houtei;
import com.github.terralian.fastmaj.yaku.h1.Iipatu;
import com.github.terralian.fastmaj.yaku.h1.Iipeikou;
import com.github.terralian.fastmaj.yaku.h1.Jikaze;
import com.github.terralian.fastmaj.yaku.h1.Pinfu;
import com.github.terralian.fastmaj.yaku.h1.Reach;
import com.github.terralian.fastmaj.yaku.h1.Rinsyan;
import com.github.terralian.fastmaj.yaku.h1.Tanyaotyu;
import com.github.terralian.fastmaj.yaku.h1.Tuumo;
import com.github.terralian.fastmaj.yaku.h1.TyanKan;
import com.github.terralian.fastmaj.yaku.h1.Tyun;
import com.github.terralian.fastmaj.yaku.h13.Daisangen;
import com.github.terralian.fastmaj.yaku.h13.Daisuusii;
import com.github.terralian.fastmaj.yaku.h13.Kokusi;
import com.github.terralian.fastmaj.yaku.h13.Kokusi13;
import com.github.terralian.fastmaj.yaku.h13.Ryuuiisou;
import com.github.terralian.fastmaj.yaku.h13.SuuankouTanki;
import com.github.terralian.fastmaj.yaku.h13.Syousuusii;
import com.github.terralian.fastmaj.yaku.h13.Tinroutou;
import com.github.terralian.fastmaj.yaku.h13.Tuuiisou;
import com.github.terralian.fastmaj.yaku.h13.Tyuuren;
import com.github.terralian.fastmaj.yaku.h13.Tyuuren9;
import com.github.terralian.fastmaj.yaku.h2.DoubleReach;
import com.github.terralian.fastmaj.yaku.h2.HonTyanta;
import com.github.terralian.fastmaj.yaku.h2.Ittuu;
import com.github.terralian.fastmaj.yaku.h2.Sanankou;
import com.github.terralian.fastmaj.yaku.h2.Sankantu;
import com.github.terralian.fastmaj.yaku.h2.SansyokuDoujyun;
import com.github.terralian.fastmaj.yaku.h2.Tiitoitu;
import com.github.terralian.fastmaj.yaku.h2.Toitoi;
import com.github.terralian.fastmaj.yaku.h3.Honitu;
import com.github.terralian.fastmaj.yaku.h3.JyunTyanta;
import com.github.terralian.fastmaj.yaku.h3.Ryanpeikou;
import com.github.terralian.fastmaj.yaku.h6.Tinitu;
import com.github.terralian.fastmaj.yama.DrawFrom;

/**
 * {@link YakuMatcher}测试
 * <p/>
 * 测试范围：
 * <ul>
 * <li>测试无上下场景
 * <li>测试单一场景
 * <li>测试复合场景
 * <li>测试役满场景
 * </ul>
 * 
 * @author terra.lian
 */
public class YakuMatcherTest {

    private IYakuMatcher yakuMatcher;
    private ITehaiAgariDivider tehaiAgariDivider;

    @Before
    public void before() {
        yakuMatcher = new YakuMatcher();
        tehaiAgariDivider = new MjscoreAdapter();
    }

    /**
     * 测试无用户上下文的场景
     */
    @Test
    public void testWithNonContext() {
        // h1
        // 白
        singleYakuTest("123m456p11789s555z", Haku.class);
        // 发
        singleYakuTest("456m456p789s66677z", Hatu.class);
        // 一杯口
        singleYakuTest("112233m234789s77z", Iipeikou.class);
        // 平和
        singleYakuTest("123m456789p22789s", Pinfu.class);
        // 断幺
        singleYakuTest("234777m345678p44s", Tanyaotyu.class);
        // 中
        singleYakuTest("123m456p11789s777z", Tyun.class);

        // h13
        // 大三元
        singleYakuTest("11m234p555666777z", Daisangen.class);
        // 大四喜
        singleYakuTest("11122233444z22s3z", Daisuusii.class);
        // 国士无双
        singleYakuTest("19m1p19s12345677z9p", Kokusi.class);
        // 国士无双13面
        singleYakuTest("19m19p19s12345677z", Kokusi13.class);
        // 绿一色
        singleYakuTest("22334444666888s", Ryuuiisou.class);
        // 四暗刻（在无上下文情况下不成立）
        // 四暗刻单骑
        singleYakuTest("111333555m88899s", SuuankouTanki.class);
        // 小四喜
        singleYakuTest("1112233444z234s3z", Syousuusii.class);
        // 清老头
        singleYakuTest("111999m11199p11s9p", Tinroutou.class);
        // 字一色
        singleYakuTest("11223344556677z", Tuuiisou.class);
        // 九莲
        singleYakuTest("1112346789999m5m", Tyuuren.class);
        // 纯九莲
        singleYakuTest("1112345678999m9m", Tyuuren9.class);

        // h2
        // 混带
        singleYakuTest("123789m11p111222z", HonTyanta.class);
        // 三暗刻
        singleYakuTest("444999m111p123s22z", Sanankou.class);
        // 三杠子（这里不好测，跳过）
        // 三色同顺
        singleYakuTest("234m234p23499s111z", SansyokuDoujyun.class);
        // 三色同刻（会复合三暗刻，跳过）
        // 小三元（会复合三暗刻，跳过）
        // 七对子
        singleYakuTest("1133557799m2244p", Tiitoitu.class);

        // h3
        // 混一色
        singleYakuTest("123456788m333444z", Honitu.class);
        // 纯全
        singleYakuTest("12378999m111999p", JyunTyanta.class);
        // 两杯口
        singleYakuTest("112233556677m11p", Ryanpeikou.class);

        // 清老头
        singleYakuTest("12334455688999m", Tinitu.class);

        // 无役
        zeroYakuTest("123m456p789s33344z");
    }

    /**
     * 单役测试
     * 
     * @param tehaiMark 记号法手牌
     * @param yakuClass 役种类
     */
    private void singleYakuTest(String tehaiMark, Class<?> yakuClass) {
        ITehai tehai = EncodeMark.toTehai(tehaiMark);
        List<DivideInfo> divideInfos = tehaiAgariDivider.divide(tehai);
        List<IYaku> result = yakuMatcher.match(tehai, CollectionUtil.getFirstElement(divideInfos), null);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getClass() == yakuClass);
    }

    /**
     * 无役测试
     * 
     * @param tehaiMark 记号法手牌
     */
    private void zeroYakuTest(String tehaiMark) {
        ITehai tehai = EncodeMark.toTehai(tehaiMark);
        List<DivideInfo> divideInfos = tehaiAgariDivider.divide(tehai);
        List<IYaku> result = yakuMatcher.match(tehai, CollectionUtil.getFirstElement(divideInfos), null);
        assertEquals(0, result.size());
    }

    // ----------------------------------------

    /**
     * 含用户上下文
     */
    @Test
    public void testWithContext() {
        PlayerGameContext gameContext = PlayerGameContextFactory.buildByNormal(1);
        gameContext.setRon(true);
        // 第一巡非同巡，否则会变成地和
        gameContext.getHaiRivers().forEach(k -> k.setSameFirstJun(false));

        // 场风
        gameContext.setBakaze(KazeEnum.DON);
        ITehai tehai = EncodeMark.toTehai("123456m567p11122z");
        List<IYaku> yakus = yakuMatcher.match(tehai, CollectionUtil.newArrayList(), gameContext);
        assertEquals(1, yakus.size());
        assertTrue(yakus.get(0).getClass() == Bakaze.class);

        // 自风
        gameContext.setBakaze(KazeEnum.DON);
        gameContext.setJikaze(KazeEnum.NAN);
        tehai = EncodeMark.toTehai("123456m567p11222z");
        yakus = yakuMatcher.match(tehai, CollectionUtil.newArrayList(), gameContext);
        assertEquals(1, yakus.size());
        assertTrue(yakus.get(0).getClass() == Jikaze.class);

        // 海底
        gameContext.setYamaCountdown(0);
        gameContext.setRon(false);
        gameContext.setLastDrawFrom(DrawFrom.YAMA);
        tehai = EncodeMark.toTehai("123456m56799p11z");
        tehai.pon(HaiPool.p(9), RivalEnum.BOTTOM);
        yakus = yakuMatcher.match(tehai, CollectionUtil.newArrayList(), gameContext);
        assertEquals(1, yakus.size());
        assertTrue(yakus.get(0).getClass() == Haitei.class);

        // 河底
        gameContext.setYamaCountdown(0);
        gameContext.setRon(true);
        gameContext.setLastTehaiActionType(TehaiActionType.KIRI);
        tehai = EncodeMark.toTehai("123456m56799p11z");
        tehai.pon(HaiPool.p(9), RivalEnum.BOTTOM);
        yakus = yakuMatcher.match(tehai, CollectionUtil.newArrayList(), gameContext);
        assertEquals(1, yakus.size());
        assertTrue(yakus.get(0).getClass() == Houtei.class);

        // 一发，（立直）
        gameContext.setYamaCountdown(50);
        gameContext.setRon(true);
        gameContext.getHaiRivers().forEach(k -> {
            k.setSameJun(true);
            k.reach(HaiPool.random());
        });
        tehai = EncodeMark.toTehai("123456m567999p11z");
        yakus = yakuMatcher.match(tehai, CollectionUtil.newArrayList(), gameContext);
        assertEquals(2, yakus.size());
        assertTrue(yakus.stream().anyMatch(k -> k.getClass() == Iipatu.class));

        // 立直
        gameContext.setYamaCountdown(50);
        gameContext.setRon(true);
        gameContext.getHaiRivers().forEach(k -> {
            k.setSameJun(false);
            k.reach(HaiPool.random());
        });
        tehai = EncodeMark.toTehai("123456m567999p11z");
        yakus = yakuMatcher.match(tehai, CollectionUtil.newArrayList(), gameContext);
        assertEquals(1, yakus.size());
        assertTrue(yakus.get(0).getClass() == Reach.class);

        // 岭上
        gameContext.setYamaCountdown(50);
        gameContext.setRon(false);

        gameContext.setLastTehaiActionType(TehaiActionType.ANNKAN);
        gameContext.getHaiRivers().forEach(k -> k.clear());
        tehai = EncodeMark.toTehai("123456m56799p11z");
        tehai.pon(HaiPool.p(9), RivalEnum.BOTTOM);
        yakus = yakuMatcher.match(tehai, CollectionUtil.newArrayList(), gameContext);
        assertEquals(1, yakus.size());
        assertTrue(yakus.get(0).getClass() == Rinsyan.class);

        // 自摸
        // 第一巡非同巡，否则会变成地和
        gameContext.getHaiRivers().forEach(k -> k.setSameFirstJun(false));
        gameContext.setYamaCountdown(50);
        gameContext.setRon(false);
        gameContext.setLastTehaiActionType(null);
        tehai = EncodeMark.toTehai("123456m567999p11z");
        yakus = yakuMatcher.match(tehai, CollectionUtil.newArrayList(), gameContext);
        assertEquals(1, yakus.size());
        assertTrue(yakus.get(0).getClass() == Tuumo.class);

        // 抢杠
        gameContext.setYamaCountdown(50);
        gameContext.setRon(true);
        gameContext.setLastTehaiActionType(TehaiActionType.KAKAN);
        gameContext.getHaiRivers().forEach(k -> k.clear());
        gameContext.getHaiRivers().forEach(k -> k.setSameFirstJun(false));
        tehai = EncodeMark.toTehai("123456m56799p11z9p");
        yakus = yakuMatcher.match(tehai, CollectionUtil.newArrayList(), gameContext);
        assertEquals(1, yakus.size());
        assertTrue(yakus.get(0).getClass() == TyanKan.class);

        // 两立直
        gameContext = PlayerGameContextFactory.buildByNormal(0);
        gameContext.getHaiRiver().reach(HaiPool.p(1));
        gameContext.setRon(true);
        gameContext.getHaiRivers().forEach(k -> {
            k.setSameFirstJun(true);
            k.setSameJun(false);
            k.reach(HaiPool.random(), true);
        });
        tehai = EncodeMark.toTehai("123456m56799p11z9p");
        yakus = yakuMatcher.match(tehai, CollectionUtil.newArrayList(), gameContext);
        assertEquals(1, yakus.size());
        assertTrue(yakus.get(0).getClass() == DoubleReach.class);
    }

    // ----------------------------------------

    /**
     * 多役复合
     */
    @SuppressWarnings("unchecked")
    @Test
    public void mutiYakuTest() {
        PlayerGameContext gameContext = PlayerGameContextFactory.buildByNormal(0);

        // 立直一发自摸，清一色，一气，一杯口，平和
        gameContext.getHaiRivers().forEach(k -> {
            k.clear();
            k.setSameFirstJun(false);
            k.setSameJun(true);
            k.reach(HaiPool.random(), false);
        });
        ITehai tehai = EncodeMark.toTehai("11223345678999m");
        gameContext.setTehai(tehai);
        List<DivideInfo> divideInfos = tehaiAgariDivider.divide(tehai);
        List<IYaku> yakus = yakuMatcher.match(tehai, divideInfos, gameContext);
        assertEquals(7, yakus.size());
        assertYakus(yakus, Reach.class, Iipatu.class, Tuumo.class, Tinitu.class, Iipeikou.class, Ittuu.class);

        // 宫永咲名场面
        // 清一色，对对和，三暗刻，三杠子，岭上开花
        gameContext.getHaiRivers().forEach(k -> {
            k.clear();
            k.setSameFirstJun(false);
            k.setSameJun(false);
            k.kiri(HaiPool.random());
        });
        tehai = EncodeMark.toTehai("1112222333345p");
        // 从天江衣杠1p
        tehai.minkan(HaiPool.p(1), RivalEnum.TOP);
        // 岭上牌4p
        tehai.draw(HaiPool.p(4));
        // 再暗杠2p
        tehai.annkan(HaiPool.p(2));
        // 岭上牌4p
        tehai.draw(HaiPool.p(4));
        // 再暗杠3p
        tehai.annkan(HaiPool.p(3));
        // 岭上5p和了
        tehai.draw(HaiPool.p(0));
        // 设置上一个动作为暗杠
        gameContext.setLastTehaiActionType(TehaiActionType.ANNKAN);
        // 非荣和
        gameContext.setRon(false);
        divideInfos = tehaiAgariDivider.divide(tehai);
        yakus = yakuMatcher.match(tehai, divideInfos, gameContext);
        assertEquals(5, yakus.size());
        assertYakus(yakus, Tinitu.class, Toitoi.class, Sanankou.class, Sankantu.class, Rinsyan.class);

        // 岭上 白，发，自风，三杠子，对对和，混一色
        gameContext.getHaiRivers().forEach(k -> {
            k.clear();
            k.setSameFirstJun(false);
            k.setSameJun(false);
            k.kiri(HaiPool.random());
        });
        // 非荣和
        gameContext.setRon(false);
        // 设置上一个动作为暗杠
        gameContext.setLastTehaiActionType(TehaiActionType.ANNKAN);
        // 场风南
        gameContext.setBakaze(KazeEnum.NAN);
        // 自风西
        gameContext.setJikaze(KazeEnum.SYA);
        gameContext.setPosition(3);
        tehai = EncodeMark.toTehai("8s111333556666z");
        tehai.minkan(HaiPool.z(1), RivalEnum.BOTTOM);
        tehai.minkan(HaiPool.z(3), RivalEnum.TOP);
        tehai.pon(HaiPool.z(5), RivalEnum.BOTTOM);
        tehai.annkan(HaiPool.z(6));
        tehai.draw(HaiPool.s(8));
        divideInfos = tehaiAgariDivider.divide(tehai);
        yakus = yakuMatcher.match(tehai, divideInfos, gameContext);
        assertEquals(7, yakus.size());
        assertYakus(yakus, Rinsyan.class, Haku.class, Hatu.class, Jikaze.class, Sankantu.class, Toitoi.class, Honitu.class);
    }

    /**
     * 役匹配校验
     * 
     * @param actuals 实际匹配到的役
     * @param expecteds 预期的役
     */
    private void assertYakus(List<IYaku> actuals, Class<? extends IYaku>... expecteds) {
        assertTrue(EmptyUtil.isNotEmpty(actuals));
        for (Class<?> yaku : expecteds) {
            assertTrue(actuals.stream().anyMatch(k -> k.getClass() == yaku));
        }
    }

    // -----------------------------------------

    /**
     * 役满测试
     */
    @Test
    public void yakumanTest() {
        PlayerGameContext gameContext = PlayerGameContextFactory.buildByNormal(0);

        // 绿一色
        ITehai tehai = EncodeMark.toTehai("22233344466s6z");
        tehai.pon(HaiPool.s(6), RivalEnum.BOTTOM);
        tehai.draw(HaiPool.z(6));
        List<DivideInfo> divideInfos = tehaiAgariDivider.divide(tehai);
        List<IYaku> yakus = yakuMatcher.match(tehai, divideInfos, gameContext);
        assertEquals(1, yakus.size());
        assertEquals(Ryuuiisou.class, yakus.get(0).getClass());
    }
}
