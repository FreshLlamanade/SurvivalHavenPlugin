package me.monst.survivalhaven.particle;

import me.monst.survivalhaven.SurvivalHavenPlugin;
import me.monst.survivalhaven.configuration.values.Guides;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.Objects;

/**
 * A particle guide is a recurring visual effect only visible to one player, for as long as they have it enabled.
 * It shows itself once every 1500ms, creating a trail of redstone dust particles leading away from the player
 * in the direction of a certain target. The target can be fixed or moving, and may disappear (for instance, if the
 * target is a player and enters a different dimension).
 */
abstract class ParticleGuide implements Runnable {
    
    private final Plugin plugin;
    protected final Guides guides;
    protected final Player player;
    private final Particle.DustOptions dustOptions;
    
    private boolean stopped;
    
    ParticleGuide(SurvivalHavenPlugin plugin, Player player, Color color) {
        this.plugin = plugin;
        this.guides = plugin.config().guides;
        this.player = player;
        this.dustOptions = new Particle.DustOptions(color, 1);
    }
    
    @Override
    public void run() {
        stopped = false;
        while (!stopped && player.isOnline()) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, this::show);
            sleep(guides.repeatDelay.get());
        }
        stopped = true;
    }
    
    abstract void show();
    
    static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }
    
    public void stop() {
        stopped = true;
    }
    
    public boolean isStopped() {
        return stopped;
    }
    
    void spawnParticle(Location location) {
        player.spawnParticle(Particle.REDSTONE, location, guides.guideDensity.get(), dustOptions);
    }
    
    void highlight(Location location) {
        player.spawnParticle(Particle.REDSTONE, location, guides.highlightDensity.get(), 1, 1, 1, dustOptions);
    }
    
    Location getPlayerLocation() {
        return player.getEyeLocation().subtract(0, 1, 0);
    }
    
    static boolean differentWorlds(World world1, World world2) {
        return !Objects.equals(world1, world2);
    }
    
    static Vector getVectorBetween(Location from, Location to) {
        return to.toVector().subtract(from.toVector());
    }

}
