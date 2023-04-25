package me.monst.survivalhaven.configuration.values;

import me.monst.pluginutil.configuration.ConfigurationValue;
import me.monst.pluginutil.configuration.transform.BoundedTransformer;
import me.monst.pluginutil.configuration.validation.Bound;
import me.monst.survivalhaven.configuration.transform.IntegerTransformer;

public class AutoClickerDetectionStrictnessFactor extends ConfigurationValue<Integer> {
    
    public AutoClickerDetectionStrictnessFactor() {
        super(
                "autoclicker-detection-strictness-factor",
                15,
                new BoundedTransformer<>(new IntegerTransformer(), Bound.atLeast(1))
        );
    }
    
}
