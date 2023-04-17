package com.github.terralian.fastmaj.yaku.meta;

import java.util.Objects;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import lombok.Data;

/**
 * 役种的Meta信息
 *
 * @author terra.lian
 * @since 2023-04-17
 */
@Data
public class YakuMeta {
    private JihaiYaku jihaiYaku;
    private KozuYaku kozuYaku;
    private MenchanYaku menchanYaku;
    private MenzuYaku menzuYaku;
    private NonJihaiYaku nonJihaiYaku;
    private RonYaku ronYaku;
    private ShunzuYaku shunzuYaku;
    private RequestContextYaku requestContextYaku;
    /**
     * 包装的目标役种
     */
    private IYaku target;

    @Override
    public boolean equals(Object o) {
        return o instanceof IYaku && target.equals((IYaku) o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(target.getName());
    }

    /**
     * 获取牌谱名称
     */
    public String getYakuName() {
        return target.getName();
    }

    /**
     * 进行役种匹配
     *
     * @param tehai 手牌
     * @param divide 和牌分割，手牌少数情况下有多种分割
     * @param gameContext 玩家对局数据容器
     */
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext gameContext) {
        return target.match(tehai, divide, gameContext);
    }

    @Override
    public String toString() {
        return target.getName();
    }
}
