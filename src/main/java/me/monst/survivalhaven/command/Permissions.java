package me.monst.survivalhaven.command;

import me.monst.pluginutil.command.Permission;
import org.bukkit.command.CommandSender;

public enum Permissions implements Permission {
    
    LOCATE,
    LOCATE_DEATH,
    LOCATE_PLAYER,
    LOCATE_HOME,
    BREADCRUMBS,
    ADMIN;
    
    private final String perm;
    Permissions() {
        this.perm = "survivalhaven." + name().toLowerCase().replace('_', '.');
    }
    
    @Override
    public boolean ownedBy(CommandSender commandSender) {
        return commandSender.hasPermission(perm);
    }
    
}
