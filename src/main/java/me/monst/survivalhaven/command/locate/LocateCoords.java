package me.monst.survivalhaven.command.locate;

import me.monst.pluginutil.command.Args;
import me.monst.pluginutil.command.CommandException;
import me.monst.pluginutil.command.PlayerExecutable;
import me.monst.survivalhaven.particle.ParticleService;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Iterator;

class LocateCoords implements PlayerExecutable {
    
    private final ParticleService particleService;
    
    LocateCoords(ParticleService particleService) {
        this.particleService = particleService;
    }
    
    @Override
    public String getName() {
        return "coords";
    }
    
    @Override
    public String getDescription() {
        return "Leads you to the specified coordinates.";
    }
    
    @Override
    public String getUsage() {
        return "/locate coords <x> <y> <z>";
    }
    
    @Override
    public void execute(Player player, Args args) throws CommandException {
        if (args.size() < 3)
            throw new CommandException("Please specify the x, y, and z coordinates to locate.");
        Iterator<String> argIterator = args.iterator();
        int x = parseCoordinate(argIterator.next());
        int y = parseCoordinate(argIterator.next());
        int z = parseCoordinate(argIterator.next());
        player.sendMessage(ChatColor.YELLOW + "Locating coordinates " + x + ", " + y + ", " + z + "...");
        particleService.addGuide(player, new Location(player.getWorld(), x + 0.5, y + 0.5, z + 0.5));
    }
    
    private int parseCoordinate(String arg) throws CommandException {
        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            throw new CommandException(arg + " is not a valid coordinate.");
        }
    }
    
}
