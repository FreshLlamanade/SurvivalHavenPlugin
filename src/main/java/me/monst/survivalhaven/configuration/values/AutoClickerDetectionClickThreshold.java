package me.monst.survivalhaven.configuration.values;

import me.monst.pluginutil.configuration.ConfigurationValue;
import me.monst.pluginutil.configuration.transform.BoundedTransformer;
import me.monst.pluginutil.configuration.validation.Bound;
import me.monst.survivalhaven.configuration.transform.IntegerTransformer;

public class AutoClickerDetectionClickThreshold extends ConfigurationValue<Integer> {
    
    public AutoClickerDetectionClickThreshold() {
        super(
                "autoclicker-detection-click-threshold",
                10,
                new BoundedTransformer<>(new IntegerTransformer(), Bound.atLeast(3))
        );
    }
    
}
