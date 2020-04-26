package me.kenny.galastic;

import me.kenny.galastic.command.*;
import me.kenny.galastic.config.configs.*;
import me.kenny.galastic.item.SpecialItemHandler;
import me.kenny.galastic.listener.AutoSmelt;
import me.kenny.galastic.lootbox.LootTableListener;
import me.kenny.galastic.lootbox.LootboxListener;
import me.kenny.galastic.lootbox.RareLootTableListener;
import me.kenny.galastic.util.EditingPlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Galastic extends JavaPlugin {
    private String lootboxName;
    private String lootboxLore;

    private LootConfig lootConfig;
    private RareLootConfig rareLootConfig;
    private CooldownConfig cooldownConfig;
    private ShortcutConfig shortcutConfig;
    private PlayerLocationConfig playerLocationConfig;

    private EditingPlayerHandler editingPlayerHandler;
    private SpecialItemHandler specialItemHandler;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        lootConfig = new LootConfig(this);
        rareLootConfig = new RareLootConfig(this);
        cooldownConfig = new CooldownConfig(this);
        shortcutConfig = new ShortcutConfig(this);
        playerLocationConfig = new PlayerLocationConfig(this);
        editingPlayerHandler = new EditingPlayerHandler(this);
        specialItemHandler = new SpecialItemHandler(this);

        setupConfig();

        Bukkit.getPluginManager().registerEvents(new LootboxListener(this), this);
        Bukkit.getPluginManager().registerEvents(new LootTableListener(this), this);
        Bukkit.getPluginManager().registerEvents(new RareLootTableListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ItemCommand(), this);
        Bukkit.getPluginManager().registerEvents(new AutoSmelt(), this);
        Bukkit.getPluginManager().registerEvents(editingPlayerHandler, this);
        Bukkit.getPluginManager().registerEvents(specialItemHandler, this);
        Bukkit.getPluginManager().registerEvents(playerLocationConfig, this);

        Bukkit.getPluginCommand("galastic").setExecutor(new GalasticCommand(this));
        Bukkit.getPluginCommand("tpoffline").setExecutor(new TpOfflineCommand(this));
        Bukkit.getPluginCommand("shortcut").setExecutor(new ShortcutCommand(this));
    }

    public String getLootboxName() {
        return lootboxName;
    }

    public String getLootboxLore() {
        return lootboxLore;
    }

    public LootConfig getLootConfig() { return lootConfig; }

    public RareLootConfig getRareLootConfig() { return rareLootConfig; }

    public CooldownConfig getCooldownConfig() { return cooldownConfig; }

    public PlayerLocationConfig getPlayerLocationConfig() { return playerLocationConfig; }

    public ShortcutConfig getShortcutConfig() { return shortcutConfig; }

    public EditingPlayerHandler getEditingPlayerHandler() { return editingPlayerHandler; }

    public SpecialItemHandler getSpecialItemHandler() { return specialItemHandler; }

    public void setupConfig() {
        this.reloadConfig();
        lootConfig.reload();
        rareLootConfig.reload();
        cooldownConfig.reload();
        playerLocationConfig.reload();
        shortcutConfig.reload();

        lootboxName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("lootbox-name"));
        lootboxLore = ChatColor.translateAlternateColorCodes('&', getConfig().getString("lootbox-lore"));
    }
}
