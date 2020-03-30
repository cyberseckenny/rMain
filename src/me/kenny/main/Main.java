package me.kenny.main;

import me.kenny.main.lootbox.LootboxCommand;
import me.kenny.main.lootbox.LootboxLootConstructor;
import me.kenny.main.lootbox.LootboxOpenListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private String lootboxName;
    private String lootboxLore;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        lootboxName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("name"));
        lootboxLore = ChatColor.translateAlternateColorCodes('&', getConfig().getString("lore"));

        Bukkit.getPluginManager().registerEvents(new LootboxOpenListener(this), this);

        Bukkit.getPluginCommand("lootbox").setExecutor(new LootboxCommand(this));
    }

    public String getLootboxName() {
        return lootboxName;
    }

    public String getLootboxLore() {
        return lootboxLore;
    }

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
