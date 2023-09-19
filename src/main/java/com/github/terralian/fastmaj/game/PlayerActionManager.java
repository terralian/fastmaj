package com.github.terralian.fastmaj.game;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

import com.github.terralian.fastmaj.game.action.river.ChiiAction;
import com.github.terralian.fastmaj.game.action.river.IRiverAction;
import com.github.terralian.fastmaj.game.action.river.MinKanAction;
import com.github.terralian.fastmaj.game.action.river.PonAction;
import com.github.terralian.fastmaj.game.action.river.RiverActionType;
import com.github.terralian.fastmaj.game.action.river.RonAction;
import com.github.terralian.fastmaj.game.action.tehai.AnnkanAction;
import com.github.terralian.fastmaj.game.action.tehai.ITehaiAction;
import com.github.terralian.fastmaj.game.action.tehai.KakanAction;
import com.github.terralian.fastmaj.game.action.tehai.KiriAction;
import com.github.terralian.fastmaj.game.action.tehai.KitaAction;
import com.github.terralian.fastmaj.game.action.tehai.ReachAction;
import com.github.terralian.fastmaj.game.action.tehai.Ryuukyoku99Action;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.game.action.tehai.TsumoAction;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;
import com.github.terralian.fastmaj.game.validator.river.ChiiValidator;
import com.github.terralian.fastmaj.game.validator.river.IRiverActionValidator;
import com.github.terralian.fastmaj.game.validator.river.MinKanValidator;
import com.github.terralian.fastmaj.game.validator.river.PonValidator;
import com.github.terralian.fastmaj.game.validator.river.RonValidator;
import com.github.terralian.fastmaj.game.validator.tehai.AnnkanValidator;
import com.github.terralian.fastmaj.game.validator.tehai.ITehaiActionValidator;
import com.github.terralian.fastmaj.game.validator.tehai.KakanValidator;
import com.github.terralian.fastmaj.game.validator.tehai.KiriValidator;
import com.github.terralian.fastmaj.game.validator.tehai.KitaValidator;
import com.github.terralian.fastmaj.game.validator.tehai.ReachValidator;
import com.github.terralian.fastmaj.game.validator.tehai.Ryuukyoku99Validator;
import com.github.terralian.fastmaj.game.validator.tehai.TsumoValidator;

/**
 * 默认的玩家动作管理器实现，使用{@link EnumMap}进行存储
 *
 * @author terra.lian
 */
public class PlayerActionManager implements IPlayerActionManager {

    private EnumMap<TehaiActionType, ITehaiAction> tehaiActionMap;
    private EnumMap<TehaiActionType, ITehaiActionValidator> tehaiActionValidatorMap;

    private EnumMap<RiverActionType, IRiverAction> riverActionMap;
    private EnumMap<RiverActionType, IRiverActionValidator> riverActionValidatorMap;

    /**
     * 初始化默认的玩家动作管理器
     */
    public static PlayerActionManager defaultManager(GameComponent gameComponent) {
        PlayerActionManager actionManager = new PlayerActionManager();
        actionManager.initialTehaiAction(gameComponent);
        actionManager.initialRiverAction(gameComponent);
        return actionManager;
    }

    @Override
    public ITehaiAction getTehaiAction(TehaiActionType actionType) {
        return tehaiActionMap.get(actionType);
    }

    @Override
    public ITehaiActionValidator getTehaiActionValidator(TehaiActionType actionType) {
        return tehaiActionValidatorMap.get(actionType);
    }

    @Override
    public Set<TehaiActionType> validateTehaiActions(int position, GameConfig gameConfig, IGameCore gameCore) {
        Set<TehaiActionType> enables = new HashSet<>();
        for (ITehaiActionValidator validator : tehaiActionValidatorMap.values()) {
            if (validator.resolveAction(position, gameConfig, gameCore)) {
                enables.add(validator.getTehaiActionType());
            }
        }
        return enables;
    }

    @Override
    public boolean validateTehaiAction(TehaiActionType actionType, int position, GameConfig gameConfig,
            IGameCore gameCore) {
        ITehaiActionValidator validator = tehaiActionValidatorMap.get(actionType);
        if (validator == null) {
            throw new IllegalArgumentException("动作未启用");
        }
        return validator.resolveAction(position, gameConfig, gameCore);
    }

    @Override
    public IRiverAction getRiverAction(RiverActionType actionType) {
        return riverActionMap.get(actionType);
    }

    @Override
    public IRiverActionValidator getRiverActionValidator(RiverActionType actionType) {
        return riverActionValidatorMap.get(actionType);
    }

    @Override
    public Set<RiverActionType> validateRiverActions(int position, TehaiActionEvent rivalTehaiAction,
            GameConfig gameConfig,
            IGameCore gameCore, PlayerGameContext context) {
        Set<RiverActionType> enables = new HashSet<>();
        for (IRiverActionValidator validator : riverActionValidatorMap.values()) {
            if (validator.resolveAction(position, rivalTehaiAction, gameConfig, gameCore, context)) {
                enables.add(validator.getRiverActionType());
            }
        }
        return enables;
    }

    @Override
    public boolean validateRiverAction(RiverActionType actionType, int position, TehaiActionEvent rivalTehaiAction,
            GameConfig gameConfig,
            IGameCore gameCore, PlayerGameContext context) {
        IRiverActionValidator validator = riverActionValidatorMap.get(actionType);
        if (validator == null) {
            throw new IllegalArgumentException("动作未启用");
        }
        return validator.resolveAction(position, rivalTehaiAction, gameConfig, gameCore, context);
    }

    @Override
    public void remove(TehaiActionType actionType) {
        tehaiActionMap.remove(actionType);
        tehaiActionValidatorMap.remove(actionType);
    }

    @Override
    public void remove(RiverActionType actionType) {
        riverActionMap.remove(actionType);
        riverActionValidatorMap.remove(actionType);
    }

    /**
     * 初始化手牌动作（及判定器）
     */
    private void initialTehaiAction(GameComponent gameComponent) {
        tehaiActionMap = new EnumMap<>(TehaiActionType.class);
        tehaiActionMap.put(TehaiActionType.ANNKAN, new AnnkanAction());
        tehaiActionMap.put(TehaiActionType.KAKAN, new KakanAction());
        tehaiActionMap.put(TehaiActionType.KIRI, new KiriAction());
        tehaiActionMap.put(TehaiActionType.KITA, new KitaAction());
        tehaiActionMap.put(TehaiActionType.REACH, new ReachAction());
        tehaiActionMap.put(TehaiActionType.RYUUKYOKU99, new Ryuukyoku99Action());
        tehaiActionMap.put(TehaiActionType.TSUMO, new TsumoAction(gameComponent.getAgariCalculator()));

        tehaiActionValidatorMap = new EnumMap<>(TehaiActionType.class);
        tehaiActionValidatorMap.put(TehaiActionType.ANNKAN,
                new AnnkanValidator(gameComponent.getYuukouhaiCalculator()));
        tehaiActionValidatorMap.put(TehaiActionType.KAKAN, new KakanValidator());
        tehaiActionValidatorMap.put(TehaiActionType.KIRI, new KiriValidator());
        tehaiActionValidatorMap.put(TehaiActionType.KITA, new KitaValidator());
        tehaiActionValidatorMap.put(TehaiActionType.REACH, new ReachValidator());
        tehaiActionValidatorMap.put(TehaiActionType.RYUUKYOKU99, new Ryuukyoku99Validator());
        tehaiActionValidatorMap.put(TehaiActionType.TSUMO, new TsumoValidator());
    }

    /**
     * 初始化手牌动作（及判定器）
     */
    private void initialRiverAction(GameComponent gameComponent) {
        riverActionMap = new EnumMap<>(RiverActionType.class);
        riverActionMap.put(RiverActionType.CHII, new ChiiAction());
        riverActionMap.put(RiverActionType.MINKAN, new MinKanAction());
        riverActionMap.put(RiverActionType.PON, new PonAction());
        riverActionMap.put(RiverActionType.RON, new RonAction(gameComponent.getAgariCalculator()));

        riverActionValidatorMap = new EnumMap<>(RiverActionType.class);
        riverActionValidatorMap.put(RiverActionType.CHII, new ChiiValidator());
        riverActionValidatorMap.put(RiverActionType.MINKAN, new MinKanValidator());
        riverActionValidatorMap.put(RiverActionType.PON, new PonValidator());
        riverActionValidatorMap.put(RiverActionType.RON, new RonValidator(gameComponent.getSyatenCalculator(), gameComponent.getRonYakuMatcher()));
    }
}
