package fr.pandaguerrier.conodia.commands;

import fr.pandaguerrier.conodia.constant.Messages;
import fr.pandaguerrier.conodia.constant.PermissionList;
import fr.pandaguerrier.conodia.constant.SanctionType;
import fr.pandaguerrier.conodia.managers.BanManager;
import fr.pandaguerrier.conodia.utils.CheckUtils;
import fr.pandaguerrier.conodia.utils.DateUtils;
import fr.pandaguerrier.conodia.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.List;

public class TempBanCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission(PermissionList.TEMPBAN_PERM)) {
            if (args.length >= 3) {
                if (CheckUtils.isPlayerExist(args[0])) {
                    if (CheckUtils.isPlayerOnline(args[0]) && Bukkit.getPlayer(args[0]).hasPermission(PermissionList.STAFF_PERM) && !sender.hasPermission(PermissionList.ADMIN_PERM)) {
                        sender.sendMessage("§cVous ne pouvez pas sanctionner ce joueur !");
                        return false;
                    }

                    if (!DateUtils.isValid(args[1])) {
                        sender.sendMessage(StringUtils.prefix + "§cdate pas valide");
                        return true;
                    }

                    StringBuilder reasonString = new StringBuilder();
                    for(int i = 2; i < args.length; i++) { reasonString.append(args[i]).append(" "); }
                    String reason = reasonString.toString();
                    reason = reason.substring(0, reason.length() - 1);

                    long unixTime = DateUtils.unixEndFromFormattedDate(args[1]);
                    String timeLeft = DateUtils.unixEndToDate(unixTime);
                    String targetName = CheckUtils.getPlayerName(args[0]);
                    String authorName = sender.getName();

                    BanManager banManager = new BanManager(targetName, authorName, reason, unixTime, System.currentTimeMillis(), false);
                    banManager.handle();

                    Messages.sendBroadcastSanctionned(args[0], sender.getName(), reason, timeLeft, SanctionType.TEMP_BAN);

                    if (CheckUtils.isPlayerOnline(args[0])) {
                        try {
                            Bukkit.getPlayer(args[0]).kickPlayer("" + Messages.getTempBanMessage(sender.getName(), reason, timeLeft));
                        } catch (Exception ignored) {}
                        return true;
                    }
                }
            } else {
                sender.sendMessage(StringUtils.prefix + "§c/ban <Joueur> <Temps> [Raison]");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }

}
