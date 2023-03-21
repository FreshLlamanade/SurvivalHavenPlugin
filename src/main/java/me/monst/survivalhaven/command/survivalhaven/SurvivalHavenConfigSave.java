package me.monst.survivalhaven.command.survivalhaven;

import me.monst.pluginutil.command.CommandExecutionException;
import me.monst.pluginutil.command.Executable;
import me.monst.survivalhaven.configuration.Configuration;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

class SurvivalHavenConfigSave implements Executable {
    
    private final Configuration config;
    
    public SurvivalHavenConfigSave(Configuration config) {
        this.config = config;
    }
    
    @Override
    public String getName() {
        return "save";
    }
    
    @Override
    public String getDescription() {
        return "Saves the plugin configuration to the config.yml file.";
    }
    
    @Override
    public String getUsage() {
        return "/survivalhaven config save";
    }
    
    @Override
    public void execute(CommandSender sender, List<String> args) throws CommandExecutionException {
        config.save();
        sender.sendMessage(ChatColor.YELLOW + "Configuration saved to config.yml.");
    }
    
}
