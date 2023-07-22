package com.github.terralian.fastmaj.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.github.terralian.fastmaj.game.action.DrawAction;
import com.github.terralian.fastmaj.game.action.GameEndAction;
import com.github.terralian.fastmaj.game.action.river.IRiverAction;
import com.github.terralian.fastmaj.game.action.river.RiverActionType;
import com.github.terralian.fastmaj.game.action.tehai.ITehaiAction;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.game.context.PlayerGameContextFactory;
import com.github.terralian.fastmaj.game.event.ActionEvents;
import com.github.terralian.fastmaj.game.event.river.RiverActionEvent;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;
import com.github.terralian.fastmaj.game.option.DoraAddRule;
import com.github.terralian.fastmaj.game.ryuuky.IRyuukyokuResolver;
import com.github.terralian.fastmaj.game.ryuuky.IRyuukyokuResolverManager;
import com.github.terralian.fastmaj.game.ryuuky.Ron3RyuukyokuResolver;
import com.github.terralian.fastmaj.game.validator.GameEndValidator;
import com.github.terralian.fastmaj.game.validator.tehai.ITehaiActionValidator;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.player.IPlayer;
import com.github.terralian.fastmaj.player.RivalEnum;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.util.Assert;
import lombok.Getter;
import lombok.Setter;

/**
 * 按步骤执行游戏，该类为游戏的操作步骤基类，内部对各个动作进行了分类归纳，但还是需要按照一定步骤才可以正确的执行游戏。
 * 一个正确的自动执行的游戏可以参考 {@link StreamMajongGame}
 *
 * @author terra.lian
 */
@Getter
@Setter
public abstract class StepMajongGame {
    /**
     * 游戏规则
     */
    protected GameConfig config;
    /**
     * 游戏核心
     */
    protected IGameCore gameCore;

    // --------------------------------------------
    // 流程动作及事件判定
    // --------------------------------------------
    /**
     * 摸牌动作
     */
    protected DrawAction drawAction = new DrawAction();
    /**
     * 游戏结束判定器
     */
    protected GameEndValidator gameEndValidator = new GameEndValidator();
    /**
     * 游戏结束动作
     */
    protected GameEndAction gameEndAction = new GameEndAction();
    /**
     * 流局可执行事件判定器
     */
    protected IRyuukyokuResolverManager ryuukyokuResolverManager;
    /**
     * 玩家动作管理器
     */
    protected IPlayerActionManager playerActionManager;
    /**
     * 特殊流局：三家和了处理
     */
    protected Ron3RyuukyokuResolver ron3RyuukyokuResolver = new Ron3RyuukyokuResolver();

    // --------------------------------------------
    // 公共方法
    // --------------------------------------------

    /**
     * 游戏开始，对游戏执行初始化，但是还未开始对局，当前局-1，当前玩家-1
     */
    public void startGame() {
        gameCore.startGame();
    }

    /**
     * 游戏结束，改变游戏状态，打印日志
     */
    public void endGame(int[] increaseAndDecrease) {
        gameCore.endGame(increaseAndDecrease);
    }

    /**
     * 校验游戏是否结束，若判定游戏继续，则开始下一局，否则结束游戏。
     * <p/>
     * 对于无法在开始下一局判定游戏结束的场景，可以直接调用{@link IGameCore#endGame(int[])}}结束游戏
     *
     * @return 游戏状态
     */
    public GameRunningState nextKyoku() {
        // 校验游戏是否结束
        if (gameEndValidator.validate(config, gameCore)) {
            gameEndAction.doAction(config, gameCore);
            return GameRunningState.END;
        }
        // 下一回合，执行初始化，分发手牌等
        gameCore.nextKyoku();
        return GameRunningState.CONTINUE;
    }

    /**
     * 从牌山或者从岭上摸下一枚牌。当牌山没有下一枚可摸的牌，返回对局结束
     *
     * @return 对局状态
     */
    public KyokuState nextDraw() {
        if (gameCore.getYamaCountdown() <= 0) {
            return KyokuState.END;
        }

        // 当上一轮的牌河动作存在时，清空上一轮动作，且仅有杠需要摸牌
        RiverActionEvent lastRiverAction = gameCore.getLastRiverAction();
        if (lastRiverAction != null) {
            // 吃碰不需要摸牌
            if (!ActionEvents.needAddDraw(lastRiverAction)) {
                return KyokuState.CONTINUE;
            }
        }

        // 判定是否需要切换玩家，若上一轮玩家有动作，并且是模切，立直时，切换玩家
        // 当玩家是暗杠加杠等动作，则可以从王牌区再进行一次摸牌
        // 当玩家被牌河动作鸣牌，在牌河动作方法里会进行一次玩家切换
        if (lastRiverAction == null && gameCore.getLastTehaiAction() != null //
                && ActionEvents.needSwitchPlayer(gameCore.getLastTehaiAction())) {
            gameCore.nextPlayer();
        }

        // 为玩家摸一张手牌，可能是通常牌，也可能是岭上牌
        // 摸牌后，牌山可用数 - 1
        drawAction.doAction(config, gameCore);
        return KyokuState.CONTINUE;
    }

    /**
     * 调用{@link IPlayer}接口请求玩家的手牌动作，校验并执行该动作。若手牌动作为自摸，则返回对局结束。
     *
     * @return 对局状态
     */
    public KyokuState nextTehaiAction() {
        // 当前玩家
        int position = gameCore.getPosition();
        // 玩家的手牌
        ITehai tehai = gameCore.getTehai();

        // 获取可执行的手牌动作
        Set<TehaiActionType> enableActions = playerActionManager.validateTehaiActions(position, config, gameCore);
        // 在如雀魂娱乐局允许多次和了的情况，这里就需要进行调整
        Assert.notEmpty(enableActions, "玩家无可用按钮，请检查动作判定器，一般至少切牌是可用的");

        // 请求玩家动作
        IPlayer player = gameCore.getPlayer(position);
        PlayerGameContext playerGameContext = PlayerGameContextFactory.buildByGameCore(position, config, gameCore);
        TehaiActionEvent tehaiActionEvent = player.drawHai(tehai, enableActions, playerGameContext);
        // 校验并执行玩家动作
        return nextTehaiAction(tehaiActionEvent);
    }

    /**
     * 传入一个手牌动作，校验动作并执行。若手牌动作为自摸，则返回对局结束。
     *
     * @param tehaiActionEvent 手牌动作
     * @return 对局状态
     */
    public KyokuState nextTehaiAction(TehaiActionEvent tehaiActionEvent) {
        if (tehaiActionEvent == null) {
            throw new IllegalStateException("玩家必须对手牌动作进行处理，至少是模切");
        }

        // 手牌动作前的规则可选时点，新宝牌处理
        // 在这种情况下，岭上牌是在牌山新宝牌番出来之后才会摸
        optionBeforeTehaiAction();

        // 当前玩家
        int position = gameCore.getPosition();
        // 校验这个动作是否合法
        ITehaiActionValidator validator = playerActionManager.getTehaiActionValidator(tehaiActionEvent.getActionType());
        if (!validator.resolveAction(position, config, gameCore)) {
            throw new IllegalArgumentException("非法手牌动作：" + tehaiActionEvent.getActionType());
        }

        tehaiActionEvent.setPosition(gameCore.getPosition());
        // 执行动作
        ITehaiAction tehaiAction = playerActionManager.getTehaiAction(tehaiActionEvent.getActionType());
        if (tehaiAction == null) {
            throw new IllegalStateException(
                    "该手牌处理动作无法执行，动作管理器获取该动作为空：" + tehaiActionEvent.getActionType());
        }
        KyokuState kyokuState = tehaiAction.doAction(tehaiActionEvent, gameCore, config);
        // 手牌动作后的规则可选时点，新宝牌处理
        // 在这种规则下，岭上开花等结束动作不会翻新的宝牌
        if (kyokuState != KyokuState.END) {
            optionAfterTehaiAction();
        }

        // 动作执行完成后，作为上一轮的手牌动作
        gameCore.setLastTehaiAction(tehaiActionEvent);

        // 清除上一轮牌河动作缓存
        // 当无人鸣牌时，相当于无效操作
        // 当有人鸣牌时，相当于在其鸣牌后，即将打一枚牌时清空鸣牌动作
        // 此时已经经过了流局、摸牌判定，上一轮的牌河动作已经失去作用
        // 放在这里也是最兼容回放的做法（回放没有流程判定，鸣牌后就是弃牌操作）
        gameCore.setLastRiverAction(null);

        return kyokuState;
    }

    /**
     * 流局判定并执行，在规则时点执行之后执行。
     * <ul>
     * <li>九种九牌（玩家动作）
     * <li>四家立直，所有人立直后不会被牌河动作处理
     * <li>四风连打，最后一枚风牌不会被牌河动作处理
     * <li>三家和了（未实现，需要在牌河动作特殊处理）
     * <li>四杠散了，对鸣牌动作做该流局判定，若流局不能执行动作。
     * <li>荒牌流局，牌山的剩余枚数限制了鸣牌动作
     * <li>流局满贯，荒牌流局的特殊形式，无影响
     * </ul>
     */
    public KyokuState resolverRyuukyoku() {
        IRyuukyokuResolver resolver = ryuukyokuResolverManager.resolve(config, gameCore);
        if (resolver != null) {
            resolver.execute(config, gameCore);
            return KyokuState.END;
        }
        return KyokuState.CONTINUE;
    }

    /**
     * 批量请求所有对手对该手牌动作的牌河处理动作，鸣牌或者荣和，当存在多个玩家响应，执行优先级最高的动作。
     * 当为荣和时，返回对局结束状态，非荣和时会将唯一的动作存入游戏核心{@link IGameCore#setLastRiverAction}，这个动作将是是否摸牌的判断依据
     *
     * @return 对局状态，若为结束状态则需要立即结束当前对局
     */
    public KyokuState nextRiverAction() {
        // 当前玩家的手牌动作信息
        TehaiActionEvent lastTehaiAction = gameCore.getLastTehaiAction();
        IHai actionHai = lastTehaiAction.getIfHai();
        TehaiActionType actionType = lastTehaiAction.getActionType();

        // 循环所有玩家，获取其可执行动作集合
        List<RiverActionEvent> actions = new ArrayList<>();
        for (int i = 0; i < config.getPlayerSize(); i++) {
            // 跳过当前处理手牌的玩家
            if (gameCore.getPosition() == i) {
                continue;
            }

            PlayerGameContext playerGameContext = PlayerGameContextFactory.buildByGameCore(i, config, gameCore);
            Set<RiverActionType> enableActions = playerActionManager.validateRiverActions(i, //
                    lastTehaiAction, config, gameCore, playerGameContext
            );
            // 没有可执行动作则跳过
            if (enableActions.isEmpty()) {
                continue;
            }

            // 为玩家执行动作，校验该动作合法，并加入到集合
            IPlayer player = gameCore.getPlayer(i);
            RiverActionEvent nakiAction =
                    player.nakiOrRon(gameCore.getPosition(), actionType, enableActions, playerGameContext);
            // 玩家跳过执行
            if (nakiAction == null || nakiAction.getRiverType() == RiverActionType.SKIP) {
                continue;
            }
            if (!enableActions.contains(nakiAction.getRiverType())) {
                throw new IllegalStateException("玩家" + (i + 1) + "不可执行的牌河动作：" + nakiAction.getRiverType());
            }
            // 设置无需由玩家设置的项
            nakiAction.setFromHai(actionHai).setPosition(i).setFrom(gameCore.getPosition());
            actions.add(nakiAction);
        }

        return nextRiverAction(actions);
    }

    /**
     * 传入多个玩家的牌河处理动作值，执行优先级最高的动作。
     * 当为荣和时，返回对局结束状态， 非荣和时会将唯一的动作存入游戏核心{@link IGameCore#setLastRiverAction}，这个动作将是是否摸牌的判断依据
     *
     * @param actions 动作
     * @return 对局状态，若为结束状态则需要立即结束当前对局
     */
    public KyokuState nextRiverAction(List<RiverActionEvent> actions) {
        // 没有人处理，返回对局继续
        if (actions.isEmpty()) {
            return KyokuState.CONTINUE;
        }

        TehaiActionEvent tehaiAction = gameCore.getLastTehaiAction();

        // 有动作的情况，先对动作排序，取优先级最高的
        // 若优先级相同，需要以手牌动作玩家的视角，按下家，对家，上家的顺序优先处理
        // 对于部分规则仅允许一人和牌时，取第一动作即可
        actions.sort((a, b) -> {
            if (a.getOrder() == b.getOrder()) {
                return RivalEnum.compare(tehaiAction.getPosition(), a.getPosition(), b.getPosition());
            }
            return a.getOrder() - b.getOrder();
        });

        // 三家和了特殊处理
        if (ron3RyuukyokuResolver.validate(actions, config, gameCore)) {
            ron3RyuukyokuResolver.execute(config, gameCore);
            return KyokuState.END;
        }

        // 若需要判定三家和了，只能在这里进行判定
        // 处理所有玩家最高优先级的动作，荣和的情况下返回荣和
        RiverActionEvent firstAction = actions.get(0);
        for (RiverActionEvent action : actions) {
            if (firstAction.getRiverType() != action.getRiverType()) {
                break;
            }
            IRiverAction riverAction = playerActionManager.getRiverAction(action.getRiverType());
            // 切换到对应玩家，执行动作
            gameCore.switchPlayer(action.getPosition());
            riverAction.doAction(action, config, gameCore);
        }

        // 荣和情况下结束对局
        if (ActionEvents.isEndEvent(firstAction)) {
            // 手动结束对局
            gameCore.endKyoku();
            return KyokuState.END;
        }
        // 非荣和情况下设置最近牌河动作
        gameCore.setLastRiverAction(firstAction);

        // 对局继续
        return KyokuState.CONTINUE;
    }

    /**
     * 为回放设置的牌河动作执行，传入一个牌谱的牌河处理动作并进行处理。若非荣和动作，则作为最近的牌河动作存入游戏核心
     *
     * @param playerPosition 玩家坐席
     * @param actionEvent 牌河事件
     */
    public void nextRiverAction(int playerPosition, RiverActionEvent actionEvent) {
        TehaiActionEvent lastTehaiAction = gameCore.getLastTehaiAction();
        IHai actionHai = lastTehaiAction.getIfHai();

        actionEvent.setFrom(lastTehaiAction.getPosition()) //
                .setPosition(playerPosition) //
                .setFromHai(actionHai);

        IRiverAction riverAction = playerActionManager.getRiverAction(actionEvent.getRiverType());
        // 切换到对应玩家，执行动作
        gameCore.switchPlayer(actionEvent.getPosition());
        riverAction.doAction(actionEvent, config, gameCore);

        // 荣和情况下结束对局
        if (!ActionEvents.isEndEvent(actionEvent)) {
            gameCore.setLastRiverAction(actionEvent);
        }
    }

    /**
     * 在手牌动作之前的一个执行时点，特别的规则设置会在这里执行。如宝牌增加规则设置为{@link DoraAddRule#BEFORE_KIRI}时，会增加新的宝牌
     */
    public void optionBeforeTehaiAction() {
        TehaiActionType tehaiActionType = Optional.ofNullable(gameCore.getLastTehaiAction()) //
                .map(TehaiActionEvent::getActionType) //
                .orElse(null);
        RiverActionType riverActionType = Optional.ofNullable(gameCore.getLastRiverAction()) //
                .map(RiverActionEvent::getRiverType) //
                .orElse(null);
        if (config.newDoraAction(DoraAddRule.BEFORE_KIRI, tehaiActionType, riverActionType)) {
            nextDoraDisplayOnActionKan();
        }
    }

    /**
     * 在手牌动作之后的一个执行时点，特别的规则设置会在这里执行。如宝牌增加规则设置为{@link DoraAddRule#AFTER_KIRI}
     */
    public void optionAfterTehaiAction() {
        // 不同规则下的动作可选执行点
        // 玩家打出一枚牌后才会增加宝牌的规则
        TehaiActionType tehaiActionType = Optional.ofNullable(gameCore.getLastTehaiAction()) //
                .map(TehaiActionEvent::getActionType) //
                .orElse(null);
        RiverActionType riverActionType = Optional.ofNullable(gameCore.getLastRiverAction()) //
                .map(RiverActionEvent::getRiverType) //
                .orElse(null);
        if (config.newDoraAction(DoraAddRule.AFTER_KIRI, tehaiActionType, riverActionType)) {
            nextDoraDisplayOnActionKan();
        }
    }

    /**
     * 在牌河动作之后的一个执行时点，在这里会处理立直放置立直棒的流程，当规则设置为宝牌在切牌之后时，也会在此时执行
     */
    public void optionAfterRiverAction() {
        // 立直步骤2处理
        reach2();
    }

    /**
     * 玩家立直时，点数扣减，增加点棒
     */
    protected void reach2() {
        TehaiActionEvent lastTehaiAction = gameCore.getLastTehaiAction();
        if (lastTehaiAction.getActionType() == TehaiActionType.REACH) {
            int[] increaseAndDecrease = gameCore.getPlayerPoints();
            increaseAndDecrease[lastTehaiAction.getPosition()] -= 1000;
            gameCore.reach2(increaseAndDecrease);
        }
    }

    /**
     * 增加新宝牌
     */
    private void nextDoraDisplayOnActionKan() {
        // 玩家操作暗杠或者加杠，或者对手进行大明杠，会增加宝牌
        if (gameCore.getLastTehaiAction() == null) {
            return;
        }
        if (ActionEvents.needAddDraw(gameCore.getLastTehaiAction()) //
                || gameCore.getLastRiverAction() != null && gameCore.getLastRiverAction()
                .getRiverType() == RiverActionType.MINKAN) {
            gameCore.nextDoraDisplay();
        }
    }

    // --------------------------------------------
    // 私有方法
    // --------------------------------------------


    /**
     * 游戏的运行状态
     *
     * @author terra.lian
     */
    protected enum GameRunningState {
        /**
         * 游戏继续
         */
        CONTINUE,

        /**
         * 游戏结束
         */
        END,
    }
}
