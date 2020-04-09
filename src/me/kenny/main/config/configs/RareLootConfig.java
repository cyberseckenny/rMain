package me.kenny.main.config.configs;

import me.kenny.main.Main;
import me.kenny.main.config.Config;
import me.kenny.main.gui.RareLootTableGui;
import me.kenny.main.util.EditingPlayerHandler;
import me.kenny.main.util.EditingPlayerHandler.EditingType;

public class RareLootConfig extends ItemConfig {
    public RareLootConfig(Main main) {
        super(main, "rareLoot", EditingType.LOOT_TABLE_GUI, new RareLootTableGui(main));
    }
}
