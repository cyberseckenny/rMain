package me.kenny.main.lootbox;

import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

// stores the 10 items when a player opens a lootbox
public class LootboxStorage {
    private ItemStack[] items;
    private int index = 0;

    // the last item must ALWAYS be the rare item
    public LootboxStorage(ItemStack... items) {
        this.items = items;
    }

    public ItemStack getRareItemStack() {
        return items[items.length - 1];
    }

    public ItemStack getNextItemStack() {
        int i = index;
        index++;
        return items[index];
    }

    public boolean isMaxed() {
        return index > items.length - 2;
    }
}
