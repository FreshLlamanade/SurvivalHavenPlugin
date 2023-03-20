package me.monst.survivalhaven.command;

import me.monst.pluginutil.command.Permission;
import org.bukkit.command.CommandSender;

public enum Permissions implements Permission {
    
    GUIDE,
    GUIDE_DEATH,
    GUIDE_PLAYER,
    GUIDE_HOME,
    BREADCRUMBS,
    ADMIN;
    
    private final String perm;
    Permissions() {
        this.perm = "survivalhaven." + name().toLowerCase().replace('_', '.');
    }
    
    public String getPerm() {
        return perm;
    }
    
    @Override
    public boolean ownedBy(CommandSender commandSender) {
        return commandSender.hasPermission(perm);
    }
    
}
