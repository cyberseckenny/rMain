package me.kenny.main;

import me.kenny.main.listener.LootboxOpen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main main;

    private String lootboxName;
    private String lootboxLore;

    @Override
    public void onEnable() {
        main = this;

        this.saveDefaultConfig();

        lootboxName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("name"));
        lootboxLore = ChatColor.translateAlternateColorCodes('&', getConfig().getString("lore"));

        Bukkit.getPluginManager().registerEvents(new LootboxOpen(), this);
    }

    public static Main getInstance() {
        return main;
    }

    public String getLootboxName() {
        return lootboxName;
    }

    public String getLootboxLore() {
        return lootboxLore;
    }
}
