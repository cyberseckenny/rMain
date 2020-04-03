package me.kenny.main.config.configs;

import me.kenny.main.Main;
import me.kenny.main.config.Config;
import me.kenny.main.lootbox.LootTableGui;
import me.kenny.main.util.DoubleValue;
import me.kenny.main.util.EditingPlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class LootConfig extends Config {
    public LootConfig(Main main) {
        super(main, "loot");
    }

    public Integer addLoot(ItemStack item, boolean rare) {
        int nextKey = getFirstAvailableKey();
        getFileConfiguration().set(Integer.valueOf(nextKey).toString() + ".item", item.serialize());
        getFileConfiguration().set(Integer.valueOf(nextKey).toString() + ".rare", rare);
        save();

        reorganize();
        updateLootGui();

        return nextKey;
    }

    public Integer removeLoot(ItemStack item) {
        for (String path : getFileConfiguration().getKeys(false)) {
            ItemStack configItem = ItemStack.deserialize(getFileConfiguration().getConfigurationSection(path + ".item").getValues(true));
            if (configItem.isSimilar(item)) {
                updateLootGui();
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

    public Map<ItemStack, DoubleValue> getLoot() {
        Map<ItemStack, DoubleValue> loot = new HashMap<ItemStack, DoubleValue>();
        for (String path : getFileConfiguration().getKeys(false)) {
            FileConfiguration configuration = getFileConfiguration();
            ConfigurationSection configurationSection = getFileConfiguration().getConfigurationSection(path + ".item");
            Map<String, Object> section = getFileConfiguration().getConfigurationSection(path + ".item").getValues(false);
            ItemStack item = ItemStack.deserialize(section);
            boolean rare = getFileConfiguration().getBoolean(path + ".rare");
            loot.put(item, new DoubleValue(Integer.parseInt(path), rare));
        }
        return loot;
    }

    public boolean hasIdenticalItem(ItemStack item) {
        for (Map.Entry<ItemStack, DoubleValue> loot : getLoot().entrySet()) {
            if (loot.getKey().isSimilar(item)) {
                return true;
            }
        }
        return false;
    }

    // reorganizes the config from least to greatest.
    private void reorganize() {
        Map<Integer, Map<String, Object>> sections = new HashMap<>();

        for (String path : getFileConfiguration().getKeys(false)) {
            ConfigurationSection section = getFileConfiguration().getConfigurationSection(path);
            sections.put(Integer.parseInt(path), section.getValues(true));
        }

        sections = sections.entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, Map<String, Object>>comparingByKey())
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

        clearConfig();

        for (Map.Entry<Integer, Map<String, Object>> entry : sections.entrySet()) {
            for (Map.Entry<String, Object> value : entry.getValue().entrySet()) {
                getFileConfiguration().set(entry.getKey() + "." + value.getKey(), value.getValue());
            }
        }

        save();
    }

    private void updateLootGui() {
        for (Player player : main.getEditingPlayerHandler().getEditing(EditingPlayerHandler.EditingType.LOOT_TABLE_GUI)) {
            player.getOpenInventory().getTopInventory().setContents(new LootTableGui(main, player).getGui().getContents());
        }
    }

    private void clearConfig() {
        for (String key : getFileConfiguration().getKeys(false)) {
            getFileConfiguration().set(key, null);
        }
    }
}
