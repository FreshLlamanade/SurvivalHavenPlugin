package me.monst.survivalhaven;

import com.earth2me.essentials.Essentials;
import me.monst.pluginutil.command.CommandRegisterService;
import me.monst.pluginutil.log.Debuggable;
import me.monst.pluginutil.log.PluginLogger;
import me.monst.survivalhaven.command.survivalhaven.SurvivalHavenCommand;
import me.monst.survivalhaven.configuration.Configuration;
import me.monst.survivalhaven.listener.DeathListener;
import me.monst.survivalhaven.particle.BreadcrumbsListener;
import me.monst.survivalhaven.particle.ParticleService;
import me.monst.survivalhaven.command.breadcrumbs.BreadcrumbsCommand;
import me.monst.survivalhaven.command.locate.GuideCommand;
import me.monst.survivalhaven.listener.SlabBreakListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class SurvivalHavenPlugin extends JavaPlugin implements Debuggable {
    
    private final PluginLogger logger = PluginLogger.of(this);
    private Configuration configuration;
    private ParticleService particleService;
    private Essentials essentials;
    
    @Override
    public void onLoad() {
        this.configuration = new Configuration(this);
        log().debug("Loaded configuration.");
    }
    
    @Override
    public void onEnable() {
        registerEssentials();
        this.particleService = new ParticleService(this);
        new CommandRegisterService(this).register(
                new GuideCommand(this, particleService, essentials),
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
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
    }
    
    private void registerEssentials() {
        Plugin plugin = getServer().getPluginManager().getPlugin("Essentials");
        if (plugin instanceof Essentials) {
            this.essentials = (Essentials) plugin;
        }
    }
    
    public void reload() {
        configuration.reload();
    }
    
    public Configuration config() {
        return configuration;
    }
    
    @Override
    public PluginLogger log() {
        return logger;
    }
    
}
