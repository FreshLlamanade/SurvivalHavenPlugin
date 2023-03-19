package me.monst.survivalhaven.command.locate;

import me.monst.pluginutil.command.Args;
import me.monst.pluginutil.command.CommandException;
import me.monst.pluginutil.command.Permission;
import me.monst.pluginutil.command.PlayerExecutable;
import me.monst.survivalhaven.command.Permissions;
import me.monst.survivalhaven.particle.ParticleService;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Objects;

class LocateDeath implements PlayerExecutable {
    
    private final ParticleService particleService;
    
    LocateDeath(ParticleService particleService) {
        this.particleService = particleService;
    }
    
    @Override
    public String getName() {
        return "death";
    }
    
    @Override
    public String getDescription() {
        return "Leads you to the location where you last died.";
    }
    
    @Override
    public String getUsage() {
        return "/locate death";
    }
    
    @Override
    public Permission getPermission() {
        return Permissions.LOCATE_DEATH;
    }
    
    @Override
    public void execute(Player player, Args args) throws CommandException {
        Location lastDeath = player.getLastDeathLocation();
        if (lastDeath == null || lastDeath.getWorld() == null)
            throw new CommandException("No death location found.");
        if (!Objects.equals(lastDeath.getWorld(), player.getWorld()))
            throw new CommandException("Death location is in world '" + lastDeath.getWorld().getName() + "'.");
    
        player.sendMessage(ChatColor.YELLOW + "Locating your last death...");
        particleService.addGuide(player, lastDeath);
    }
    
}
