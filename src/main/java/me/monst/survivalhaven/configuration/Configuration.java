package me.monst.survivalhaven.configuration;

import me.monst.pluginutil.configuration.ConfigurationBranch;
import me.monst.pluginutil.configuration.ConfigurationValue;
import me.monst.pluginutil.configuration.YamlFile;
import me.monst.survivalhaven.SurvivalHavenPlugin;
import me.monst.survivalhaven.configuration.values.AutoClickerDetectionClickThreshold;
import me.monst.survivalhaven.configuration.values.AutoClickerDetectionStrictnessFactor;
import me.monst.survivalhaven.configuration.values.AutoClickerDetectionMaxLenience;

public class Configuration extends ConfigurationBranch {
    
    public final ConfigurationValue<Integer> clickThreshold = addChild(new AutoClickerDetectionClickThreshold());
    
    public final ConfigurationValue<Integer> strictnessFactor = addChild(new AutoClickerDetectionStrictnessFactor());
    
    public final ConfigurationValue<Integer> maxLenience = addChild(new AutoClickerDetectionMaxLenience());
    
    private final YamlFile file;
    
    public Configuration(SurvivalHavenPlugin plugin) {
        super("config.yml");
        this.file = new YamlFile(plugin, getKey()); // Create config.yml file
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
