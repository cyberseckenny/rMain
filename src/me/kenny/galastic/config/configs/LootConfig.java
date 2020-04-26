package me.kenny.galastic.config.configs;

import me.kenny.galastic.Galastic;
import me.kenny.galastic.gui.LootTableGui;
import me.kenny.galastic.util.EditingPlayerHandler;

public class LootConfig extends ItemConfig {
    public LootConfig(Galastic galastic) {
        super(galastic, "loot", EditingPlayerHandler.EditingType.LOOT_TABLE_GUI, new LootTableGui(galastic));
    }
}
