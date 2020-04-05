package me.kenny.main.config.configs;

import me.kenny.main.Main;
import me.kenny.main.gui.Gui;
import me.kenny.main.util.EditingPlayerHandler;

public class GkitConfig extends ItemConfig{
    public GkitConfig(Main main) {
        // null because we have no editing gui
        super(main, "gkits", null, null);
    }
}
