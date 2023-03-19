package me.monst.survivalhaven.command.locate;

import me.monst.pluginutil.command.Args;
import me.monst.pluginutil.command.PlayerExecutable;
import me.monst.survivalhaven.particle.ParticleService;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

class LocateHere implements PlayerExecutable {
    
    private final ParticleService particleService;
    
    LocateHere(ParticleService particleService) {
        this.particleService = particleService;
    }
    
    @Override
    public String getName() {
        return "here";
    }
    
    @Override
    public String getDescription() {
        return "Leads you to your current location.";
    }
    
    @Override
    public String getUsage() {
        return "/locate here";
    }
    
    @Override
    public void execute(Player player, Args args) {
        player.sendMessage(ChatColor.YELLOW + "Locating your current location...");
        particleService.addGuide(player, player.getLocation());
    }
    
}
