package me.monst.survivalhaven.particle;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

/**
 * A particle guide to a specific, unmoving location.
 */
public class FixedLocationParticleGuide extends ParticleGuide {
    
    private final Location target;
    
    FixedLocationParticleGuide(Plugin plugin, Player player, Location target, Color color) {
        super(plugin, player, color);
        this.target = target;
    }
    
    @Override
    void show() {
        // Cannot show guide to a target in a different world
        if (differentWorlds(getPlayerWorld(), target.getWorld())) {
            stop();
            return;
        }
        // Represents the player's initial position, although the player could be moving this will stay the same
        final Location startLocation = getPlayerLocation();
        if (startLocation.distance(target) <= GUIDE_LENGTH) {
            // If the player is within range, make a puff of particles around the target to highlight it
            highlight(target);
            return;
        }
        // Find the direction between the player's initial position and the target
        final Vector direction = getVectorBetween(startLocation, target).normalize();
        for (int blocksAway = 1; blocksAway <= GUIDE_LENGTH; blocksAway++) {
            // Get the distance the player has moved in the direction of the target (could be negative)
            double movedTowardsTarget = getVectorBetween(startLocation, getPlayerLocation()).dot(direction);
            // Make sure the next particle's location is offset by the distance the player moved
            // This ensures that the player will always be able to see the guide, even when moving very fast
            Location nextParticleLocation = startLocation.clone().add(direction.clone().multiply(blocksAway + movedTowardsTarget));
            spawnParticle(nextParticleLocation);
            sleep(TIME_BETWEEN_PARTICLES_MS);
        }
    }
    
}
