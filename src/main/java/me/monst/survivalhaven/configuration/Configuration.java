package me.monst.survivalhaven.configuration;

import me.monst.pluginutil.configuration.ConfigurationBranch;
import me.monst.pluginutil.configuration.YamlFile;
import me.monst.survivalhaven.SurvivalHavenPlugin;
import me.monst.survivalhaven.configuration.values.EnableDebugLog;

public class Configuration extends ConfigurationBranch {
    
    public final EnableDebugLog enableDebugLog;
    
    public Configuration(SurvivalHavenPlugin plugin) {
        super("config.yml");
        
        this.enableDebugLog = addChild(new EnableDebugLog(plugin));
        
        this.file = new YamlFile(plugin, getKey()); // Create config.yml file
        reload();
    }
    
    private final YamlFile file;
    public void reload() {
        feed(file.load());
        file.save(getAsYaml());
    }
    
}
