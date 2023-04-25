package com.github.terralian.fastmaj.game.event.river;

import com.github.terralian.fastmaj.game.event.GameEventEnum;
import com.github.terralian.fastmaj.hai.IHai;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 鸣牌-碰动作
 *
 * @author terra.lian
 * @since 2023-03-29
 */
@Data
@Accessors(chain = true)
public class PonEvent implements RiverActionEvent {

    /**
     * 从玩家鸣牌
     */
    private int from;

    /**
     * 鸣牌的玩家
     */
    private int position;

    /**
     * 自己的搭子
     */
    private IHai[] selfHais;

    /**
     * 鸣的牌
     */
    private IHai nakiHai;

    @Override
    public GameEventEnum getType() {
        return GameEventEnum.PON;
    }
}
