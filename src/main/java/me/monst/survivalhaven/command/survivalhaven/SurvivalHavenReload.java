package me.monst.survivalhaven.command.survivalhaven;

import me.monst.pluginutil.command.Args;
import me.monst.pluginutil.command.Executable;
import me.monst.pluginutil.command.Permission;
import me.monst.survivalhaven.SurvivalHavenPlugin;
import me.monst.survivalhaven.command.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class SurvivalHavenReload implements Executable {
    
    private final SurvivalHavenPlugin plugin;
    
    public SurvivalHavenReload(SurvivalHavenPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public String getName() {
        return "reload";
    }
    
    @Override
    public String getDescription() {
        return "Reload the Survival Haven Minecraft Plugin.";
    }
    
    @Override
    public String getUsage() {
        return "/survivalhaven reload";
    }
    
    @Override
    public Permission getPermission() {
        return Permissions.ADMIN.or(Permission.OP);
    }
    
    @Override
    public void execute(CommandSender sender, Args args) {
        sender.sendMessage(ChatColor.YELLOW + "Reloading plugin...");
        plugin.reload();
    }
    
}
