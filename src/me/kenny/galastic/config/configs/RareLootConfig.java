package me.kenny.galastic.config.configs;

import me.kenny.galastic.Galastic;
import me.kenny.galastic.gui.RareLootTableGui;
import me.kenny.galastic.util.EditingPlayerHandler.EditingType;

public class RareLootConfig extends ItemConfig {
    public RareLootConfig(Galastic galastic) {
        super(galastic, "rareLoot", EditingType.LOOT_TABLE_GUI, new RareLootTableGui(galastic));
    }
}
