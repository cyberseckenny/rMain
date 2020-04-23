package me.kenny.main.config.configs;

import com.sun.xml.internal.bind.v2.TODO;
import me.kenny.main.Main;
import me.kenny.main.crate.Crate;
import me.kenny.main.gui.CrateGui;
import me.kenny.main.util.DoubleValue;
import me.kenny.main.util.EditingPlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class CrateConfig extends ItemConfig {
    private Map<Player, String> currentCrate = new HashMap<>();

    public CrateConfig(Main main) {
        super(main, "crates", EditingPlayerHandler.EditingType.CRATE_GUI, new CrateGui(main, ""));
    }

    public boolean addCrate(String crateName, Location location, boolean rare) {
        String path = crateName.replace("_", "") + ".location";
        if (getFileConfiguration().get(path) != null)
            return false;

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        String world = location.getWorld().getName();
        getFileConfiguration().set(crateName + ".location.x", x);
        getFileConfiguration().set(crateName + ".location.y", y);
        getFileConfiguration().set(crateName + ".location.z", z);
        getFileConfiguration().set(crateName + ".location.world", world);

        Crate crate = new Crate(location, crateName, null, true);
        save();

        return true;
    }

    public boolean crateHasLocation(Location location) {
        for (Crate crate : getCrates()) {
            if (crate.getLocation().equals(location))
                return true;
        }
        return false;
    }

    public List<Crate> getCrates() {
        List<Crate> crates = new ArrayList<>();
        for (String path : getFileConfiguration().getKeys(false)) {
            if (getFileConfiguration().getConfigurationSection(path + ".location") != null) {
                int x = getFileConfiguration().getInt(path + ".location.x");
                int y = getFileConfiguration().getInt(path + ".location.y");
                int z = getFileConfiguration().getInt(path + ".location.z");
                String world = getFileConfiguration().getString(path + ".location.world");
                Set<ItemStack> items;
                if (getItems(path + ".items") == null)
                    items = new HashSet<>();
                else
                    items = getItems(path + ".items").keySet();
                Location l = new Location(Bukkit.getWorld(world), x, y, z);
                Crate crate = new Crate(l, path, items,false);
                crates.add(crate);
            }
        }
        return crates;
    }

    public Map<Player, String> getCurrentCrate() {
        return currentCrate;
    }
}
