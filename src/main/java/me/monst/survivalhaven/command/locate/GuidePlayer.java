package me.monst.survivalhaven.command.locate;

import me.monst.pluginutil.command.CommandExecutionException;
import me.monst.pluginutil.command.Permission;
import me.monst.pluginutil.command.PlayerExecutable;
import me.monst.survivalhaven.command.Permissions;
import me.monst.survivalhaven.particle.ParticleColors;
import me.monst.survivalhaven.particle.ParticleService;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.StringUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class GuidePlayer implements PlayerExecutable {
    
    private final Plugin plugin;
    private final ParticleService particleService;
    
    GuidePlayer(Plugin plugin, ParticleService particleService) {
        this.plugin = plugin;
        this.particleService = particleService;
    }
    
    @Override
    public String getName() {
        return "player";
    }
    
    @Override
    public String getDescription() {
        return "Guides you to the specified player.";
    }
    
    @Override
    public String getUsage() {
        return "/guide player <name> [color]";
    }
    
    @Override
    public Permission getPermission() {
        return Permissions.GUIDE_PLAYER;
    }
    
    @Override
    public void execute(Player player, List<String> args) throws CommandExecutionException {
        Player target = plugin.getServer().getPlayer(args.get(0));
        if (target == null)
            throw new CommandExecutionException("Player not found.");
        if (target.equals(player))
            throw new CommandExecutionException("We all need a little guidance sometimes...");
        if (!Objects.equals(target.getWorld(), player.getWorld()))
            throw new CommandExecutionException("That player is currently in a different world.");
        
        player.sendMessage(ChatColor.YELLOW + "Guiding you to " + target.getName() + "...");
        if (args.size() == 1)
            particleService.addGuide(player, target);
        else
            particleService.addGuide(player, target, ParticleColors.get(args.get(1)));
    }
    
    @Override
    public List<String> getTabCompletions(Player player, List<String> args) {
        if (args.size() == 1)
            return plugin.getServer().getOnlinePlayers().stream()
                    .filter(p -> !p.equals(player))
                    .map(Player::getName)
                    .filter(name -> StringUtil.startsWithIgnoreCase(name, args.get(0)))
                    .collect(Collectors.toList());
        if (args.size() == 2)
            return ParticleColors.search(args.get(1));
        return Collections.emptyList();
    }
    
}
