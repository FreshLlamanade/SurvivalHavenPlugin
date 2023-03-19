package me.monst.survivalhaven.configuration.values;

import me.monst.pluginutil.configuration.ConfigurationValue;
import me.monst.survivalhaven.configuration.transform.IntegerTransformer;

import java.util.Optional;

public class MaxBreadcrumbs extends ConfigurationValue<Optional<Integer>> {
    
    public MaxBreadcrumbs() {
        super("max-breadcrumbs", Optional.of(500), new IntegerTransformer().optional());
    }
    
    public boolean isAllowedLength(int length) {
        return get().map(limit -> length < limit).orElse(true);
    }
    
}
