package me.monst.survivalhaven.command.locate;

import me.monst.pluginutil.command.Args;
import me.monst.pluginutil.command.CommandException;
import me.monst.pluginutil.command.Permission;
import me.monst.pluginutil.command.PlayerExecutable;
import me.monst.survivalhaven.command.Permissions;
import me.monst.survivalhaven.particle.ParticleService;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.StringUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class LocatePlayer implements PlayerExecutable {
    
    private final Plugin plugin;
    private final ParticleService particleService;
    
    LocatePlayer(Plugin plugin, ParticleService particleService) {
        this.plugin = plugin;
        this.particleService = particleService;
    }
    
    @Override
    public String getName() {
        return "player";
    }
    
    @Override
    public String getDescription() {
        return "Leads you to the specified player.";
    }
    
    @Override
    public String getUsage() {
        return "/locate player <name>";
    }
    
    @Override
    public Permission getPermission() {
        return Permissions.LOCATE_PLAYER;
    }
    
    @Override
    public void execute(Player player, Args args) throws CommandException {
        Player target = plugin.getServer().getPlayer(args.first());
        if (target == null)
            throw new CommandException("Player not found.");
        if (target.equals(player))
            throw new CommandException("You can't locate yourself.");
        if (!Objects.equals(target.getWorld(), player.getWorld()))
            throw new CommandException("That player is in world '" + target.getWorld().getName() + "'.");
        
        player.sendMessage(ChatColor.YELLOW + "Locating " + target.getName() + "...");
        particleService.addGuide(player, target);
    }
    
    @Override
    public List<String> getTabCompletions(Player player, Args args) {
        if (args.size() != 1)
            return Collections.emptyList();
        return plugin.getServer().getOnlinePlayers().stream()
                .filter(p -> !p.equals(player))
                .map(Player::getName)
                .filter(name -> StringUtil.startsWithIgnoreCase(name, args.first()))
                .collect(Collectors.toList());
    }
    
}
