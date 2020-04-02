package me.kenny.main;

import me.kenny.main.command.MainCommand;
import me.kenny.main.config.configs.LootConfig;
import me.kenny.main.lootbox.LootTableGui;
import me.kenny.main.lootbox.LootTableListener;
import me.kenny.main.lootbox.LootboxListener;
import me.kenny.main.util.EditingPlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private String lootboxName;
    private String lootboxLore;

    private LootConfig lootConfig;
    private EditingPlayerHandler editingPlayerHandler;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        setupConfig();

        lootConfig = new LootConfig(this);
        editingPlayerHandler = new EditingPlayerHandler();

        Bukkit.getPluginManager().registerEvents(new LootboxListener(this), this);
        Bukkit.getPluginManager().registerEvents(new LootTableListener(this), this);
        Bukkit.getPluginManager().registerEvents(editingPlayerHandler, this);

        Bukkit.getPluginCommand("main").setExecutor(new MainCommand(this));
    }

    public String getLootboxName() {
        return lootboxName;
    }

    public String getLootboxLore() {
        return lootboxLore;
    }

    public LootConfig getLootConfig() { return lootConfig; }

    public EditingPlayerHandler getEditingPlayerHandler() { return editingPlayerHandler; }

    public boolean isLootbox(ItemMeta meta) {
        if (meta.getLore() != null && !meta.getLore().isEmpty()) {
            if (meta.getDisplayName().equals(getLootboxName())) {
                if (meta.getLore().get(0).equals(getLootboxLore())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setupConfig() {
        this.reloadConfig();
        lootboxName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("lootbox-name"));
        lootboxLore = ChatColor.translateAlternateColorCodes('&', getConfig().getString("lootbox-lore"));
    }
}
