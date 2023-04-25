package me.monst.survivalhaven.configuration.values;

import me.monst.pluginutil.configuration.ConfigurationValue;
import me.monst.pluginutil.configuration.transform.BoundedTransformer;
import me.monst.pluginutil.configuration.validation.Bound;
import me.monst.survivalhaven.configuration.transform.IntegerTransformer;

public class AutoClickerDetectionMaxLenience extends ConfigurationValue<Integer> {
    
    public AutoClickerDetectionMaxLenience() {
        super(
                "autoclicker-detection-max-lenience-millis",
                100,
                new BoundedTransformer<>(new IntegerTransformer(), Bound.atLeast(0))
        );
    }
    
}
