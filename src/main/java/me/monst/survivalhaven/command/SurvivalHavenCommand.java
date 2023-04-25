package me.monst.survivalhaven.command;

import me.monst.pluginutil.command.SimpleCommandDelegator;
import me.monst.survivalhaven.SurvivalHavenPlugin;

public class SurvivalHavenCommand extends SimpleCommandDelegator {
    
    public SurvivalHavenCommand(SurvivalHavenPlugin plugin) {
        super(
                "survivalhaven",
                "Manage the SurvivalHaven plugin.",
                "/survivalhaven <config>",
                new SurvivalHavenConfig(plugin.config())
        );
    }
    
}
