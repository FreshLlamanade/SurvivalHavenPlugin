package me.monst.survivalhaven.command.survivalhaven;

import me.monst.pluginutil.command.CommandExecutionException;
import me.monst.pluginutil.command.Executable;
import me.monst.survivalhaven.configuration.Configuration;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

class SurvivalHavenConfigReload implements Executable {
    
    private final Configuration config;
    
    public SurvivalHavenConfigReload(Configuration config) {
        this.config = config;
    }
    
    @Override
    public String getName() {
        return "reload";
    }
    
    @Override
    public String getDescription() {
        return "Reloads the plugin configuration from the config.yml file.";
    }
    
    @Override
    public String getUsage() {
        return "/survivalhaven config reload";
    }
    
    @Override
    public void execute(CommandSender sender, List<String> args) throws CommandExecutionException {
        config.load();
        sender.sendMessage(ChatColor.YELLOW + "Configuration loaded from config.yml.");
    }
    
}
