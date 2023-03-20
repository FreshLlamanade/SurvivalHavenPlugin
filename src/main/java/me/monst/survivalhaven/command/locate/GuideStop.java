package me.monst.survivalhaven.command.locate;

import me.monst.pluginutil.command.PlayerExecutable;
import me.monst.survivalhaven.particle.ParticleService;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

class GuideStop implements PlayerExecutable {
    
    private final ParticleService particleService;
    
    GuideStop(ParticleService particleService) {
        this.particleService = particleService;
    }
    
    @Override
    public String getName() {
        return "stop";
    }
    
    @Override
    public String getDescription() {
        return "Stops all particle guides.";
    }
    
    @Override
    public String getUsage() {
        return "/guide stop";
    }
    
    @Override
    public void execute(Player player, List<String> args) {
        player.sendMessage(ChatColor.YELLOW + "Guides cleared.");
        particleService.removeGuides(player);
    }
    
}
