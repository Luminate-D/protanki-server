package ua.lann.protankiserver.game.battles.impl;

import ua.lann.protankiserver.game.battles.BattleBase;
import ua.lann.protankiserver.game.battles.BattlePlayer;
import ua.lann.protankiserver.game.battles.models.Map;
import ua.lann.protankiserver.enums.BattleFormat;
import ua.lann.protankiserver.enums.BattleSuspictionLevel;
import ua.lann.protankiserver.models.battle.BattleDisplayInfo;
import ua.lann.protankiserver.models.battle.BattleListInfo;
import ua.lann.protankiserver.models.battle.BattleSettings;
import ua.lann.protankiserver.models.battle.ProBattleSettings;

import java.util.ArrayList;
import java.util.List;

public class DeathMatchBattle extends BattleBase {
    private final List<BattlePlayer> players;

    public DeathMatchBattle(String id, String name, Map map, BattleSettings settings, BattleSuspictionLevel suspictionLevel, boolean isProBattle, ProBattleSettings proBattleSettings) {
        super(id, name, map, settings, suspictionLevel, false, isProBattle, proBattleSettings);
        players = new ArrayList<>();
    }

    @Override
    public BattleListInfo getBattleListInfo() {
        return new BattleListInfo(
                name,
                id,
                map.getMapId(),
                map.getPreviewResourceId(),
                settings.getMode(),
                settings.getMaxPeople(),
                settings.getMinRank().getNumber(),
                settings.getMaxRank().getNumber(),
                false,
                proBattleSettings.isPrivate(),
                isProBattle,
                proBattleSettings.getFormat().equals(BattleFormat.Parkour),
                players.stream().map(x -> x.controller().getPlayer().getNickname()).toList(), null, null,
                suspictionLevel
        );
    }

    @Override
    public BattleDisplayInfo getBattleDisplayInfo() {
        return new BattleDisplayInfo(
                name,
                id,
                map.getMapId(),
                map.getPreviewResourceId(),
                settings.getMode(),
                settings.getMaxPeople(),
                settings.getScoreLimit(),
                settings.getMinRank().getNumber(),
                settings.getMaxRank().getNumber(),
                settings.getTimeLimitInSeconds(),
                settings.getTimeLimitInSeconds(),
                !proBattleSettings.getFormat().equals(BattleFormat.Parkour) || !proBattleSettings.getFormat().equals(BattleFormat.None),
                proBattleSettings.isPrivate(),
                isProBattle,
                proBattleSettings.getFormat().equals(BattleFormat.Parkour),
                suspictionLevel,
                proBattleSettings.isRearmorring(),
                isRoundStarted,
                proBattleSettings.isSupplyDropDisabled(),
                proBattleSettings.isSuppliesDisabled(),
                proBattleSettings.isCrystalDropDisabled(),
                players.stream().map(x -> x.controller().getPlayer().getNickname()).toList(),
                null, null, 0, 0,
                false, false,
                false, false, 250
        );
    }
}
