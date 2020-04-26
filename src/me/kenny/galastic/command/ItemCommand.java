package me.kenny.galastic.command;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ItemCommand implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        org.bukkit.inventory.ItemStack item = player.getInventory().getItemInHand();
        if (event.getMessage().contains("[item]")) {
            if (item != null &&
                   item.getType() != Material.AIR) {
                ItemStack nms = CraftItemStack.asNMSCopy(item);
                NBTTagCompound tag = new NBTTagCompound();
                nms.save(tag);

                String name = item.getItemMeta().getDisplayName() != null ? item.getItemMeta().getDisplayName() : item.getType().toString();
                TextComponent component = new TextComponent(String.format(event.getFormat(), player.getDisplayName(), event.getMessage().replace("[item]", name)));
                component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ComponentBuilder(tag.toString()).create()));

                event.setCancelled(true);
                for (Player recipent : event.getRecipients()) {
                    recipent.spigot().sendMessage(component);
                }
            }
        }
    }
}
