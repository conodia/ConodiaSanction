package fr.blixow.conodia.commands;

import fr.blixow.conodia.constant.Messages;
import fr.blixow.conodia.constant.PermissionList;
import fr.blixow.conodia.constant.SanctionType;
import fr.blixow.conodia.utils.CheckUtils;
import fr.blixow.conodia.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.List;

public class KickCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission(PermissionList.KICK_PERM)) {
            if (args.length >= 2) {
                if (CheckUtils.isPlayerExist(args[0])) {
                    if (CheckUtils.isPlayerOnline(args[0]) && Bukkit.getPlayer(args[0]).hasPermission(PermissionList.STAFF_PERM) && !sender.hasPermission(PermissionList.ADMIN_PERM)) {
                        sender.sendMessage("§cVous ne pouvez pas sanctionner ce joueur !");
                        return false;
                    }

                    StringBuilder reasonString = new StringBuilder();
                    for(int i = 1; i < args.length; i++) { reasonString.append(args[i]).append(" "); }
                    String reason = reasonString.toString();
                    reason = reason.substring(0, reason.length() - 1);

                    Bukkit.getPlayer(args[0]).kickPlayer(Messages.getKickMessage(sender.getName(), reason));
                    Messages.sendBroadcastSanctionned(args[0], sender.getName(), reason, "X", SanctionType.KICK);
                    return true;
                }
            } else {
                sender.sendMessage(StringUtils.prefix + "§c/kick <Joueur> [Raison]");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
