package fr.pandaguerrier.conodia.commands;

import fr.pandaguerrier.conodia.constant.PermissionList;
import fr.pandaguerrier.conodia.utils.CheckUtils;
import fr.pandaguerrier.conodia.utils.DateUtils;
import fr.pandaguerrier.conodia.utils.StringUtils;
import fr.pandaguerrier.conodiagameapi.ConodiaGameAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public class HistoryCommand implements TabExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!sender.hasPermission(PermissionList.STAFF_PERM)) {
      return false;
    }

    if (args.length < 1) {
      sender.sendMessage(StringUtils.prefix + "§c/history <Joueur>");
      return false;
    }

    if (!CheckUtils.isPlayerExist(args[0])) {
      sender.sendMessage(StringUtils.prefix + "§cLe joueur §7" + args[0] + " §cn'existe pas !");
      return false;
    }

    if (CheckUtils.isPlayerOnline(args[0]) && Bukkit.getPlayer(args[0]).hasPermission(PermissionList.STAFF_PERM) && !sender.hasPermission(PermissionList.ADMIN_PERM)) {
      sender.sendMessage("§cVous ne pouvez pas voir l'historique de ce joueur !");
      return false;
    }

    JSONObject response = ConodiaGameAPI.getInstance().getApiManager().get("/sanctions/" + args[0], new JSONObject());
    JSONArray history = (JSONArray) response.get("sanctions");
    if (history == null) {
      sender.sendMessage(StringUtils.prefix + "§cUne erreur est survenue !");
      return false;
    }

    StringBuilder message = new StringBuilder("§7Historique des sanctions de §e" + args[0] + "§7: \n");

    for (Object sanction : history) {
      JSONObject payload = (JSONObject) sanction;
      message.append("§b- §9")
          .append(payload.get("type").toString().toUpperCase())
          .append(" §bpar §9")
          .append(payload.get("author_name"))
          .append(" §bpour §9")
          .append(payload.get("reason"))
          .append(" §ble §9")
          .append(DateUtils.unixToDate(Long.parseLong(payload.get("start_time").toString())))
          .append(" §bpour §9")
          .append(payload.get("end_time") != null ? DateUtils.unixToTimer(Long.parseLong(payload.get("start_time").toString()), Long.parseLong(payload.get("end_time").toString())) : "Permanant")
          .append(" §bFini: §9")
          .append(payload.get("end_time") == null && Boolean.parseBoolean(payload.get("is_permanent").toString()) ? "§cJAMAIS§b, Actif: " + (Boolean.parseBoolean(payload.get("is_active").toString()) ? "§cOui" : "§aNon") : Long.parseLong(payload.get("end_time").toString()) >= System.currentTimeMillis() ? "§cNon" : "§aOui")
          .append("§b.\n");
    }

    sender.sendMessage(message.toString());
    return true;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    // todo
    return null;
  }
}
