package me.kenny.main.config.configs;

import me.kenny.main.Main;
import me.kenny.main.config.Config;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ShortcutConfig extends Config {
    public ShortcutConfig(Main main) {
        super(main, "shortcuts");
    }

    public void setShortcut(String name, String command) {
        getFileConfiguration().set(name, command);
        save();
    }

    public boolean doShortcut(Player player, String name) {
        if (getFileConfiguration().getString(name) != null) {
            player.chat(getFileConfiguration().getString(name));
        } else {
            return false;
        }
        return true;
    }

    public boolean removeShortcut(String name) {
        if (getFileConfiguration().getString(name) != null) {
            getFileConfiguration().set(name, null);
        } else {
            return false;
        }
        return true;
    }

    public Map<String, String> getShortcuts() {
        Map<String, String> shortcuts = new HashMap<>();
        for (String key : getFileConfiguration().getKeys(false)) {
            shortcuts.put(key, getFileConfiguration().getString(key));
        }
        return shortcuts;
    }
}
