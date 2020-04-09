package me.kenny.main.lootbox;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

// stores the 10 items when a player opens a lootbox
public class LootboxStorage {
    private ItemStack[] items;
    private ItemStack rare;
    private int index = 0;

    // the last item must ALWAYS be the rare item
    public LootboxStorage(ItemStack rare, ItemStack... items) {
        this.items = items;
        this.rare = rare;
    }

    public ItemStack getRare() {
        return rare;
    }

    public ItemStack getNextItemStack() {
        int i = index;
        index++;
        return items[index];
    }

    public ItemStack[] getItems() {
        return items;
    }

    public boolean isMaxed() {
        return index > items.length - 2;
    }
}
