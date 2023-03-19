package me.monst.survivalhaven.command.locate;

import me.monst.pluginutil.command.*;
import me.monst.survivalhaven.command.Permissions;
import me.monst.survivalhaven.particle.ParticleService;
import org.bukkit.entity.Player;

class LocateHome implements PlayerExecutable {
    
    private final ParticleService particleService;
    
    LocateHome(ParticleService particleService) {
        this.particleService = particleService;
    }
    
    @Override
    public String getName() {
        return "home";
    }
    
    @Override
    public String getDescription() {
        return "Leads you to your specified home.";
    }
    
    @Override
    public String getUsage() {
        return "/locate home <name>";
    }
    
    @Override
    public Permission getPermission() {
        return Permissions.LOCATE_HOME;
    }
    
    @Override
    public void execute(Player player, Args args) throws CommandException {
    
    }
    
}
