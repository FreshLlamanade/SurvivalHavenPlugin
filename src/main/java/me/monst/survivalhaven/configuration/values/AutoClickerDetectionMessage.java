package me.monst.survivalhaven.configuration.values;

import me.monst.pluginutil.configuration.ConfigurationValue;
import org.bukkit.ChatColor;

public class AutoClickerDetectionMessage extends ConfigurationValue<String> {
    
    public AutoClickerDetectionMessage() {
        super(
                "autoclicker-detection-message",
                ChatColor.RED + "You might be using an auto-clicker, which is in violation of rule 2. "
                        + "You have been teleported to spawn. "
                        + "Please use /back if this is in error.",
                input -> ChatColor.translateAlternateColorCodes('&', input)
        );
    }
    
}
