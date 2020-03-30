package me.kenny.main;

import me.kenny.main.command.MainCommand;
import me.kenny.main.config.configs.LootConfig;
import me.kenny.main.lootbox.LootboxOpenListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private String lootboxName;
    private String lootboxLore;

    private LootConfig lootConfig;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        lootboxName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("name"));
        lootboxLore = ChatColor.translateAlternateColorCodes('&', getConfig().getString("lore"));

        lootConfig = new LootConfig(this);

        Bukkit.getPluginManager().registerEvents(new LootboxOpenListener(this), this);

        Bukkit.getPluginCommand("main").setExecutor(new MainCommand(this));
    }

    public String getLootboxName() {
        return lootboxName;
    }

    public String getLootboxLore() {
        return lootboxLore;
    }

    public LootConfig getLootConfig() { return lootConfig; }

    public boolean isLootbox(ItemMeta meta) {
        if (meta != null && meta.getLore() != null && !meta.getLore().isEmpty()) {
            if (meta.getDisplayName().equals(getLootboxName())) {
                if (meta.getLore().get(0).equals(getLootboxLore())) {
                    return true;
                }
            }
        }
        return false;
    }
}
