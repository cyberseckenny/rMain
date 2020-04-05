package me.kenny.main.crate;

import me.kenny.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class CrateListener implements Listener {
    private Main main;

    public CrateListener(Main main) {
        this.main = main;
    }

    public void onPlayerJoin(PlayerJoinEvent event) {

    }
}
