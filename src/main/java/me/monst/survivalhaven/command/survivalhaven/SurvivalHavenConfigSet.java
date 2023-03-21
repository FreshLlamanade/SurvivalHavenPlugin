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
        int arg = 0;
        ConfigurationNode targetNode = config;
        while (targetNode instanceof ConfigurationBranch && arg < args.size())
            targetNode = ((ConfigurationBranch) targetNode).getChild(args.get(arg++));
        String path = String.join(".", args.subList(0, arg));
        if (!(targetNode instanceof ConfigurationValue<?> configValue))
            throw new CommandExecutionException(path + " is not a configuration value.");
    
        String input = arg >= args.size() ? null : String.join(" ", args.subList(arg, args.size()));
    
        boolean wasAutoSaveEnabled = config.autoSaveConfig.get();
        if (wasAutoSaveEnabled)
            config.load();
        String previousValue = configValue.toString();
    
        try {
            configValue.feed(input);
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
        if (config.autoSaveConfig.get())
            config.save();
        else if (wasAutoSaveEnabled) // If auto-save was enabled before, but is now disabled, inform the user of how to manually save changes.
            sender.sendMessage(ChatColor.RED + "Auto-saving is disabled. Save changes with /survivalhaven config save.");
    }
    
    @Override
    public List<String> getTabCompletions(Player player, List<String> args) {
        return getTabCompletions(config, player, args, 0);
    }
    
    private List<String> getTabCompletions(ConfigurationBranch branch, Player player, List<String> args, int arg) {
        if (arg >= args.size() - 1)
            return branch.getChildren().keySet().stream()
                    .filter(name -> containsIgnoreCase(name, args.get(args.size() - 1)))
                    .collect(Collectors.toList());
        ConfigurationNode node = branch.getChild(args.get(arg));
        if (node instanceof ConfigurationBranch)
            return getTabCompletions((ConfigurationBranch) node, player, args, arg + 1);
        if (node instanceof ConfigurationValue)
            return ((ConfigurationValue<?>) node).getTabCompletions(player, args);
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
