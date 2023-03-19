package me.monst.survivalhaven;

import me.monst.pluginutil.command.CommandRegisterService;
import me.monst.survivalhaven.command.survivalhaven.SurvivalHavenCommand;
import me.monst.survivalhaven.configuration.Configuration;
import me.monst.survivalhaven.particle.BreadcrumbsListener;
import me.monst.survivalhaven.particle.ParticleService;
import me.monst.survivalhaven.command.breadcrumbs.BreadcrumbsCommand;
import me.monst.survivalhaven.command.locate.LocateCommand;
import me.monst.survivalhaven.slab.SlabBreakListener;
import org.bukkit.plugin.java.JavaPlugin;

public class SurvivalHavenPlugin extends JavaPlugin {
    
    private Configuration configuration;
    private ParticleService particleService;
    
    @Override
    public void onLoad() {
        this.configuration = new Configuration(this);
    }
    
    @Override
    public void onEnable() {
        this.particleService = new ParticleService(this);
        new CommandRegisterService(this).register(
                new LocateCommand(this, particleService),
                new BreadcrumbsCommand(particleService),
                new SurvivalHavenCommand(this)
        );
        registerListeners();
    }
    
    @Override
    public void onDisable() {
        particleService.removeGuides(); // Halt asynchronous tasks
    }
    
    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new SlabBreakListener(), this);
        getServer().getPluginManager().registerEvents(new BreadcrumbsListener(particleService), this);
    }
    
    public void reload() {
        configuration.reload();
    }
    
    public Configuration config() {
        return configuration;
    }
    
}
