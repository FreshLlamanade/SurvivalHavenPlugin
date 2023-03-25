package me.monst.survivalhaven.command;

import me.monst.pluginutil.command.Permission;
import me.monst.pluginutil.command.PermissionLimit;

public final class Permissions {
    
    public static final Permission ADMIN = permission("admin");
    
    private Permissions() {}
    
    private static Permission permission(String permission) {
        return Permission.of("survivalhaven." + permission);
    }
    
    private static PermissionLimit permissionLimit(String permission) {
        return PermissionLimit.of("survivalhaven." + permission);
    }
    
}
