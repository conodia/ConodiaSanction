package fr.pandaguerrier.conodia.utils;


import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class CheckUtils {
    public static boolean isPlayerExist(String name) {
        return isPlayerOnline(name) || isPlayerOffline(name);
    }

    public static boolean isPlayerOnline(String name) {
        try {
            Player player = Bukkit.getPlayerExact(name);
            return true;
        } catch(Exception ignored) {}
        return false;
    }

    public static boolean isPlayerOffline(String name) {
        try {
            OfflinePlayer player = Bukkit.getOfflinePlayer(name);
            return true;
        } catch(Exception ignored) {}
        return false;
    }

    public static String getPlayerName(String name) {
        if (isPlayerExist(name)) {
            if (isPlayerOnline(name)) {
                Bukkit.getPlayer(name);
            } else if (isPlayerOffline(name)) {
                Bukkit.getOfflinePlayer(name);
            }
        }
        return Bukkit.getOfflinePlayer(name).getName();
    }

    public static Player getPlayerByName(String name) {
        if (isPlayerExist(name)) {
            if (isPlayerOnline(name)) {
                Bukkit.getPlayer(name);
            } else if (isPlayerOffline(name)) {
                Bukkit.getOfflinePlayer(name);
            }
        }
        return Bukkit.getOfflinePlayer(name).getPlayer();
    }

    public static boolean isInteger(String str){
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception ignored){}
        return false;
    }

    public static boolean isDouble(String str){
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception ignored){}
        return false;
    }

    public static boolean isLong(String str){
        try {
            long l = Long.parseLong(str);
            return true;
        } catch (Exception ignored){}
        return false;
    }

}
