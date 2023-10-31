package com.github.terralian.fastmaj.paifu.tenhou;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.paifu.IRuleTimelineHandler;
import com.github.terralian.fastmaj.paifu.domain.PaifuGame;
import com.github.terralian.fastmaj.util.EmptyUtil;

/**
 * 天凤牌谱的规则推断处理器
 *
 * @author terra.lian
 */
public class TenhouRuleTimelineHandler implements IRuleTimelineHandler {

    private static final Pattern TIME_PATTERN = Pattern.compile("([0-9]{10})gm-\\w{4}-[0-9]{4}");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHH");

    private static final LocalDateTime TIME_POINT_A = LocalDateTime.of(2010, 6, 1, 7, 0);

    @Override
    public void deduce(GameConfig gameConfig, PaifuGame paifuGame, String... params) {
        // 没有文件名无法推断
        if (EmptyUtil.isEmpty(params)) {
            return;
        }
        String fileName = params[0];
        // 识别文件名中的时间
        Matcher matcher = TIME_PATTERN.matcher(fileName);
        if (matcher.find()) {
            String res = matcher.group(1);
            LocalDateTime localDateTime = LocalDateTime.parse(res, DATE_TIME_FORMATTER);
            // 2010年6月01日之前，听牌不会自动结束
            if (localDateTime.isBefore(TIME_POINT_A)) {
                gameConfig.setGameContinueIfOyaRenchanRyuukyokuAtLastKyoku(true);
            }
        }
    }
}
