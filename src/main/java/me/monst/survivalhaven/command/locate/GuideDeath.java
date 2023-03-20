package me.monst.survivalhaven.command.locate;

import me.monst.pluginutil.command.CommandExecutionException;
import me.monst.pluginutil.command.Permission;
import me.monst.pluginutil.command.PlayerExecutable;
import me.monst.survivalhaven.command.Permissions;
import me.monst.survivalhaven.particle.ParticleColors;
import me.monst.survivalhaven.particle.ParticleService;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

class GuideDeath implements PlayerExecutable {
    
    private final ParticleService particleService;
    
    GuideDeath(ParticleService particleService) {
        this.particleService = particleService;
    }
    
    @Override
    public String getName() {
        return "death";
    }
    
    @Override
    public String getDescription() {
        return "Guides you to the location where you last died.";
    }
    
    @Override
    public String getUsage() {
        return "/guide death [color]";
    }
    
    @Override
    public Permission getPermission() {
        return Permissions.GUIDE_DEATH;
    }
    
    @Override
    public void execute(Player player, List<String> args) throws CommandExecutionException {
        Location lastDeath = player.getLastDeathLocation();
        if (lastDeath == null || lastDeath.getWorld() == null)
            throw new CommandExecutionException("No death location found.");
        if (!Objects.equals(lastDeath.getWorld(), player.getWorld()))
            throw new CommandExecutionException("Death location is in world '" + lastDeath.getWorld().getName() + "'.");
    
        player.sendMessage(ChatColor.YELLOW + "Guiding you to your last death...");
        if (args.isEmpty())
            particleService.addGuide(player, lastDeath);
        else
            particleService.addGuide(player, lastDeath, ParticleColors.get(args.get(0)));
    }
    
    @Override
    public List<String> getTabCompletions(Player player, List<String> args) {
        return ParticleColors.search(args.get(0));
    }
    
}
