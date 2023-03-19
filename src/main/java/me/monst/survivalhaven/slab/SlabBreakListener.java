package me.monst.survivalhaven.slab;

import org.bukkit.FluidCollisionMode;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Slab;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.util.RayTraceResult;

import static org.bukkit.block.data.type.Slab.Type.*;

public class SlabBreakListener implements Listener {
    
    private static final int REACH_DISTANCE = 7;
    
    @EventHandler
    private void onSlabBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (!player.isSneaking())
            return;
        Block block = e.getBlock();
        if (!(block.getBlockData() instanceof Slab slab))
            return;
        if (slab.getType() != DOUBLE)
            return;
        RayTraceResult rayTrace = block.rayTrace(
                player.getEyeLocation(),
                player.getLocation().getDirection(),
                REACH_DISTANCE,
                FluidCollisionMode.NEVER);
        if (rayTrace == null)
            return;
        e.setCancelled(true);
        // This represents how "high up" the block was mined, from 0 (bottom) to 1 (top)
        double mineHeight = rayTrace.getHitPosition().getY() - block.getY();
        boolean topHalfMined = mineHeight >= 0.5;
        if (player.getGameMode() == GameMode.SURVIVAL) {
            // Set the block data to be just that half and then break it
            slab.setType(toSlabType(topHalfMined));
            block.setBlockData(slab);
            block.breakNaturally();
        }
        // Set the block data to be just the slab left over
        slab.setType(toSlabType(!topHalfMined));
        block.setBlockData(slab);
    }
    
    private Slab.Type toSlabType(boolean top) {
        return top ? TOP : BOTTOM;
    }

}
