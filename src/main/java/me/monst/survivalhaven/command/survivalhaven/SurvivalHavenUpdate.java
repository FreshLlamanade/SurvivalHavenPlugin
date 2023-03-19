package me.monst.survivalhaven.command.survivalhaven;

import me.monst.pluginutil.command.Args;
import me.monst.pluginutil.command.CommandException;
import me.monst.pluginutil.command.Executable;
import org.bukkit.command.CommandSender;

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
    public void execute(CommandSender commandSender, Args args) throws CommandException {
        throw new CommandException("Not implemented yet!");
    }
    
}
