package me.kenny.main;

import me.kenny.main.lootbox.LootboxCommand;
import me.kenny.main.lootbox.LootboxOpen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private String lootboxName;
    private String lootboxLore;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        lootboxName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("name"));
        lootboxLore = ChatColor.translateAlternateColorCodes('&', getConfig().getString("lore"));

        Bukkit.getPluginManager().registerEvents(new LootboxOpen(this), this);
        Bukkit.getPluginCommand("lootbox").setExecutor(new LootboxCommand(this));
    }

    public String getLootboxName() {
        return lootboxName;
    }

    public String getLootboxLore() {
        return lootboxLore;
    }
}
