package me.monst.survivalhaven.command.survivalhaven;

import me.monst.pluginutil.command.Executable;
import me.monst.pluginutil.command.TopLevelDelegator;
import me.monst.survivalhaven.SurvivalHavenPlugin;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public class SurvivalHavenCommand implements TopLevelDelegator {
    
    private final Map<String, Executable> subCommands;
    
    public SurvivalHavenCommand(SurvivalHavenPlugin plugin) {
        this.subCommands = new LinkedHashMap<>();
        Stream.of(
                new SurvivalHavenGitHub(),
                new SurvivalHavenReload(plugin),
                new SurvivalHavenConfig(plugin.config()),
                new SurvivalHavenUpdate()
        ).forEach(subCommand -> subCommands.put(subCommand.getName(), subCommand));
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
        return "/survivalhaven <reload|update|github>";
    }
    
    @Override
    public Map<String, Executable> getSubCommands() {
        return subCommands;
    }
    
}
