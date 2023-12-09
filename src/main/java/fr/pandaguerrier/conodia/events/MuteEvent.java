package fr.pandaguerrier.conodia.events;

import fr.pandaguerrier.conodia.constant.Messages;
import fr.pandaguerrier.conodia.managers.CacheManager;
import fr.pandaguerrier.conodia.managers.MuteManager;
import fr.pandaguerrier.conodia.utils.DateUtils;
import fr.pandaguerrier.conodia.utils.StringUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;
import java.util.List;

public class MuteEvent implements Listener {
    private final List<String> commandList = Arrays.asList("tell", "msg", "m", "emsg", "etell", "ereply", "w", "er", "r");

    @EventHandler
    public void muteEvent(AsyncPlayerChatEvent event) {
        String player = event.getPlayer().getName();
        MuteManager muteManager = CacheManager.getMuteCache().getMute(player);
        long currentTimestamp = System.currentTimeMillis() / 1000;
        if (muteManager != null) {
            if (muteManager.isPermanent()) {
                event.getPlayer().sendMessage(Messages.getMutePermMessage(muteManager.getAuthorName(), muteManager.getReason()));
                event.setCancelled(true);
            } else if (muteManager.getEndTime() >= currentTimestamp) {
                String timeLeft = DateUtils.unixEndToDate(muteManager.getEndTime());
                event.getPlayer().sendMessage(Messages.getTempMuteMessage(muteManager.getAuthorName(), muteManager.getReason(), timeLeft));
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void commandChat(PlayerCommandPreprocessEvent event) {
        String player = event.getPlayer().getName();
        MuteManager muteManager = CacheManager.getMuteCache().getMute(player);
        long currentTimestamp = System.currentTimeMillis() / 1000;
        if (muteManager != null && (muteManager.getEndTime() >= currentTimestamp || muteManager.isPermanent())) {
            String cmd = event.getMessage().toLowerCase();
            if (cmd.startsWith("/")) {
                cmd = cmd.substring(1);
            }
            if (cmd.split(" ").length >= 1) {
                cmd = cmd.split(" ")[0];
            }
            if (commandList.contains(cmd)) {
                event.getPlayer().sendMessage(StringUtils.prefix + "§7Tu n'as accès à cette commande quand tu es mute.");
                event.setCancelled(true);
            }
        }
    }

}
