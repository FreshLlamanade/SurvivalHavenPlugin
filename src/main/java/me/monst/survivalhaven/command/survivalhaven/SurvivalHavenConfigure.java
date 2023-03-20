package me.monst.survivalhaven.command.survivalhaven;

import me.monst.pluginutil.command.CommandExecutionException;
import me.monst.pluginutil.command.Executable;
import me.monst.pluginutil.command.Permission;
import me.monst.pluginutil.configuration.ConfigurationBranch;
import me.monst.pluginutil.configuration.ConfigurationNode;
import me.monst.pluginutil.configuration.ConfigurationValue;
import me.monst.pluginutil.configuration.exception.ArgumentParseException;
import me.monst.pluginutil.lang.ColorStringBuilder;
import me.monst.survivalhaven.SurvivalHavenPlugin;
import me.monst.survivalhaven.command.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SurvivalHavenConfigure implements Executable {
    
    private final SurvivalHavenPlugin plugin;
    
    public SurvivalHavenConfigure(SurvivalHavenPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public String getName() {
        return "config";
    }
    
    @Override
    public String getDescription() {
        return "Configure the Survival Haven Minecraft Plugin.";
    }
    
    @Override
    public String getUsage() {
        return "/survivalhaven config";
    }
    
    @Override
    public Permission getPermission() {
        return Permissions.ADMIN.or(Permission.OP);
    }
    
    @Override
    public void execute(CommandSender sender, List<String> args) throws CommandExecutionException {
        int arg = 0;
        ConfigurationNode targetNode = plugin.config();
        while (targetNode instanceof ConfigurationBranch && arg < args.size())
            targetNode = ((ConfigurationBranch) targetNode).getChild(args.get(arg++));
        String path = String.join(".", args.subList(0, arg));
        if (!(targetNode instanceof ConfigurationValue<?> configValue))
            throw new CommandExecutionException(path + " is not a configuration value.");
    
        String input = arg >= args.size() ? null : String.join(" ", args.subList(arg, args.size()));
    
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
    }
    
    @Override
    public List<String> getTabCompletions(Player player, List<String> args) {
        return getTabCompletions(plugin.config(), player, args, 0);
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
