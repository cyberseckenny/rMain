package me.kenny.main;

import me.kenny.main.command.*;
import me.kenny.main.config.configs.*;
import me.kenny.main.crate.CrateListener;
import me.kenny.main.gkit.GkitMenuGuiListener;
import me.kenny.main.item.SpecialItemHandler;
import me.kenny.main.listener.AutoSmelt;
import me.kenny.main.lootbox.LootTableListener;
import me.kenny.main.lootbox.LootboxListener;
import me.kenny.main.lootbox.RareLootTableListener;
import me.kenny.main.util.EditingPlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class Main extends JavaPlugin {
    private String lootboxName;
    private String lootboxLore;

    private LootConfig lootConfig;
    private RareLootConfig rareLootConfig;
    private CooldownConfig cooldownConfig;
    private CrateConfig crateConfig;
    private GkitConfig gkitConfig;
    private ShortcutConfig shortcutConfig;
    private PlayerLocationConfig playerLocationConfig;

    private EditingPlayerHandler editingPlayerHandler;
    private SpecialItemHandler specialItemHandler;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        this.saveResource("gkits.yml", false);

        lootConfig = new LootConfig(this);
        rareLootConfig = new RareLootConfig(this);
        cooldownConfig = new CooldownConfig(this);
        crateConfig = new CrateConfig(this);
        gkitConfig = new GkitConfig(this);
        shortcutConfig = new ShortcutConfig(this);
        playerLocationConfig = new PlayerLocationConfig(this);
        editingPlayerHandler = new EditingPlayerHandler(this);
        specialItemHandler = new SpecialItemHandler(this);

        setupConfig();

        Bukkit.getPluginManager().registerEvents(new LootboxListener(this), this);
        Bukkit.getPluginManager().registerEvents(new LootTableListener(this), this);
        Bukkit.getPluginManager().registerEvents(new RareLootTableListener(this), this);
        Bukkit.getPluginManager().registerEvents(new CrateListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GkitMenuGuiListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ItemCommand(), this);
        Bukkit.getPluginManager().registerEvents(new AutoSmelt(), this);
        Bukkit.getPluginManager().registerEvents(editingPlayerHandler, this);
        Bukkit.getPluginManager().registerEvents(specialItemHandler, this);
        Bukkit.getPluginManager().registerEvents(playerLocationConfig, this);

        Bukkit.getPluginCommand("main").setExecutor(new MainCommand(this));
        Bukkit.getPluginCommand("gkit").setExecutor(new GkitCommand(this));
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

    public CrateConfig getCrateConfig() { return crateConfig; }

    public GkitConfig getGkitConfig() { return gkitConfig; }

    public PlayerLocationConfig getPlayerLocationConfig() { return playerLocationConfig; }

    public ShortcutConfig getShortcutConfig() { return shortcutConfig; }

    public EditingPlayerHandler getEditingPlayerHandler() { return editingPlayerHandler; }

    public SpecialItemHandler getSpecialItemHandler() { return specialItemHandler; }

    public void setupConfig() {
        this.reloadConfig();
        lootConfig.reload();
        rareLootConfig.reload();
        cooldownConfig.reload();
        crateConfig.reload();
        gkitConfig.reload();
        playerLocationConfig.reload();
        shortcutConfig.reload();

        lootboxName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("lootbox-name"));
        lootboxLore = ChatColor.translateAlternateColorCodes('&', getConfig().getString("lootbox-lore"));
    }
}
