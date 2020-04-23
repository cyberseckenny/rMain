package me.kenny.main.listener;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Random;

public class AutoSmelt implements Listener {
    @EventHandler
    public void onMine(BlockBreakEvent event) {
        for (Drop drop : Drop.values()) {
            Block block = event.getBlock();
            if (block.getType().toString().equals(drop.toString())) {
                Player player = event.getPlayer();
                if (player.getGameMode() != GameMode.CREATIVE) {
                    if (player.getInventory().getItemInHand() != null &&
                            player.getInventory().getItemInHand().getType().toString().endsWith("PICKAXE") &&
                            player.getInventory().getItemInHand().getType() != Material.WOOD_PICKAXE) {
                        ItemStack pickaxe = player.getInventory().getItemInHand();

                        if (pickaxe.containsEnchantment(Enchantment.SILK_TOUCH))
                            return;

                        event.setCancelled(true);
                        block.setType(Material.AIR);

                        if (pickaxe.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                            for (Map.Entry<Enchantment, Integer> entry : pickaxe.getEnchantments().entrySet()) {
                                Random r = new Random();
                                int random = r.nextInt(100) + 1;
                                switch (entry.getValue()) {
                                    case 1:
                                        if (random <= 66)
                                            dropOre(block.getLocation(), drop.getNewDrop(), 1);
                                        else
                                            dropOre(block.getLocation(), drop.getNewDrop(), 2);
                                        break;
                                    case 2:
                                        if (random <= 50)
                                            dropOre(block.getLocation(), drop.getNewDrop(), 1);
                                        else if (random <= 75)
                                            dropOre(block.getLocation(), drop.getNewDrop(), 2);
                                        else if (random <= 100)
                                            dropOre(block.getLocation(), drop.getNewDrop(), 3);
                                        break;
                                    case 3:
                                        if (random <= 40)
                                            dropOre(block.getLocation(), drop.getNewDrop(), 1);
                                        else if (random <= 60)
                                            dropOre(block.getLocation(), drop.getNewDrop(), 2);
                                        else if (random <= 80)
                                            dropOre(block.getLocation(), drop.getNewDrop(), 3);
                                        else if (random <= 100)
                                            dropOre(block.getLocation(), drop.getNewDrop(), 4);
                                        break;
                                }
                            }
                        } else {
                            dropOre(block.getLocation(), drop.getNewDrop(), 1);
                        }
                    }
                }
            }
        }
    }

    public void dropOre(Location location, Material ore, int amount) {
        location.getWorld().dropItemNaturally(location, new ItemStack(ore, amount));
    }

    public enum Drop {
        IRON_ORE("IRON_INGOT"),
        GOLD_ORE("GOLD_INGOT");

        private Material newDrop;

        Drop(String newDrop) {
            this.newDrop = Material.valueOf(newDrop);
        }

        public Material getNewDrop() {
            return newDrop;
        }
    }
}
