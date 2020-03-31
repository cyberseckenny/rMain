package me.kenny.main.config.configs;

import me.kenny.main.Main;
import me.kenny.main.config.Config;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class LootConfig extends Config {
    public LootConfig(Main main) {
        super(main, "loot");
    }

    public Integer addLoot(ItemStack item, boolean rare) {
        int nextKey = getNextKey();
        getFileConfiguration().set(Integer.valueOf(nextKey).toString() + ".item", item.serialize());
        getFileConfiguration().set(Integer.valueOf(nextKey).toString() + ".rare", rare);
        save();

        return nextKey;
    }

    public Integer removeLoot(ItemStack item) {
        for (String path : getFileConfiguration().getKeys(true)) {
            if (getFileConfiguration().getItemStack(path + ".item").isSimilar(item)) {
                getFileConfiguration().set(path, null);
                return Integer.parseInt(path);
            }
        }
        return -1;
    }

    // each loot will be stored in increments of 1, and this gets the next available one
    public Integer getNextKey() {
        return getFileConfiguration().getKeys(false).size() + 1;
    }
}
