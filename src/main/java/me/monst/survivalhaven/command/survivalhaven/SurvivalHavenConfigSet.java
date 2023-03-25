package me.monst.survivalhaven.command.survivalhaven;

import me.monst.pluginutil.command.CommandExecutionException;
import me.monst.pluginutil.command.Executable;
import me.monst.pluginutil.configuration.ConfigurationBranch;
import me.monst.pluginutil.configuration.ConfigurationNode;
import me.monst.pluginutil.configuration.ConfigurationValue;
import me.monst.pluginutil.configuration.exception.ArgumentParseException;
import me.monst.pluginutil.lang.ColorStringBuilder;
import me.monst.survivalhaven.configuration.Configuration;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

class SurvivalHavenConfigSet implements Executable {
    
    private final Configuration config;
    
    public SurvivalHavenConfigSet(Configuration config) {
        this.config = config;
    }
    
    @Override
    public String getName() {
        return "set";
    }
    
    @Override
    public String getDescription() {
        return "Set a configuration value.";
    }
    
    @Override
    public String getUsage() {
        return "/survivalhaven config set <path> [value]";
    }
    
    @Override
    public void execute(CommandSender sender, List<String> args) throws CommandExecutionException {
        ListIterator<String> iterator = args.listIterator();
        ConfigurationNode targetNode = config.deepSearch(iterator);
        String path = String.join(".", args.subList(0, iterator.nextIndex()));
        
        if (!(targetNode instanceof ConfigurationValue<?> configValue))
            throw new CommandExecutionException(path + " is not a configuration value.");
    
        boolean wasAutoSaveEnabled = config.autoSaveConfig.get();
        if (wasAutoSaveEnabled)
            config.load();
        String previousValue = configValue.toString();
    
        try {
            String input = String.join(" ", args.subList(iterator.nextIndex(), args.size()));
            configValue.feed(input.isEmpty() ? null : input);
        } catch (ArgumentParseException e) {
            throw new CommandExecutionException(e.getMessage());
        }
    
        String newValue = configValue.toString();
    
        sender.sendMessage(new ColorStringBuilder()
                .green("Set ")
                .gold(path)
                .green(" from ")
                .gold(previousValue)
                .green(" to ")
                .gold(newValue)
                .green(".")
                .toString());
        if (!configValue.isHotSwappable())
            sender.sendMessage(ChatColor.RED + "This change will not take effect until the server is restarted.");
        if (config.autoSaveConfig.get()) // If auto-save is enabled, save the config.
            config.save();
        else if (wasAutoSaveEnabled) // If auto-save is disabled, but was enabled before, inform the user of how to manually save changes.
            sender.sendMessage(ChatColor.RED + "Auto-saving is disabled. Save changes with /survivalhaven config save.");
    }
    
    @Override
    public List<String> getTabCompletions(Player player, List<String> args) {
        ListIterator<String> iterator = args.listIterator();
        ConfigurationNode targetNode = config.deepSearch(iterator);
        if (targetNode instanceof ConfigurationBranch branch) {
            if (iterator.nextIndex() < args.size())
                return Collections.emptyList();
            String lastArg = args.get(args.size() - 1);
            return branch.getChildren().keySet().stream()
                    .filter(child -> containsIgnoreCase(child, lastArg))
                    .collect(Collectors.toList());
        } else if (targetNode instanceof ConfigurationValue<?> configValue)
            return configValue.getTabCompletions(player, args.subList(iterator.nextIndex(), args.size()));
        return Collections.emptyList();
    }
    
    private static boolean containsIgnoreCase(String string, String substring) {
        int len = substring.length();
        int max = string.length() - len;
        for (int i = 0; i <= max; i++)
            if (string.regionMatches(true, i, substring, 0, len))
                return true;
        return false;
    }
    
}
