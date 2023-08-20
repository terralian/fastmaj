package com.github.terralian.fastmaj.test.game;

import com.github.terralian.fastmaj.yama.worker.UnsupportedYamaSeedException;
import org.junit.Test;

/**
 * 天凤的一些特殊牌谱例子
 */
public abstract class AbstractTenhouSpecialSamplePaifuTest extends AbstractTenhouPaifuTest {

    /**
     * 正在进行测试修复缺陷的单独样本
     */
    @Test
    public void testing_and_wait_fix() throws Exception {
    }

    /**
     * 曾经易出错的点的特别样本测试，只是错误的地方未进行标记
     */
    @Test
    public void special_sample_paifu_test() throws Exception {
        simulate_run_game("2014050315gm-0041-0000-0f3f6de5&tw=3.mjlog", true);
        simulate_run_game("2014050315gm-0041-0000-06a52f11&tw=2.mjlog", true);
        simulate_run_game("2014050315gm-0041-0000-7ddece8e&tw=0.mjlog", true);
        simulate_run_game("2014050706gm-0041-0000-0f2d0390&tw=1.mjlog", true);
        simulate_run_game("2014050706gm-0041-0000-d961758c&tw=3.mjlog", true);
        simulate_run_game("2014050707gm-0041-0000-5cf9066d&tw=3.mjlog", true);
        simulate_run_game("2014050821gm-0041-0000-defb2e94&tw=0.mjlog", true);
        simulate_run_game("2014050911gm-0041-0000-01181f16&tw=0.mjlog", true);
        simulate_run_game("2014050912gm-0041-0000-5b9bd6a8&tw=1.mjlog", true);
        // 末场流局后（庄家未听牌），场上存在立直棒时，需要把立直棒给1位
        simulate_run_game("2014051204gm-0041-0000-c9ebd8d8&tw=1.mjlog", true);
        simulate_run_game("2014051313gm-0041-0000-c47daaf7&tw=2.mjlog", true);
        simulate_run_game("2014051313gm-0041-0000-c7bd6c03&tw=3.mjlog", true);
        simulate_run_game("2014051419gm-0041-0000-7ec58083&tw=1.mjlog", true);
        simulate_run_game("2014051420gm-0041-0000-f7f77570&tw=0.mjlog", true);
        simulate_run_game("2014051511gm-0041-0000-a29f5b7b&tw=1.mjlog", true);
        simulate_run_game("2014051613gm-0041-0000-3366561a&tw=0.mjlog", true);
        simulate_run_game("2014052605gm-0041-0000-5db7afd1&tw=1.mjlog", true);
        simulate_run_game("2014052606gm-00c1-0000-6c0dfa74&tw=1.mjlog", true);
        simulate_run_game("2014052711gm-00c1-0000-82c05143&tw=1.mjlog", true);
        simulate_run_game("2014060412gm-00c1-0000-abd08a3d&tw=2.mjlog", true);
        simulate_run_game("2014060512gm-00c1-0000-59befdce&tw=1.mjlog", true);
        simulate_run_game("2014060512gm-00c1-0000-eb8a80a7&tw=3.mjlog", true);
        simulate_run_game("2014060513gm-00c1-0000-84fa529d&tw=1.mjlog", true);
        simulate_run_game("2014060513gm-00c1-0000-b2530541&tw=0.mjlog", true);
        simulate_run_game("2014072411gm-0089-0000-37d4cafc&tw=1.mjlog", true);
        simulate_run_game("2014120309gm-0029-0000-ed40750a&tw=1.mjlog", true);
        simulate_run_game("2014120511gm-0029-0000-511b245b&tw=0.mjlog", true);
        simulate_run_game("2014120816gm-0029-0000-6c78c8b6&tw=3.mjlog", true);
        simulate_run_game("2014051123gm-0041-0000-19ae6ef7&tw=0.mjlog", true);
        simulate_run_game("2015011211gm-0029-0000-2dd7ec0f&tw=3.mjlog", true);
        simulate_run_game("2015041416gm-0029-0000-5e89a32a&tw=0.mjlog", true);
        simulate_run_game("2015050103gm-0029-0000-c8a73922&tw=1.mjlog", true);
        simulate_run_game("2015070411gm-00a9-0000-b8b13fcc&tw=0.mjlog", true);
        simulate_run_game("2015070614gm-00a9-0000-a76c73b1&tw=2.mjlog", true);
        simulate_run_game("2016110202gm-00a9-0000-9bd0c557&tw=3.mjlog", true);
        simulate_run_game("2018120719gm-00a9-0000-f41035fe&tw=2.mjlog", true);
        simulate_run_game("2019110302gm-00a9-0000-473e5852&tw=3.mjlog", true);
        simulate_run_game("2021072417gm-00a9-0000-bd1e1772&tw=1.mjlog", true);
        // 三家和了
        simulate_run_game("2021010917gm-0089-0000-d9225d74&tw=1.mjlog", true);
        simulate_run_game("2015012707gm-0029-0000-bf2d93df&tw=2.mjlog", true);
    }

    /**
     * 重构前遗留的牌谱，也加入到特别样本进行测试
     */
    @Test
    public void old_sample_paifu_test() throws Exception {
        simulate_run_game("2014050315gm-0041-0000-0f3f6de5&tw=3.mjlog", true);

        simulate_run_game("2014050315gm-0041-0000-06a52f11&tw=2.mjlog", true);
        simulate_run_game("2014050315gm-0041-0000-6babc3e5&tw=0.mjlog", true);
        simulate_run_game("2014050315gm-0041-0000-7ddece8e&tw=0.mjlog", true);
        simulate_run_game("2014050706gm-0041-0000-4e38a006&tw=1.mjlog", true);
        simulate_run_game("2014050707gm-0041-0000-5cf9066d&tw=3.mjlog", true);

        simulate_run_game("2014050821gm-0041-0000-a1298f1a&tw=2.mjlog", true);
        simulate_run_game("2014050911gm-0041-0000-01181f16&tw=0.mjlog", true);
        simulate_run_game("2014051313gm-0041-0000-c47daaf7&tw=2.mjlog", true);
        simulate_run_game("2014051511gm-0041-0000-a29f5b7b&tw=1.mjlog", true);
        simulate_run_game("2014052604gm-0041-0000-dc1795af&tw=3.mjlog", true);

        simulate_run_game("2014052605gm-0041-0000-5db7afd1&tw=1.mjlog", true);
        simulate_run_game("2014060513gm-00c1-0000-84fa529d&tw=1.mjlog", true);
        simulate_run_game("2014072411gm-0089-0000-37d4cafc&tw=1.mjlog", true);
        simulate_run_game("2014112412gm-00c1-0000-bfbe4417&tw=1.mjlog", true);
        simulate_run_game("2014120309gm-0029-0000-ed40750a&tw=1.mjlog", true);

        simulate_run_game("2014120822gm-0029-0000-808ea6c0&tw=0.mjlog", true);
        simulate_run_game("2014051123gm-0041-0000-19ae6ef7&tw=0.mjlog", true);
        simulate_run_game("2014120823gm-0029-0000-26f781c3&tw=0.mjlog", true);
        // 加杠后岭上开花（无新宝牌）
        simulate_run_game("2014123118gm-0029-0000-4700b218&tw=0.mjlog", true);
        simulate_run_game("2015070411gm-00a9-0000-b8b13fcc&tw=0.mjlog", true);

        simulate_run_game("2015080113gm-00a9-0000-0213ac0c&tw=3.mjlog", true);
        simulate_run_game("2015082211gm-00a9-0000-b665ff0a&tw=1.mjlog", true);
        simulate_run_game("2018061303gm-00a9-0000-5a8850e9&tw=2.mjlog", true);
        simulate_run_game("2019050400gm-00a9-0000-8c08e32a&tw=3.mjlog", true);
        simulate_run_game("2019110302gm-00a9-0000-473e5852&tw=3.mjlog", true);

        simulate_run_game("2021041720gm-00a9-0000-f3b19e15&tw=3.mjlog", true);
        simulate_run_game("2021010917gm-0089-0000-d9225d74&tw=1.mjlog", true);
        simulate_run_game("2021100323gm-00a9-0000-d0ac1302&tw=1.mjlog", true);
    }

    /**
     * 2009-2010年时间段，天凤对规则及种子改造时期的牌谱兼容性测试
     */
    @Test
    public void tenhou_2009_old_paifu_support_test() throws Exception {
        // 旧版牌山和新版存在一丢丢差异
        simulate_run_game("2009072917gm-0061-0000-85a7478c&tw=3.mjlog", true);
        // 旧版岭上区和新版的顺序不一致
        simulate_run_game("2009072918gm-0061-0000-e6e91672&tw=0.mjlog", true);
        // 饼参加了一场无红宝牌
        simulate_run_game("2009082817gm-00ab-0000-997dbe26&tw=0.mjlog", true);
        // 时间更后，已经切换了种子规则
        simulate_run_game("2010030412gm-00e1-0000-4a275c59&tw=0.mjlog", true);
    }

    /**
     * 2009-2010年时间段，连庄规则不一致的兼容性测试
     *
     * @throws Exception
     */
    @Test
    public void test_game_if_end_when_oya_renchan() throws Exception {
        // 新旧规则冲突
        simulate_run_game("2009082713gm-00e1-0000-8820f355&tw=3.mjlog", true);
        // 旧版，庄家未连庄，结束
        simulate_run_game("2009082812gm-00e1-0000-47dd79cf&tw=3.mjlog", true);
        // 旧版，庄家连庄，不结束
        simulate_run_game("2009082815gm-00e1-0000-b7e41fe8&tw=3.mjlog", true);
        // 旧版，庄家和了，结束
        simulate_run_game("2009073112gm-0061-0000-cad1a977&tw=3.mjlog", true);
        // 新版规则导入那一天的牌谱（庄家听牌不结束）
        simulate_run_game("2010060106gm-00a9-0000-30d6de3b&tw=0.mjlog", true);
        // 新版规则导入的附近的时间的牌谱
        simulate_run_game("2010060800gm-0061-0000-6d767860&tw=1.mjlog", true);
        // 新版结束
        simulate_run_game("2015060814gm-0029-0000-f1ac5f4d&tw=3.mjlog", true);
    }

    /**
     * 易出错的牌谱测试
     */
    @Test
    public void error_prone_yaku_test() throws Exception {
        // 红5万加杠，加杠在天凤解析内使用的是不同的值，易错（后续看看能否统一为一个值）
        simulate_run_game("2015070814gm-00a9-0000-37705455&tw=1.mjlog", true);
        // 河底
        simulate_run_game("2010050507gm-00a9-0000-f13f215d&tw=1.mjlog", true);
        // 四暗刻单骑
        simulate_run_game("2010112211gm-00a9-0000-56a320b2&tw=1.mjlog", true);
        // 地和
        simulate_run_game("2013111600gm-00a9-0000-506bac66&tw=1.mjlog", true);
        // 单河底
        simulate_run_game("2013020314gm-0029-0000-6d6d3c5d&tw=0.mjlog", true);
        // 多分割下的单三色同顺
        simulate_run_game("2013112515gm-00a9-0000-baa070ad&tw=0.mjlog", true);
        // 九莲
        simulate_run_game("2010050420gm-0089-0000-1e338bad&tw=1.mjlog", true);
        // 抢杠瞬间放铳时的一发判定
        simulate_run_game("2014121823gm-00e1-0000-375a275e&tw=1.mjlog", true);
        // 加杠后岭上开花，不计算新宝牌
        // 明杠后打出牌的一瞬间荣和，此时计新宝牌
        simulate_run_game("2018060200gm-00a9-0000-20015615&tw=1.mjlog", true);
    }

    /**
     * 包牌样本测试
     */
    @Test
    public void blame_sample_test() throws Exception {
        // 大三元包牌
        simulate_run_game("2010060416gm-0061-0000-2d91fbe3&tw=0.mjlog", true);
    }

    /**
     * 三家和了流局测试
     */
    @Test
    public void test_ron3_ryuukyoku() throws Exception {
        simulate_run_game("2010113000gm-0061-0000-ef7062e1&tw=3.mjlog", true);
    }

    /**
     * 尚未支持的牌谱异常抛出测试
     */
    @Test(expected = UnsupportedYamaSeedException.class)
    public void current_un_support_old_paifu() throws Exception {
        // 四暗刻单骑
        simulate_run_game("2009071823gm-0041-0000-ff3a2214&tw=0.mjlog", true);
    }
}
