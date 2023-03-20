package me.monst.survivalhaven.command.locate;

import com.earth2me.essentials.Essentials;
import me.monst.pluginutil.command.*;
import me.monst.survivalhaven.command.Permissions;
import me.monst.survivalhaven.particle.ParticleService;
import org.bukkit.plugin.Plugin;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public class GuideCommand implements TopLevelDelegator {
    
    private final Map<String, Executable> subCommands;
    
    public GuideCommand(Plugin plugin, ParticleService particleService, Essentials essentials) {
        this.subCommands = new LinkedHashMap<>();
        Stream.of(
                new GuideCoords(particleService),
                new GuideDeath(particleService),
                new GuideHere(particleService),
                new GuideHome(particleService, essentials),
                new GuidePlayer(plugin, particleService),
                new GuideStop(particleService)
        ).forEach(subCommand -> subCommands.put(subCommand.getName(), subCommand));
    }
    
    @Override
    public String getName() {
        return "guide";
    }
    
    @Override
    public String getDescription() {
        return "Create a particle trail which guides you to a specified location.";
    }
    
    @Override
    public String getUsage() {
        return "/guide <coords|death|here|home|player|stop>";
    }
    
    @Override
    public Permission getPermission() {
        return Permissions.GUIDE;
    }
    
    @Override
    public Map<String, Executable> getSubCommands() {
        return subCommands;
    }
    
}
