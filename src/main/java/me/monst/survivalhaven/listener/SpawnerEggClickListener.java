package me.monst.survivalhaven.listener;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class SpawnerEggClickListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerClickSpawnerWithEgg(PlayerInteractEvent e) {
        if (e.getItem() == null)
            return;
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE)
            return;
        if (e.getClickedBlock() == null || e.getClickedBlock().getType() != Material.SPAWNER)
            return;
        if (!e.getItem().getType().toString().endsWith("_SPAWN_EGG"))
            return;
        e.setCancelled(true);
    }

}
