package me.kenny.main.config.configs;

import com.sun.xml.internal.bind.v2.TODO;
import me.kenny.main.Main;
import me.kenny.main.crate.Crate;
import me.kenny.main.gui.CrateGui;
import me.kenny.main.util.EditingPlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class CrateConfig extends ItemConfig {
    public CrateConfig(Main main) {
        super(main, "crates", EditingPlayerHandler.EditingType.LOOT_TABLE_GUI, new CrateGui());
    }

    public boolean addCrate(String crateName, Location location, boolean rare) {
        String path = crateName + ".location";
        if (getFileConfiguration().get(path) != null)
            return false;

        // TODO: no more crates :)))))))))))))))))))))))
        // TODO: do gkits

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        String world = location.getWorld().getName();
        getFileConfiguration().set(crateName + ".location.x", x);
        getFileConfiguration().set(crateName + ".location.y", y);
        getFileConfiguration().set(crateName + ".location.z", z);
        getFileConfiguration().set(crateName + ".location.world", world);

        new Crate(location, crateName);
        save();

        return true;
    }

    public boolean crateHasLocation(Location location) {
        for (String path : getFileConfiguration().getKeys(false)) {
            if (getFileConfiguration().getConfigurationSection(path + ".location") != null) {
                int x = getFileConfiguration().getInt(path + ".location.x");
                int y = getFileConfiguration().getInt(path + ".location.y");
                int z = getFileConfiguration().getInt(path + ".location.z");
                String world = getFileConfiguration().getString(path + ".location.world");
                Location l = new Location(Bukkit.getWorld(world), x, y, z);
                if (location.equals(l))
                    return true;
            }
        }
        return false;
    }
}
