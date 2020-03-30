package me.kenny.main.config.configs;

import me.kenny.main.Main;
import me.kenny.main.config.Config;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class LootConfig extends Config {
    public LootConfig(Main main) {
        super(main, "loot");
    }

    public Integer addLoot(ItemStack item) {
        int nextKey = getNextKey();
        getFileConfiguration().set(Integer.valueOf(nextKey).toString(), item.serialize());
        save();

        return nextKey;
    }

    public void removeLoot(ItemStack item) {
        for (String path : getFileConfiguration().getKeys(true)) {
            if (getFileConfiguration().getItemStack(path).equals(item.serialize())) {
                getFileConfiguration().set(path, null);
            }
        }
    }

    // each loot will be stored in increments of 1, and this gets the next available one
    public Integer getNextKey() {
        return getFileConfiguration().getKeys(true).size() + 1;
    }
}
