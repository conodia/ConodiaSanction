package fr.blixow.conodia.commands;

import fr.blixow.conodia.constant.Messages;
import fr.blixow.conodia.constant.PermissionList;
import fr.blixow.conodia.constant.SanctionType;
import fr.blixow.conodia.managers.BanIpManager;
import fr.blixow.conodia.utils.CheckUtils;
import fr.blixow.conodia.utils.DateUtils;
import fr.blixow.conodia.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class TempBanIpCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission(PermissionList.TEMPBANIP_PERM)) {
            if (args.length >= 2) {
                if (CheckUtils.isPlayerExist(args[0])) {
                    if (CheckUtils.isPlayerOnline(args[0]) && Bukkit.getPlayer(args[0]).hasPermission(PermissionList.STAFF_PERM) && !sender.hasPermission(PermissionList.ADMIN_PERM)) {
                        sender.sendMessage("§cVous ne pouvez pas sanctionner ce joueur !");
                        return false;
                    }
                    if (!DateUtils.isValid(args[1])) {
                        sender.sendMessage(StringUtils.prefix + "§cDate pas valide");
                        return true;
                    }

                    StringBuilder reasonString = new StringBuilder();

                    for (int i = 2; i < args.length; i++) {
                        reasonString.append(args[i]).append(" ");
                    }

                    String reason = reasonString.toString();
                    reason = reason.substring(0, reason.length() - 1);

                    long endTime = DateUtils.unixEndFromFormattedDate(args[1]);
                    String timeLeft = DateUtils.unixEndToDate(endTime);
                    String targetIp = CheckUtils.getPlayerByName(args[0]).getAddress().getAddress().getHostAddress();
                    String authorName = sender.getName();

                    BanIpManager banManager = new BanIpManager(targetIp, args[0], authorName, reason, endTime, System.currentTimeMillis(), false);
                    banManager.handle();

                    Messages.sendBroadcastSanctionned(args[0], sender.getName(), reason, timeLeft, SanctionType.TEMP_BAN_IP);

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.getAddress().getAddress().getHostAddress().equals(targetIp)) {
                            kickPlayer(player, reason, authorName, timeLeft, banManager);
                        }
                    }

                    sender.sendMessage(StringUtils.prefix + "§cLe joueur " + args[0] + " a été banni, qui a comme ip " + targetIp + " pour " + timeLeft + " pour la raison suivante : " + reason);
                }
            } else {
                sender.sendMessage(StringUtils.prefix + "§c/ban <Joueur> <Temps> [Raison]");
            }
        }
        return false;
    }

    private void kickPlayer(Player player, String reason, String author, String timeLeft, BanIpManager banManager) {
            if (banManager.isPermanent()) {
                player.kickPlayer("" + Messages.getBanIpMessage(author, reason));
            } else {
                player.kickPlayer("" + Messages.getTempBanIpMessage(author, reason, timeLeft));
            }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }

}

