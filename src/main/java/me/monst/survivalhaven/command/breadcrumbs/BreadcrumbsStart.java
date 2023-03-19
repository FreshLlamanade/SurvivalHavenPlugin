package me.monst.survivalhaven.command.breadcrumbs;

import me.monst.pluginutil.command.Args;
import me.monst.pluginutil.command.PlayerExecutable;
import me.monst.survivalhaven.particle.ParticleService;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

class BreadcrumbsStart implements PlayerExecutable {
    
    private final ParticleService particleService;
    
    BreadcrumbsStart(ParticleService particleService) {
        this.particleService = particleService;
    }
    
    @Override
    public String getName() {
        return "start";
    }
    
    @Override
    public String getDescription() {
        return "Leave a trail of particles behind you to guide you back.";
    }
    
    @Override
    public String getUsage() {
        return "/breadcrumbs start";
    }
    
    @Override
    public void execute(Player player, Args args) {
        player.sendMessage(ChatColor.YELLOW + "Starting breadcrumbs...");
        particleService.addBreadcrumbs(player);
    }
    
}
