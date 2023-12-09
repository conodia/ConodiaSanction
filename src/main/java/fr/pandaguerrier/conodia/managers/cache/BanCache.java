package fr.pandaguerrier.conodia.managers.cache;

import fr.pandaguerrier.conodia.managers.BanManager;

import java.util.ArrayList;
import java.util.List;

public class BanCache {
    private final List<BanManager> banCache = new ArrayList<>();

    public void add(BanManager banManager) {
        banCache.add(banManager);
    }

    public void remove(BanManager banManager) {
        banCache.remove(banManager);
    }

    public BanManager getBan(String playerName) {
        for (BanManager banManager : banCache) {
            if (banManager.getTargetName().equalsIgnoreCase(playerName) && banManager.isActive() && (banManager.getEndTime() >= System.currentTimeMillis() / 1000 || banManager.isPermanent())) {
                return banManager;
            }
        }
        return null;
    }

    public List<BanManager> getBans() {
        return banCache;
    }
}
