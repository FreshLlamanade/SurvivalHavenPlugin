package me.monst.survivalhaven.particle;

import me.monst.survivalhaven.configuration.values.MaxBreadcrumbs;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class BreadcrumbsTrail extends ParticleGuide {

    private final MaxBreadcrumbs maxBreadcrumbs;
    private final List<Breadcrumb> breadcrumbList;
    private final Map<Breadcrumb, Integer> breadcrumbIndexMap;
    
    public BreadcrumbsTrail(Plugin plugin, MaxBreadcrumbs maxBreadcrumbs, Player player, Color color) {
        super(plugin, player, color);
        this.maxBreadcrumbs = maxBreadcrumbs;
        this.breadcrumbList = new ArrayList<>();
        this.breadcrumbIndexMap = new HashMap<>();
        dropBreadcrumb(new Breadcrumb(player.getLocation().getBlock()) {
            @Override
            void spawnParticle() {
                BreadcrumbsTrail.this.highlight(getParticleLocation());
            }
        });
    }
    
    public void stepOnBlock(Block block) {
        Breadcrumb breadcrumb = new Breadcrumb(block);
        if (!breadcrumbIndexMap.containsKey(breadcrumb)) {
            if (maxBreadcrumbs.isAllowedLength(breadcrumbList.size())) {
                dropBreadcrumb(breadcrumb);
            }
        } else {
            int index = breadcrumbIndexMap.get(breadcrumb);
            if (index < breadcrumbList.size() - 1)
                pickUpBreadcrumbs(index + 1);
        }
    }
    
    private void dropBreadcrumb(Breadcrumb breadcrumb) {
        breadcrumbList.add(breadcrumb);
        breadcrumbIndexMap.put(breadcrumb, breadcrumbList.size() - 1);
    }
    
    private void pickUpBreadcrumbs(int index) {
        List<Breadcrumb> cut = breadcrumbList.subList(index, breadcrumbList.size());
        cut.forEach(breadcrumbIndexMap::remove);
        cut.clear();
    }
    
    @Override
    void show() {
        for (int i = breadcrumbList.size() - 1; i >= 0; i--) {
            if (i >= breadcrumbList.size())
                return;
            Breadcrumb breadcrumb = breadcrumbList.get(i);
            if (differentWorlds(getPlayerWorld(), breadcrumb.getWorld()))
                continue;
            breadcrumb.spawnParticle();
            sleep(TIME_BETWEEN_PARTICLES_MS);
        }
    }
    
    private class Breadcrumb {
        
        private static final int SIZE = 3;
        
        private final Block block;
        
        Breadcrumb(Block block) {
            this.block = block;
        }
    
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Breadcrumb)) return false;
            Breadcrumb that = (Breadcrumb) o;
            return this.block.getWorld().equals(that.block.getWorld()) &&
                    this.block.getX() / SIZE == that.block.getX() / SIZE &&
                    this.block.getY() / SIZE == that.block.getY() / SIZE &&
                    this.block.getZ() / SIZE == that.block.getZ() / SIZE;
        }
    
        @Override
        public int hashCode() {
            return Objects.hash(this.block.getWorld(),
                    this.block.getX() / SIZE,
                    this.block.getY() / SIZE,
                    this.block.getZ() / SIZE);
        }
        
        World getWorld() {
            return block.getWorld();
        }
        
        Location getParticleLocation() {
            return block.getLocation().add(0.5, 0.5, 0.5);
        }
        
        void spawnParticle() {
            BreadcrumbsTrail.this.spawnParticle(getParticleLocation());
        }
    
        @Override
        public String toString() {
            return "(" + block.getX() + ", " + block.getY() + ", " + block.getZ() + ")";
        }
    }
    
}
