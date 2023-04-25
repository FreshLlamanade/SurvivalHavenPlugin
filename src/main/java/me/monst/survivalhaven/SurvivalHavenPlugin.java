package me.monst.survivalhaven;

import me.monst.pluginutil.command.CommandRegisterService;
import me.monst.survivalhaven.command.SurvivalHavenCommand;
import me.monst.survivalhaven.configuration.Configuration;
import me.monst.survivalhaven.listener.AutoClickerListener;
import me.monst.survivalhaven.listener.DeathListener;
import me.monst.survivalhaven.listener.SlabBreakListener;
import org.bukkit.plugin.java.JavaPlugin;

public class SurvivalHavenPlugin extends JavaPlugin {
    
    private final Configuration configuration = new Configuration(this);
    
    @Override
    public void onEnable() {
        new CommandRegisterService(this).register(
                new SurvivalHavenCommand(this)
        );
        registerListeners();
    }
    
    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new SlabBreakListener(), this);
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new AutoClickerListener(this), this);
    }
    
    public Configuration config() {
        return configuration;
    }
    
}
