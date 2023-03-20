package me.monst.survivalhaven.command.survivalhaven;

import me.monst.pluginutil.command.CommandExecutionException;
import me.monst.pluginutil.command.Executable;
import me.monst.pluginutil.command.Permission;
import me.monst.survivalhaven.command.Permissions;
import org.bukkit.command.CommandSender;

import java.util.List;

public class SurvivalHavenUpdate implements Executable {
    
    @Override
    public String getName() {
        return "update";
    }
    
    @Override
    public String getDescription() {
        return "Update the Survival Haven Minecraft Plugin.";
    }
    
    @Override
    public String getUsage() {
        return "/survivalhaven update";
    }
    
    @Override
    public Permission getPermission() {
        return Permissions.ADMIN.or(Permission.OP);
    }
    
    @Override
    public void execute(CommandSender commandSender, List<String> args) throws CommandExecutionException {
        throw new CommandExecutionException("Not implemented yet!");
    }
    
}
