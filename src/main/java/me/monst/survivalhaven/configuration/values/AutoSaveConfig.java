package me.monst.survivalhaven.configuration.values;

import me.monst.pluginutil.configuration.ConfigurationValue;
import me.monst.survivalhaven.configuration.transform.BooleanTransformer;

public class AutoSaveConfig extends ConfigurationValue<Boolean> {

    public AutoSaveConfig() {
        super("auto-save-config", true, new BooleanTransformer());
    }

}
