package ua.lann.protankiserver.game.localization;

import ua.lann.protankiserver.serialization.JsonUtils;

import java.util.HashMap;

public class MapNames {
    private static final HashMap<String, String> mappingRu = new HashMap<>();
    private static final HashMap<String, String> mappingEn = new HashMap<>();

    public static void load() {
        // TODO: one day i'll make this same as GarageItemsLocalization
        load(mappingRu, "localization/ru/mapNames.json");
        load(mappingEn, "localization/en/mapNames.json");
    }

    public static String getName(String mapId, Locale locale) {
         return locale.equals(Locale.English) ? mappingEn.get(mapId) : mappingRu.get(mapId);
    }

    private static void load(HashMap<String, String> destination, String resourceName) {
        MapNamesModel obj = JsonUtils.readResource(resourceName, MapNamesModel.class);
        for(String key : obj.getKeyMap().keySet()) {
            destination.put(key, obj.getKeyMap().get(key));
        }
    }
}
