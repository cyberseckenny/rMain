package me.kenny.main.config.configs;

import me.kenny.main.Main;
import me.kenny.main.config.Config;
import me.kenny.main.gui.LootTableGui;
import me.kenny.main.util.DoubleValue;
import me.kenny.main.util.EditingPlayerHandler;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class LootConfig extends GuiConfig {
    public LootConfig(Main main) {
        super(main, "loot", EditingPlayerHandler.EditingType.LOOT_TABLE_GUI, new LootTableGui(main));
    }
}
