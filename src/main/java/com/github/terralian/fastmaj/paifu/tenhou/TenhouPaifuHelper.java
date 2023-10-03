package com.github.terralian.fastmaj.paifu.tenhou;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 天凤牌谱帮助类
 *
 * @author terra.tian
 */
public abstract class TenhouPaifuHelper {

    private static final Pattern rawIdPattern = Pattern.compile("([0-9]{8,10}gm-[0-9a-f]{4}-[0-9]{4}-[0-9a-f]{8})(&tw=[0-9])?(.mjlog)?");

    /**
     * 通过形如 2011012516gm-00a9-0000-6e60e3de&tw=2.mjlog，格式的文件获取其中 2011012516gm-00a9-0000-6e60e3de部分的内容
     *
     * @param nameWithSuffix 包含后缀的文件名
     */
    public static String getRawId(String nameWithSuffix) {
        Matcher matcher = rawIdPattern.matcher(nameWithSuffix);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
