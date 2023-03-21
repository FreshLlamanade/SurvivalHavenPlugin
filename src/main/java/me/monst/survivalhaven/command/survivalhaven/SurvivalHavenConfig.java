package me.monst.survivalhaven.command.survivalhaven;

import me.monst.pluginutil.command.Delegator;
import me.monst.pluginutil.command.Executable;
import me.monst.pluginutil.command.Permission;
import me.monst.survivalhaven.command.Permissions;
import me.monst.survivalhaven.configuration.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

class SurvivalHavenConfig implements Delegator {
    
    private final Map<String, Executable> subCommands;
    
    public SurvivalHavenConfig(Configuration config) {
        this.subCommands = new LinkedHashMap<>();
        Stream.of(
                new SurvivalHavenConfigReload(config),
                new SurvivalHavenConfigSet(config),
                new SurvivalHavenConfigSave(config)
        ).forEach(subCommand -> subCommands.put(subCommand.getName(), subCommand));
    }
    
    @Override
    public String getName() {
        return "config";
    }
    
    @Override
    public String getDescription() {
        return "Configure the Survival Haven Minecraft Plugin.";
    }
    
    @Override
    public String getUsage() {
        return "/survivalhaven config <reload|set|save>";
    }
    
    @Override
    public Permission getPermission() {
        return Permissions.ADMIN;
    }
    
    @Override
    public Map<String, Executable> getSubCommands() {
        return subCommands;
    }
    
}
