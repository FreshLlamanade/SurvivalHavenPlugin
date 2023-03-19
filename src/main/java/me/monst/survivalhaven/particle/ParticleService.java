package me.monst.survivalhaven.particle;

import me.monst.survivalhaven.SurvivalHavenPlugin;
import me.monst.survivalhaven.configuration.values.MaxBreadcrumbs;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class ParticleService {
    
    private final Plugin plugin;
    private final Map<UUID, List<ParticleGuide>> playerGuideMap;
    private final Map<UUID, BreadcrumbsTrail> playerBreadcrumbsMap;
    private final MaxBreadcrumbs maxBreadcrumbs;
    private final Color[] colors = new Color[] {
            Color.LIME,
            Color.RED,
            Color.BLUE,
            Color.BLACK,
            Color.WHITE,
            Color.YELLOW,
            Color.ORANGE,
            Color.PURPLE
    };
    
    public ParticleService(SurvivalHavenPlugin plugin) {
        this.plugin = plugin;
        this.playerGuideMap = new HashMap<>();
        this.playerBreadcrumbsMap = new HashMap<>();
        this.maxBreadcrumbs = plugin.config().maxBreadcrumbs;
    }
    
    public BreadcrumbsTrail getBreadcrumbs(Player player) {
        return playerBreadcrumbsMap.get(player.getUniqueId());
    }
    
    public void addBreadcrumbs(Player player) {
        removeBreadcrumbs(player);
        addGuide(player, color -> new BreadcrumbsTrail(plugin, maxBreadcrumbs, player, color));
    }
    
    public void removeBreadcrumbs(Player player) {
        BreadcrumbsTrail breadcrumbs = playerBreadcrumbsMap.remove(player.getUniqueId());
        if (breadcrumbs != null)
            breadcrumbs.stop();
    }
    
    public void addGuide(Player player, Location target) {
        addGuide(player, color -> new FixedLocationParticleGuide(plugin, player, target.clone(), color));
    }
    
    public void addGuide(Player player, Player target) {
        Supplier<Location> targetLocationSupplier = () -> target.isOnline() ? target.getLocation() : null;
        addGuide(player, color -> new MovingTargetParticleGuide(plugin, player, targetLocationSupplier, color));
    }
    
    private void addGuide(Player player, Function<Color, ParticleGuide> guideSupplier) {
        List<ParticleGuide> guides = playerGuideMap.computeIfAbsent(player.getUniqueId(), k -> new LinkedList<>());
        guides.removeIf(ParticleGuide::isStopped);
        ParticleGuide guide = guideSupplier.apply(colors[guides.size() % colors.length]);
        if (guide instanceof BreadcrumbsTrail)
            playerBreadcrumbsMap.put(player.getUniqueId(), (BreadcrumbsTrail) guide);
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
