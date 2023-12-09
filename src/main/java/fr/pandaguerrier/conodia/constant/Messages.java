package fr.pandaguerrier.conodia.constant;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Messages {
    public static String getTempBanMessage(String author, String reason, String leftTime) {
        return "§8§m--------------------------------\n\n§c" +
                "§7Vous êtes §cBANNI§7 !\n" +
                "\n§7Par: §c" + author +
                "\n§7Raison: §c" + reason +
                "\n§7Durée: §c" + leftTime +
                "\n\n§8§m--------------------------------";
    }

    public static String getBanMessage(String author, String reason) {
        return "§8§m--------------------------------\n\n§c" +
                "§7Vous êtes §cBANNI À VIE§7 !\n" +
                "\n§7Par: §c" + author +
                "\n§7Raison: §c" + reason +
                "\n§7Durée: §cÀ vie !" +
                "\n\n§8§m--------------------------------";
    }

    public static String getBanIpMessage(String author, String reason) {
        return "§8§m--------------------------------\n\n§c" +
                "§7Vous êtes §cBANNI IP À VIE§7 !\n" +
                "\n§7Par: §c" + author +
                "\n§7Raison: §c" + reason +
                "\n§7Durée: §cÀ vie !" +
                "\n\n§8§m--------------------------------";
    }

    public static String getTempBanIpMessage(String author, String reason, String leftTime) {
        return "§8§m--------------------------------\n\n§c" +
                "§7Vous êtes §cBANNI IP§7 !\n" +
                "\n§7Par: §c" + author +
                "\n§7Raison: §c" + reason +
                "\n§7Durée: §c" + leftTime +
                "\n\n§8§m--------------------------------";
    }


    public static String getMutePermMessage(String author, String reason) {
        return "§8§m--------------------------------\n\n§c" +
                "§7Vous êtes §cMUTE À VIE§7 !\n" +
                "\n§7Par: §c" + author +
                "\n§7Raison: §c" + reason +
                "\n§7Durée: §cÀ vie !" +
                "\n\n§8§m--------------------------------";
    }

    public static String getTempMuteMessage(String author, String reason, String leftTime) {
        return "§8§m--------------------------------\n\n§c" +
                "§7Vous êtes §cMUTE§7 !\n" +
                "\n§7Par: §c" + author +
                "\n§7Raison: §c" + reason +
                "\n§7Durée: §c" + leftTime +
                "\n\n§8§m--------------------------------";
    }

    public static String getKickMessage(String author, String reason) {
        return "§8§m--------------------------------\n\n§c" +
                "§7Vous avez été §cKICK§7 !\n" +
                "\n§7Par: §c" + author +
                "\n§7Raison: §c" + reason +
                "\n\n§8§m--------------------------------";
    }

    public static void sendBroadcastSanctionned(String playername, String author, String reason, String leftTime, SanctionType type) {
        String message = "§8§m--------------------------------\n\n§c" +
                "§7Le joueur §c" + playername + " §7a été §c" + type.getValue() + "§7 !\n" +
                "\n§7Raison: §c" + reason +
                "\n§7Par: §c" + author +
                "\n§7Durée: §c" + leftTime +
                "\n\n§8§m--------------------------------";

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission(PermissionList.STAFF_PERM)) player.sendMessage(message);
        }
    }

    public static void sendBroadcastUnSanctionned(String playername, String author, SanctionType type) {
        String message = "§8§m--------------------------------\n\n§c" +
                "§7Le joueur §c" + playername + " §7a été §c" + type.getValue() + "§7 !\n" +
                "\n§7Par: §c" + author +
                "\n\n§8§m--------------------------------";

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission(PermissionList.STAFF_PERM)) player.sendMessage(message);
        }
    }
}


