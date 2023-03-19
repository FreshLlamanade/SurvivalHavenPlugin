package me.monst.survivalhaven.configuration;

import me.monst.pluginutil.configuration.ConfigurationBranch;
import me.monst.pluginutil.configuration.YamlFile;
import me.monst.survivalhaven.configuration.values.MaxBreadcrumbs;
import org.bukkit.plugin.Plugin;

public class Configuration extends ConfigurationBranch {
    
    public final MaxBreadcrumbs maxBreadcrumbs;
    
    public Configuration(Plugin plugin) {
        super("config.yml");
        this.maxBreadcrumbs = addChild(new MaxBreadcrumbs());
        
        this.file = new YamlFile(plugin, getKey());
    }
    
    private final YamlFile file;
    public void reload() {
        feed(file.load());
    }
    
}
