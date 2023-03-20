package me.monst.survivalhaven.command.breadcrumbs;

import me.monst.pluginutil.command.Executable;
import me.monst.pluginutil.command.Permission;
import me.monst.pluginutil.command.PermissionLimit;
import me.monst.pluginutil.command.TopLevelDelegator;
import me.monst.survivalhaven.command.Permissions;
import me.monst.survivalhaven.particle.ParticleService;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BreadcrumbsCommand implements TopLevelDelegator {
    
    private final Map<String, Executable> subCommands;
    
    public BreadcrumbsCommand(ParticleService particleService) {
        this.subCommands = new LinkedHashMap<>();
        Stream.of(
                new BreadcrumbsStart(particleService),
                new BreadcrumbsStop(particleService)
        ).forEach(subCommand -> subCommands.put(subCommand.getName(), subCommand));
    }
    
    @Override
    public String getName() {
        return "breadcrumbs";
    }
    
    @Override
    public String getDescription() {
        return "Turn on/off breadcrumbs.";
    }
    
    @Override
    public String getUsage() {
        return "/breadcrumbs <start|stop>";
    }
    
    @Override
    public Permission getPermission() {
        return PermissionLimit.of(Permissions.BREADCRUMBS.getPerm());
    }
    
    @Override
    public Map<String, Executable> getSubCommands() {
        return subCommands;
    }
    
}
