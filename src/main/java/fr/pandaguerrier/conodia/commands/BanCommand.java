package fr.pandaguerrier.conodia.commands;

import fr.pandaguerrier.conodia.constant.Messages;
import fr.pandaguerrier.conodia.constant.PermissionList;
import fr.pandaguerrier.conodia.constant.SanctionType;
import fr.pandaguerrier.conodia.managers.BanManager;
import fr.pandaguerrier.conodia.managers.CacheManager;
import fr.pandaguerrier.conodia.utils.CheckUtils;
import fr.pandaguerrier.conodia.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.List;

public class BanCommand implements TabExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!sender.hasPermission(PermissionList.BAN_PERM)) {
      return false;
    }

    if (args.length < 2) {
      sender.sendMessage(StringUtils.prefix + "§c/ban <Joueur> [Raison]");
      return false;
    }

    if (!CheckUtils.isPlayerExist(args[0])) {
      sender.sendMessage(StringUtils.prefix + "§cLe joueur §7" + args[0] + " §cn'existe pas !");
      return false;
    }

    if (CheckUtils.isPlayerOnline(args[0]) && Bukkit.getPlayer(args[0]).hasPermission(PermissionList.STAFF_PERM) && !sender.hasPermission(PermissionList.ADMIN_PERM)) {
      sender.sendMessage("§cVous ne pouvez pas sanctionner ce joueur !");
      return false;
    }

    if (CacheManager.getBanCache().getBan(args[0]) != null) {
      sender.sendMessage("§cLe joueur §7" + args[0] + " §cest déjà banni !");
      return false;
    }

    StringBuilder reasonString = new StringBuilder();
    for (int i = 1; i < args.length; i++) {
      reasonString.append(args[i]).append(" ");
    }
    String reason = reasonString.toString();
    reason = reason.substring(0, reason.length() - 1);

    String targetName = CheckUtils.getPlayerName(args[0]);
    String authorName = sender.getName();

    BanManager banManager = new BanManager(targetName, authorName, reason, 0, System.currentTimeMillis(), true);
    banManager.handle();

    Messages.sendBroadcastSanctionned(targetName, authorName, reason, "À vie", SanctionType.BAN);

    if (CheckUtils.isPlayerOnline(args[0])) {
      try {
        Bukkit.getPlayer(args[0]).kickPlayer("" + Messages.getTempBanMessage(sender.getName(), reason, "À vie"));
      } catch (Exception ignored) {}
      return true;
    }
    return false;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    // todo
    return null;
  }
}
