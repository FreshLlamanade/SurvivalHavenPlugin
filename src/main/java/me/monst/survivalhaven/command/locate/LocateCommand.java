package me.monst.survivalhaven.command.locate;

import me.monst.pluginutil.command.*;
import me.monst.survivalhaven.command.Permissions;
import me.monst.survivalhaven.particle.ParticleService;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LocateCommand implements TopLevelDelegator {
    
    private final Map<String, Executable> subCommands;
    
    public LocateCommand(Plugin plugin, ParticleService particleService) {
        this.subCommands = Stream.of(
                new LocateCoords(particleService),
                new LocateDeath(particleService),
                new LocateHere(particleService),
                new LocateHome(particleService),
                new LocatePlayer(plugin, particleService),
                new LocateStop(particleService)
        ).collect(Collectors.toMap(Executable::getName, subCommand -> subCommand));
    }
    
    @Override
    public String getName() {
        return "locate";
    }
    
    @Override
    public String getDescription() {
        return "Create a particle trail which guides you to a specified location.";
    }
    
    @Override
    public String getUsage() {
        return "/locate <target>";
    }
    
    @Override
    public Permission getPermission() {
        return Permissions.LOCATE;
    }
    
    @Override
    public Map<String, Executable> getSubCommands() {
        return subCommands;
    }
    
}
