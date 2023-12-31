package ua.lann.protankiserver.game.battles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import ua.lann.protankiserver.game.battles.models.Map;
import ua.lann.protankiserver.enums.BattleSuspictionLevel;
import ua.lann.protankiserver.models.battle.BattleDisplayInfo;
import ua.lann.protankiserver.models.battle.BattleListInfo;
import ua.lann.protankiserver.models.battle.BattleSettings;
import ua.lann.protankiserver.models.battle.ProBattleSettings;

@Getter
@AllArgsConstructor
public abstract class BattleBase {
    protected final String id;
    protected final String name;
    protected final Map map;
    protected final BattleSettings settings;
    protected BattleSuspictionLevel suspictionLevel;

    @NonNull protected boolean isRoundStarted;

    protected final boolean isProBattle;
    protected final ProBattleSettings proBattleSettings;

    public abstract BattleListInfo getBattleListInfo();
    public abstract BattleDisplayInfo getBattleDisplayInfo();
}
