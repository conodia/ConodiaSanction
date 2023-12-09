package fr.blixow.conodia.events;

import fr.blixow.conodia.constant.Messages;
import fr.blixow.conodia.managers.BanIpManager;
import fr.blixow.conodia.managers.BanManager;
import fr.blixow.conodia.managers.CacheManager;
import fr.blixow.conodia.utils.DateUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class BanEvent implements Listener {

    @EventHandler
    public void banIpLogin(AsyncPlayerPreLoginEvent event) {
        String ip = event.getAddress().getHostAddress();
        BanIpManager banManager = CacheManager.getBanIpCache().getBan(ip);
        long currentTimestamp = System.currentTimeMillis() / 1000;
        if(banManager != null) {
            if (banManager.isPermanent()) {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, Messages.getBanIpMessage(banManager.getAuthorName(), banManager.getReason()));
            } else if(banManager.getEndTime() >= currentTimestamp) {
                String timeLeft = DateUtils.unixEndToDate(banManager.getEndTime());
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, Messages.getTempBanIpMessage(banManager.getAuthorName(), banManager.getReason(), timeLeft));
            }
        }
    }

    @EventHandler
    public void banLogin(AsyncPlayerPreLoginEvent event) {
        String player = event.getName();
        BanManager banManager = CacheManager.getBanCache().getBan(player);
        long currentTimestamp = System.currentTimeMillis() / 1000;
        if(banManager != null) {
            if (banManager.isPermanent()) {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, Messages.getBanMessage(banManager.getAuthorName(), banManager.getReason()));
            } else if(banManager.getEndTime() >= currentTimestamp) {
                String timeLeft = DateUtils.unixEndToDate(banManager.getEndTime());
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, Messages.getTempBanMessage(banManager.getAuthorName(), banManager.getReason(), timeLeft));
            }
        }
    }
}
