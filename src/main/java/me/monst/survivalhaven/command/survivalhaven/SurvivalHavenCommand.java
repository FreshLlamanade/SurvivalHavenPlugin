package me.monst.survivalhaven.command.survivalhaven;

import me.monst.pluginutil.command.Executable;
import me.monst.pluginutil.command.TopLevelDelegator;
import me.monst.survivalhaven.SurvivalHavenPlugin;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SurvivalHavenCommand implements TopLevelDelegator {
    
    private final Map<String, Executable> subCommands;
    
    public SurvivalHavenCommand(SurvivalHavenPlugin plugin) {
        this.subCommands = Stream.of(
                new SurvivalHavenReload(plugin),
                new SurvivalHavenGitHub()
        ).collect(Collectors.toMap(Executable::getName, subCommand -> subCommand));
    }
    
    @Override
    public String getName() {
        return "survivalhaven";
    }
    
    @Override
    public String getDescription() {
        return "Use the Survival Haven Minecraft Plugin.";
    }
    
    @Override
    public String getUsage() {
        return "/survivalhaven";
    }
    
    @Override
    public Map<String, Executable> getSubCommands() {
        return null;
    }
    
}
