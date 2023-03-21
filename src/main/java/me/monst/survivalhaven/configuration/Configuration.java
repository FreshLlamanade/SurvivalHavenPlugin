package me.monst.survivalhaven.configuration;

import me.monst.pluginutil.configuration.ConfigurationBranch;
import me.monst.pluginutil.configuration.YamlFile;
import me.monst.survivalhaven.SurvivalHavenPlugin;
import me.monst.survivalhaven.configuration.values.EnableDebugLog;
import me.monst.survivalhaven.configuration.values.Guides;

public class Configuration extends ConfigurationBranch {
    
    public final Guides guides;
    public final EnableDebugLog enableDebugLog;
    
    public Configuration(SurvivalHavenPlugin plugin) {
        super("config.yml");
        
        this.guides = addChild(new Guides());
        this.enableDebugLog = addChild(new EnableDebugLog(plugin));
        
        this.file = new YamlFile(plugin, getKey()); // Create config.yml file
        reload();
    }
    
    private final YamlFile file;
    public void reload() {
        load();
        save();
    }
    
    public void load() {
        feed(file.load());
    }
    
    public void save() {
        file.save(getAsYaml());
    }
    
}
