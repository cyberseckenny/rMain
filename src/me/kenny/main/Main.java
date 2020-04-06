package me.kenny.main;

import me.kenny.main.command.GkitCommand;
import me.kenny.main.command.ItemCommand;
import me.kenny.main.command.MainCommand;
import me.kenny.main.command.TpOfflineCommand;
import me.kenny.main.config.configs.CooldownConfig;
import me.kenny.main.config.configs.CrateConfig;
import me.kenny.main.config.configs.GkitConfig;
import me.kenny.main.config.configs.LootConfig;
import me.kenny.main.crate.CrateListener;
import me.kenny.main.gkit.GkitMenuGuiListener;
import me.kenny.main.config.configs.PlayerLocationConfig;
import me.kenny.main.item.SpecialItemHandler;
import me.kenny.main.listener.AutoSmelt;
import me.kenny.main.lootbox.LootTableListener;
import me.kenny.main.lootbox.LootboxListener;
import me.kenny.main.util.EditingPlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private String lootboxName;
    private String lootboxLore;

    private LootConfig lootConfig;
    private CooldownConfig cooldownConfig;
    private CrateConfig crateConfig;
    private GkitConfig gkitConfig;
    private PlayerLocationConfig playerLocationConfig;

    private EditingPlayerHandler editingPlayerHandler;
    private SpecialItemHandler specialItemHandler;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        setupConfig();

        lootConfig = new LootConfig(this);
        cooldownConfig = new CooldownConfig(this);
        crateConfig = new CrateConfig(this);
        gkitConfig = new GkitConfig(this);
        playerLocationConfig = new PlayerLocationConfig(this);
        editingPlayerHandler = new EditingPlayerHandler();
        specialItemHandler = new SpecialItemHandler(this);

        Bukkit.getPluginManager().registerEvents(new LootboxListener(this), this);
        Bukkit.getPluginManager().registerEvents(new LootTableListener(this), this);
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
    }

    public String getLootboxName() {
        return lootboxName;
    }

    public String getLootboxLore() {
        return lootboxLore;
    }

    public LootConfig getLootConfig() { return lootConfig; }

    public CooldownConfig getCooldownConfig() { return cooldownConfig; }

    public CrateConfig getCrateConfig() { return crateConfig; }

    public GkitConfig getGkitConfig() { return gkitConfig; }

    public PlayerLocationConfig getPlayerLocationConfig() { return playerLocationConfig; }

    public EditingPlayerHandler getEditingPlayerHandler() { return editingPlayerHandler; }

    public SpecialItemHandler getSpecialItemHandler() { return specialItemHandler; }

    public void setupConfig() {
        this.reloadConfig();

        lootboxName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("lootbox-name"));
        lootboxLore = ChatColor.translateAlternateColorCodes('&', getConfig().getString("lootbox-lore"));
    }
}
