package me.kenny.main.config.configs;

import me.kenny.main.Main;
import me.kenny.main.config.Config;
import org.bukkit.inventory.ItemStack;

public class LootConfig extends Config {
    public LootConfig(Main main) {
        super(main, "loot");
    }

    public void addLoot(ItemStack item) {
        getFileConfiguration().set(item.getItemMeta().getDisplayName(), item.serialize());
    }

    public void removeLoot(ItemStack item) {
        for (String path : getFileConfiguration().getKeys(true)) {
            if (getFileConfiguration().getItemStack(path).equals(item.serialize())) {
                getFileConfiguration().set(path, null);
            }
        }
    }
}
