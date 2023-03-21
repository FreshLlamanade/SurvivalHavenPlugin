package me.monst.survivalhaven.configuration.values;

import me.monst.pluginutil.configuration.ConfigurationValue;
import me.monst.pluginutil.log.Debugger;
import me.monst.survivalhaven.SurvivalHavenPlugin;
import me.monst.survivalhaven.configuration.transform.BooleanTransformer;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class EnableDebugLog extends ConfigurationValue<Boolean> {
    
    private final SurvivalHavenPlugin plugin;
    
    public EnableDebugLog(SurvivalHavenPlugin plugin) {
        super("enable-debug-log", true, new BooleanTransformer());
        this.plugin = plugin;
        afterSet();
    }
    
    @Override
    protected void afterSet() {
        plugin.log().getDebugger().close();
        plugin.log().setDebugger(get() ? tryCreateDebugFile() : Debugger.NO_OP);
    }
    
    private Debugger tryCreateDebugFile() {
        Path debugFile = plugin.getDataFolder().toPath().resolve("debug.txt");
        try {
            PrintWriter debugWriter = new PrintWriter(Files.newOutputStream(debugFile), true);
            return Debugger.printingTo(debugWriter);
        } catch (IOException e) {
            plugin.log().severe("Failed to create debug file.");
            return Debugger.NO_OP;
        }
    }
    
    @Override
    public List<String> getTabCompletions(Player player, List<String> args) {
        return List.of("true", "false");
    }
    
}
