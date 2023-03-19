package me.monst.survivalhaven.command.survivalhaven;

import me.monst.pluginutil.command.Args;
import me.monst.pluginutil.command.Executable;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class SurvivalHavenGitHub implements Executable {
    
    @Override
    public String getName() {
        return "github";
    }
    
    @Override
    public String getDescription() {
        return "View the Survival Haven Minecraft Plugin on GitHub.";
    }
    
    @Override
    public String getUsage() {
        return "/survivalhaven github";
    }
    
    @Override
    public void execute(CommandSender sender, Args args) {
        // TODO: Add GitHub link
        sender.sendMessage(ChatColor.YELLOW + "Survival Haven Minecraft Plugin: <GitHub link>");
    }
    
}
