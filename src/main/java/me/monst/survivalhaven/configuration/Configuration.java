package me.monst.survivalhaven.configuration;

import me.monst.pluginutil.configuration.ConfigurationBranch;
import me.monst.pluginutil.configuration.YamlFile;
import org.bukkit.plugin.Plugin;

public class Configuration extends ConfigurationBranch {
    
    public Configuration(Plugin plugin) {
        super("config.yml");
        
        this.file = new YamlFile(plugin, getKey());
        reload();
    }
    
    private final YamlFile file;
    public void reload() {
//        feed(file.load());
    }
    
}
