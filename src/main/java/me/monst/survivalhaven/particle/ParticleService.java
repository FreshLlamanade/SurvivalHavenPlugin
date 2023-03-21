package me.monst.survivalhaven.particle;

import me.monst.survivalhaven.SurvivalHavenPlugin;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Supplier;

public class ParticleService {
    
    private final SurvivalHavenPlugin plugin;
    private final Map<UUID, List<ParticleGuide>> playerGuideMap;
    private final Map<UUID, BreadcrumbsTrail> playerBreadcrumbsMap;
    
    public ParticleService(SurvivalHavenPlugin plugin) {
        this.plugin = plugin;
        this.playerGuideMap = new HashMap<>();
        this.playerBreadcrumbsMap = new HashMap<>();
    }
    
    public BreadcrumbsTrail getBreadcrumbs(Player player) {
        return playerBreadcrumbsMap.get(player.getUniqueId());
    }
    
    public void addBreadcrumbs(Player player) {
        addBreadcrumbs(player, ParticleColors.random());
    }
    
    public void addBreadcrumbs(Player player, Color color) {
        removeBreadcrumbs(player);
        BreadcrumbsTrail breadcrumbs = new BreadcrumbsTrail(plugin, player, color);
        playerBreadcrumbsMap.put(player.getUniqueId(), breadcrumbs);
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, breadcrumbs);
    }
    
    public void removeBreadcrumbs(Player player) {
        BreadcrumbsTrail breadcrumbs = playerBreadcrumbsMap.remove(player.getUniqueId());
        if (breadcrumbs != null)
            breadcrumbs.stop();
    }
    
    public void addGuide(Player player, Location target) {
        addGuide(player, new FixedLocationParticleGuide(plugin, player, target.clone(), ParticleColors.random()));
    }
    
    public void addGuide(Player player, Location target, Color color) {
        addGuide(player, new FixedLocationParticleGuide(plugin, player, target.clone(), color));
    }
    
    public void addGuide(Player player, Player target) {
        Supplier<Location> targetLocationSupplier = () -> target.isOnline() ? target.getLocation() : null;
        addGuide(player, new MovingTargetParticleGuide(plugin, player, targetLocationSupplier, ParticleColors.random()));
    }
    
    public void addGuide(Player player, Player target, Color color) {
        Supplier<Location> targetLocationSupplier = () -> target.isOnline() ? target.getLocation() : null;
        addGuide(player, new MovingTargetParticleGuide(plugin, player, targetLocationSupplier, color));
    }
    
    private void addGuide(Player player, ParticleGuide guide) {
        List<ParticleGuide> guides = playerGuideMap.computeIfAbsent(player.getUniqueId(), k -> new LinkedList<>());
        guides.removeIf(ParticleGuide::isStopped);
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, guide);
        guides.add(guide);
    }
    
    public void removeGuides(Player player) {
        List<ParticleGuide> guides = playerGuideMap.remove(player.getUniqueId());
        if (guides != null)
            guides.forEach(ParticleGuide::stop);
    }
    
    public void removeGuides() {
        playerGuideMap.values().forEach(guides -> guides.forEach(ParticleGuide::stop));
        playerGuideMap.clear();
    }
    
}
