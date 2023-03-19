package me.monst.survivalhaven.command.locate;

import me.monst.pluginutil.command.Args;
import me.monst.pluginutil.command.PlayerExecutable;
import me.monst.survivalhaven.particle.ParticleService;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

class LocateStop implements PlayerExecutable {
    
    private final ParticleService particleService;
    
    LocateStop(ParticleService particleService) {
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
        return "/locate stop";
    }
    
    @Override
    public void execute(Player player, Args args) {
        player.sendMessage(ChatColor.YELLOW + "Guides cleared.");
        particleService.removeGuides(player);
    }
    
}
