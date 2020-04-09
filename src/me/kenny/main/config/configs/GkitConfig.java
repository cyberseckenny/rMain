package me.kenny.main.config.configs;

import me.kenny.main.Main;
import me.kenny.main.gkit.Gkit;
import me.kenny.main.gui.Gui;
import me.kenny.main.util.DoubleValue;
import me.kenny.main.util.EditingPlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GkitConfig extends ItemConfig {
    public GkitConfig(Main main) {
        // null because we have no editing gui
        super(main, "gkits", null, null);
    }

    public void setGkit(Gkit gkit) {
        String path = gkit.getName();
        getFileConfiguration().set(path + ".displayName", gkit.getDisplayName());
        getFileConfiguration().set(path + ".material", gkit.getMaterial().toString());
        getFileConfiguration().set(path + ".description", gkit.getDescription());

        if (gkit.getArmor() != null) {
            getFileConfiguration().set(path + ".armor", null);
            for (ItemStack armor : gkit.getArmor()) {
                addItem(armor, false, path + ".armor");
            }
        }

        if (gkit.getItems() != null) {
            getFileConfiguration().set(path + ".items", null);
            for (ItemStack item : gkit.getItems()) {
                addItem(item, false, path + ".items");
            }
        }
    }

    public List<Gkit> getGkits() {
        List<Gkit> gkits = new ArrayList<>();
        for (String key : getFileConfiguration().getKeys(false)) {
            int armorSize = getFileConfiguration().getConfigurationSection(key + ".armor").getValues(false).size();
            int itemsSize = getFileConfiguration().getConfigurationSection(key + ".items").getValues(false).size();
            ItemStack[] armor = new ItemStack[armorSize];
            ItemStack[] items = new ItemStack[itemsSize];

            for (int i = 0; i < armorSize; i++) {
                ItemStack item = getItem(key + ".armor." + (i + 1));
                armor[i] = item;
            }

            for (int i = 0; i < itemsSize; i++) {
                ItemStack item = getItem(key + ".items." + (i + 1));
                items[i] = item;
            }

            String displayName = getFileConfiguration().getString(key + ".displayName");
            String description = getFileConfiguration().getString(key + ".description");
            Material material = Material.valueOf(getFileConfiguration().getString(key + ".material"));
            gkits.add(new Gkit(key, displayName, description, material, items, armor));
        }
        return gkits;
    }
}
