package me.kenny.main.crate;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Crate {
    private Location location;
    private String text;
    private Hologram hologram;

    public Crate(Location location, String text) {
        this.location = location;
        this.text = text;
        this.hologram = new Hologram(text, location.add(0, 0.5, 0));
    }

    public String getText() {
        return text;
    }

    public Location getLocation() {
        return location;
    }

    public Hologram getHologram() {
        return hologram;
    }
}
