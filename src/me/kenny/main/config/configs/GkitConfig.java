package me.kenny.main.config.configs;

import me.kenny.main.Main;
import me.kenny.main.gkit.Gkit;
import me.kenny.main.gui.Gui;
import me.kenny.main.util.EditingPlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GkitConfig extends ItemConfig{
    public GkitConfig(Main main) {
        // null because we have no editing gui
        super(main, "gkits", null, null);
    }

    public boolean addGkit(Gkit gkit) {
        String path = gkit.getName();
        if (getFileConfiguration().getConfigurationSection(path) == null) {
            getFileConfiguration().set(path + ".displayName", gkit.getDisplayName());

            for (ItemStack armor : gkit.getArmor()) {
                addItem(armor, false, path + ".armor");
            }

            for (ItemStack item : gkit.getItems()) {
                addItem(item, false, path + ".items");
            }

            return true;
        } else {
            return false;
        }
    }

    public List<Gkit> getGkits() {
        List<Gkit> gkits = new ArrayList<>();
        for (String key : getFileConfiguration().getKeys(false)) {
            Set<ItemStack> armorList = getItems(key + ".armor").keySet();
            Set<ItemStack> itemList = getItems(key + ".items").keySet();

            ItemStack[] armor = new ItemStack[armorList.size()];
            ItemStack[] items = new ItemStack[itemList.size()];
            armor = armorList.toArray(armor);
            items = itemList.toArray(items);

            String displayName = getFileConfiguration().getString(key + ".displayName");
            gkits.add(new Gkit(key, displayName, armor, items));
        }
        return gkits;
    }
}
