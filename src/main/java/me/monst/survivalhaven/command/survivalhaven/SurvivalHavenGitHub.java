package me.monst.survivalhaven.command.survivalhaven;

import me.monst.pluginutil.command.Executable;
import me.monst.pluginutil.lang.ColorStringBuilder;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

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
    public void execute(CommandSender sender, List<String> args) {
        sender.sendMessage(new ColorStringBuilder()
                .yellow("Survival Haven Minecraft Plugin on GitHub:").newLine()
                .aqua().underline("https://github.com/FreshLlamanade/SurvivalHavenPlugin")
                .toString());
    }
    
}
