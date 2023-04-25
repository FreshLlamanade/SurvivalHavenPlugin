package me.monst.survivalhaven.listener;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {
    
    @EventHandler(priority = EventPriority.MONITOR)
    private void coordinatesOnDeath(PlayerDeathEvent e) {
        Block block = e.getEntity().getLocation().getBlock();
        String deathCoords = "You died at " + block.getX() + ", " + block.getY() + ", " + block.getZ()
                + " in '" + block.getWorld().getName() + "'.";
        e.getEntity().sendMessage(deathCoords);
    }
    
}
