package me.monst.survivalhaven.listener;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {
    
    @EventHandler(priority = EventPriority.MONITOR)
    private void coordinatesOnDeath(PlayerDeathEvent e) {
        Location loc = e.getEntity().getLocation();
        Component deathMessage = e.deathMessage();
        if (deathMessage == null)
            return;
        Component deathCoords = Component.text(
                "You died at " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ()
                        + " in '" + loc.getWorld().getName() + "'.");
        e.getPlayer().sendMessage(deathCoords);
    }
    
}
