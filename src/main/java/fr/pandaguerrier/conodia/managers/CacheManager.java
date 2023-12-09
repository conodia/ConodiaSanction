package fr.pandaguerrier.conodia.managers;

import fr.pandaguerrier.conodia.managers.cache.BanCache;
import fr.pandaguerrier.conodia.managers.cache.BanIpCache;
import fr.pandaguerrier.conodia.managers.cache.MuteCache;
import fr.pandaguerrier.conodiagameapi.ConodiaGameAPI;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CacheManager {
    private static BanCache banCache = new BanCache();
    private static MuteCache muteCache = new MuteCache();
    private static BanIpCache banIpCache = new BanIpCache();

    public static BanCache getBanCache() {
        return banCache;
    }
    public static MuteCache getMuteCache() {
        return muteCache;
    }
    public static BanIpCache getBanIpCache() {
        return banIpCache;
    }

    public static void sync() {
        JSONObject response = ConodiaGameAPI.getInstance().getApiManager().get("/sanctions", new JSONObject());

        for (Object resp : (JSONArray) response.get("sanctions")) {
            JSONObject payload = (JSONObject) resp;

            switch (payload == null ? "" : (String) payload.get("type")) {
                case "ban":
                    BanManager banManager = BanManager.from(payload);
                    banCache.add(banManager);
                    break;
                case "mute":
                    MuteManager muteManager = MuteManager.from(payload);
                    muteCache.add(muteManager);
                    break;
                case "ip_ban":
                    BanIpManager banIpManager = BanIpManager.from(payload);
                    banIpCache.add(banIpManager);
                    break;
            }
        }
    }
}
