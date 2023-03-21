package me.monst.survivalhaven.configuration.values;

import me.monst.pluginutil.configuration.ConfigurationBranch;
import me.monst.pluginutil.configuration.ConfigurationValue;
import me.monst.survivalhaven.configuration.transform.IntegerTransformer;

public class Guides extends ConfigurationBranch {
    
    public final ConfigurationValue<Integer> length;
    
    public final ConfigurationValue<Integer> guideDensity;
    
    public final ConfigurationValue<Integer> highlightDensity;
    
    public final ConfigurationValue<Integer> repeatDelay;
    
    public final ConfigurationValue<Integer> particleDelay;
    
    public Guides () {
        super("particle-guides");
        this.length = addChild(new ConfigurationValue<>("guide-length", 10, new IntegerTransformer()));
        this.guideDensity = addChild(new ConfigurationValue<>("guide-density", 5, new IntegerTransformer()));
        this.highlightDensity = addChild(new ConfigurationValue<>("highlight-density", 20, new IntegerTransformer()));
        this.repeatDelay = addChild(new ConfigurationValue<>("repeat-delay-millis", 1500, new IntegerTransformer()));
        this.particleDelay = addChild(new ConfigurationValue<>("particle-delay-millis", 50, new IntegerTransformer()));
    }
    
}
