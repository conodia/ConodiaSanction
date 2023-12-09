package fr.pandaguerrier.conodia.commands;

import fr.pandaguerrier.conodia.constant.Messages;
import fr.pandaguerrier.conodia.constant.PermissionList;
import fr.pandaguerrier.conodia.constant.SanctionType;
import fr.pandaguerrier.conodia.managers.BanIpManager;
import fr.pandaguerrier.conodia.managers.CacheManager;
import fr.pandaguerrier.conodia.utils.CheckUtils;
import fr.pandaguerrier.conodia.utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.List;

public class UnbanIpCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(PermissionList.BAN_PERM)) {
            sender.sendMessage("§cVous n'avez pas accès à cette commande.");
            return false;
        }
        if(args.length == 1) {
            if(CheckUtils.isPlayerExist(args[0])){
                BanIpManager banManager = CacheManager.getBanIpCache().getBanByName(args[0]);
                if(banManager != null) {
                    banManager.undo();
                    Messages.sendBroadcastUnSanctionned(args[0], sender.getName(), SanctionType.UNBAN_IP);
                    sender.sendMessage("§aLe joueur §f" + args[0] + " §aa été débanni.");
                } else {
                    sender.sendMessage("§cLe joueur §f" + args[0] + " §cn'est pas banni.");
                }
            } else {
                sender.sendMessage("§cLe joueur §f" + args[0] + " §cest introuvable.");
            }

            return true;
        }
        sender.sendMessage(StringUtils.prefix + "§c/debanip <Joueur>");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}