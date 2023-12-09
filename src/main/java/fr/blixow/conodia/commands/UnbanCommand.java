package fr.blixow.conodia.commands;

import fr.blixow.conodia.constant.Messages;
import fr.blixow.conodia.constant.PermissionList;
import fr.blixow.conodia.constant.SanctionType;
import fr.blixow.conodia.managers.BanManager;
import fr.blixow.conodia.managers.CacheManager;
import fr.blixow.conodia.utils.CheckUtils;
import fr.blixow.conodia.utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.List;

public class UnbanCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission(PermissionList.BAN_PERM)) {
            sender.sendMessage("§cVous n'avez pas accès à cette commande.");
            return false;
        }
        if(args.length == 1) {
            if(CheckUtils.isPlayerExist(args[0])){
                String targetName = CheckUtils.getPlayerName(args[0]);
                BanManager banManager = CacheManager.getBanCache().getBan(targetName);
                if(banManager != null) {
                    banManager.undo();
                    Messages.sendBroadcastUnSanctionned(targetName, sender.getName(), SanctionType.UNBAN);
                    sender.sendMessage("§aLe joueur §f" + args[0] + " §aa été débanni.");
                } else {
                    sender.sendMessage("§cLe joueur §f" + args[0] + " §cn'est pas banni.");
                }
            } else {
                sender.sendMessage("§cLe joueur §f" + args[0] + " §cest introuvable.");
            }

            return true;
        }
        sender.sendMessage(StringUtils.prefix + "§c/deban <Joueur>");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}