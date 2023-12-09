package fr.blixow.conodia;

import fr.blixow.conodia.commands.*;
import fr.blixow.conodia.events.BanEvent;
import fr.blixow.conodia.events.MuteEvent;
import fr.blixow.conodia.managers.CacheManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ConodiaSanction extends JavaPlugin {
    private static ConodiaSanction conodiaSanction;
    @Override
    public void onEnable() {
        getCommand("mute").setExecutor(new MuteCommand());
        getCommand("unmute").setExecutor(new UnmuteCommand());
        getCommand("tempban").setExecutor(new TempBanCommand());
        getCommand("ban").setExecutor(new BanCommand());
        getCommand("tempbanip").setExecutor(new TempBanIpCommand());
        getCommand("unban").setExecutor(new UnbanCommand());
        getCommand("unbanip").setExecutor(new UnbanIpCommand());
        getCommand("kick").setExecutor(new KickCommand());
        getCommand("history").setExecutor(new HistoryCommand());

        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new MuteEvent(), this);
        manager.registerEvents(new BanEvent(), this);

        CacheManager.sync();

        Bukkit.getLogger().info("§9------------------------------\n\n§bConodiaSanction est activé !\n\n§9------------------------------");
    }

    @Override
    public void onDisable() {}

    @Override
    public void onLoad() {
        conodiaSanction = this;
    }

    public static ConodiaSanction getConodiaSanction() {
        return conodiaSanction;
    }
}