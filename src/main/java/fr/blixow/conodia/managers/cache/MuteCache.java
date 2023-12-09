package fr.blixow.conodia.managers.cache;

import fr.blixow.conodia.managers.MuteManager;

import java.util.ArrayList;
import java.util.List;

public class MuteCache {
    private final List<MuteManager> muteCache = new ArrayList<>();

    public void add(MuteManager muteManager) {
        muteCache.add(muteManager);
    }

    public void remove(MuteManager muteManager) {
        muteCache.remove(muteManager);
    }

    public MuteManager getMute(String playerName) {
        for (MuteManager muteManager : muteCache) {
            if (muteManager.getTargetName().equalsIgnoreCase(playerName) && muteManager.isActive() && (muteManager.getEndTime() >= System.currentTimeMillis() / 1000 || muteManager.isPermanent())) {
                return muteManager;
            }
        }
        return null;
    }

    public List<MuteManager> getMutes() {
        return muteCache;
    }
}
