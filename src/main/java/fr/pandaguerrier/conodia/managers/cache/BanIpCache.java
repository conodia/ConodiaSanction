package fr.pandaguerrier.conodia.managers.cache;

import fr.pandaguerrier.conodia.managers.BanIpManager;

import java.util.ArrayList;
import java.util.List;

public class BanIpCache {
    private final List<BanIpManager> banCache = new ArrayList<>();

    public void add(BanIpManager banManager) {
        banCache.add(banManager);
    }

    public void remove(BanIpManager banManager) {
        banCache.remove(banManager);
    }

    public BanIpManager getBan(String ip) {
        long currentTimestamp = System.currentTimeMillis() / 1000;
        for (BanIpManager banManager : banCache) {
            if (banManager.getTargetIp().equalsIgnoreCase(ip) && banManager.isActive() && (banManager.getEndTime() >= currentTimestamp || banManager.isPermanent())) {
                return banManager;
            }
        }
        return null;
    }

    public BanIpManager getBanByName(String playerName) {
        long currentTimestamp = System.currentTimeMillis() / 1000;
        for (BanIpManager banManager : banCache) {
            if (banManager.getTargetName().equalsIgnoreCase(playerName) && banManager.isActive() && (banManager.getEndTime() >= currentTimestamp || banManager.isPermanent())) {
                System.out.println("ban found");
                return banManager;
            }
        }
        return null;
    }

    public List<BanIpManager> getBans() {
        return banCache;
    }
}
