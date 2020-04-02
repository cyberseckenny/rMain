package me.kenny.main.config.configs;

import me.kenny.main.Main;
import me.kenny.main.config.Config;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.*;

public class LootConfig extends Config {
    public LootConfig(Main main) {
        super(main, "loot");
    }

    public Integer addLoot(ItemStack item, boolean rare) {
        int nextKey = getFirstAvailableKey();
        getFileConfiguration().set(Integer.valueOf(nextKey).toString() + ".item", item.serialize());
        getFileConfiguration().set(Integer.valueOf(nextKey).toString() + ".rare", rare);
        save();

        return nextKey;
    }

    public Integer removeLoot(ItemStack item) {
        for (String path : getFileConfiguration().getKeys(false)) {
            ItemStack configItem = ItemStack.deserialize(getFileConfiguration().getConfigurationSection(path + ".item").getValues(true));
            if (configItem.isSimilar(item)) {
                getFileConfiguration().set(path, null);
                save();
                return Integer.parseInt(path);
            }
        }
        return -1;
    }

    // gets the first available key in the config
    public Integer getFirstAvailableKey() {
        int lastKey = 0;
        Set<String> paths = getFileConfiguration().getKeys(false);

        if (getFileConfiguration().getKeys(false).isEmpty())
            return 1;

        int lastValue = Integer.parseInt((String) paths.toArray()[paths.size() - 1]);
        for (int i = 1; i < lastValue + 1; i++) {
            if (!getFileConfiguration().contains(String.valueOf(i))) {
                return i;
            }
            lastKey = i;
        }
        return lastKey + 1;
    }

    public Map<ItemStack, Integer> getLoot() {
        Map<ItemStack, Integer> loot = new HashMap<ItemStack, Integer>();

        // loading the file configuration again prevents null pointer
        try {
            getFileConfiguration().load(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }

        for (String path : getFileConfiguration().getKeys(false)) {
            FileConfiguration configuration = getFileConfiguration();
            ConfigurationSection configurationSection = getFileConfiguration().getConfigurationSection(path + ".item");
            Map<String, Object> section = getFileConfiguration().getConfigurationSection(path + ".item").getValues(false);
            ItemStack item = ItemStack.deserialize(section);
            loot.put(item, Integer.parseInt(path));
        }
        return loot;
    }

    public boolean hasIdenticalItem(ItemStack item) {
        for (Map.Entry<ItemStack, Integer> loot : getLoot().entrySet()) {
            if (loot.getKey().isSimilar(item)) {
                return true;
            }
        }
        return false;
    }
}
