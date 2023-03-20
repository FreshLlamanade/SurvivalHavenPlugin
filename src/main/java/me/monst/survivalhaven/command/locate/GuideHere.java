package me.monst.survivalhaven.command.locate;

import me.monst.pluginutil.command.CommandExecutionException;
import me.monst.pluginutil.command.PlayerExecutable;
import me.monst.survivalhaven.particle.ParticleColors;
import me.monst.survivalhaven.particle.ParticleService;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

class GuideHere implements PlayerExecutable {
    
    private final ParticleService particleService;
    
    GuideHere(ParticleService particleService) {
        this.particleService = particleService;
    }
    
    @Override
    public String getName() {
        return "here";
    }
    
    @Override
    public String getDescription() {
        return "Guides you back to your current location.";
    }
    
    @Override
    public String getUsage() {
        return "/guide here [color]";
    }
    
    @Override
    public void execute(Player player, List<String> args) throws CommandExecutionException {
        player.sendMessage(ChatColor.YELLOW + "Guiding you to your current location...");
        if (args.isEmpty())
            particleService.addGuide(player, player.getLocation());
        else
            particleService.addGuide(player, player.getLocation(), ParticleColors.get(args.get(0)));
    }
    
    @Override
    public List<String> getTabCompletions(Player player, List<String> args) {
        return ParticleColors.search(args.get(0));
    }
    
}
