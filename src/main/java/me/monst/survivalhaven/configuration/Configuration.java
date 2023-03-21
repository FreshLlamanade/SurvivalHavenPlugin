package me.monst.survivalhaven.configuration;

import me.monst.pluginutil.configuration.ConfigurationBranch;
import me.monst.pluginutil.configuration.YamlFile;
import me.monst.survivalhaven.SurvivalHavenPlugin;
import me.monst.survivalhaven.configuration.values.AutoSaveConfig;
import me.monst.survivalhaven.configuration.values.EnableDebugLog;
import me.monst.survivalhaven.configuration.values.Guides;

public class Configuration extends ConfigurationBranch {
    
    private final YamlFile file;
    
    public final Guides guides;
    public final AutoSaveConfig autoSaveConfig;
    public final EnableDebugLog enableDebugLog;
    
    public Configuration(SurvivalHavenPlugin plugin) {
        super("config.yml");
        this.file = new YamlFile(plugin, getKey()); // Create config.yml file
        
        this.guides = addChild(new Guides());
        this.autoSaveConfig = addChild(new AutoSaveConfig());
        this.enableDebugLog = addChild(new EnableDebugLog(plugin));
        
        reload();
    }
    
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
