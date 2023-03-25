package me.monst.survivalhaven;

import me.monst.pluginutil.command.CommandRegisterService;
import me.monst.pluginutil.log.Debuggable;
import me.monst.pluginutil.log.PluginLogger;
import me.monst.survivalhaven.command.survivalhaven.SurvivalHavenCommand;
import me.monst.survivalhaven.listener.DeathListener;
import me.monst.survivalhaven.listener.SlabBreakListener;
import org.bukkit.plugin.java.JavaPlugin;

public class SurvivalHavenPlugin extends JavaPlugin implements Debuggable {
    
    private final PluginLogger logger = PluginLogger.of(this);
    
    @Override
    public void onEnable() {
        new CommandRegisterService(this).register(
                new SurvivalHavenCommand()
        );
        registerListeners();
    }
    
    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new SlabBreakListener(), this);
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
    }
    
    @Override
    public PluginLogger log() {
        return logger;
    }
    
}
