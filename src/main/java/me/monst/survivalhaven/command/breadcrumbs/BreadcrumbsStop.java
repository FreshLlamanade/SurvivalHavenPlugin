package me.monst.survivalhaven.command.breadcrumbs;

import me.monst.pluginutil.command.PlayerExecutable;
import me.monst.survivalhaven.particle.ParticleService;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

class BreadcrumbsStop implements PlayerExecutable {
    
    private final ParticleService particleService;
    
    BreadcrumbsStop(ParticleService particleService) {
        this.particleService = particleService;
    }
    
    @Override
    public String getName() {
        return "stop";
    }
    
    @Override
    public String getDescription() {
        return "Stops all breadcrumbs.";
    }
    
    @Override
    public String getUsage() {
        return "/breadcrumbs stop";
    }
    
    @Override
    public void execute(Player player, List<String> args) {
        player.sendMessage(ChatColor.YELLOW + "Breadcrumbs cleared.");
        particleService.removeBreadcrumbs(player);
    }
    
}
