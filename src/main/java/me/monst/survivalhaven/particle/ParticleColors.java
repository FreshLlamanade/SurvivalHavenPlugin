package me.monst.survivalhaven.particle;

import me.monst.pluginutil.command.CommandExecutionException;
import org.bukkit.Color;

import java.util.List;
import java.util.Map;

public class ParticleColors {

    private static final Map<String, Color> COLOR_MAP = Map.ofEntries(
            Map.entry("red", Color.RED),
            Map.entry("lime", Color.LIME),
            Map.entry("green", Color.GREEN),
            Map.entry("blue", Color.BLUE),
            Map.entry("black", Color.BLACK),
            Map.entry("white", Color.WHITE),
            Map.entry("yellow", Color.YELLOW),
            Map.entry("orange", Color.ORANGE),
            Map.entry("purple", Color.PURPLE),
            Map.entry("aqua", Color.AQUA),
            Map.entry("gray", Color.SILVER),
            Map.entry("pink", Color.fromRGB(255, 192, 203)),
            Map.entry("cyan", Color.fromRGB(0, 255, 255)),
            Map.entry("magenta", Color.fromRGB(255, 0, 255))
    );
    
    public static Color get(String name) throws CommandExecutionException {
        Color color = COLOR_MAP.get(name);
        if (color == null)
            throw new CommandExecutionException("'" + name + "' is not a valid color.");
        return color;
    }
    
    public static List<String> search(String search) {
        return COLOR_MAP.keySet().stream()
                .filter(key -> key.contains(search))
                .toList();
    }
    
    public static Color random() {
        return COLOR_MAP.values().stream()
                .skip((int) (Math.random() * COLOR_MAP.size()))
                .findFirst()
                .orElse(Color.WHITE);
    }

}
