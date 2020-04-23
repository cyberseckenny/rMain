package me.kenny.main.config.configs;

import me.kenny.main.Main;
import me.kenny.main.gui.LootTableGui;
import me.kenny.main.util.DoubleValue;
import me.kenny.main.util.EditingPlayerHandler;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class LootConfig extends ItemConfig {
    public LootConfig(Main main) {
        super(main, "loot", EditingPlayerHandler.EditingType.LOOT_TABLE_GUI, new LootTableGui(main));
    }
}
