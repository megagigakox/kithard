package pl.kithard.core.guild.permission;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GuildPermissionScheme {

    private final String guild;

    private final String name;
    private Set<GuildPermission> allowedPermissions;

    public GuildPermissionScheme(String guild, String name) {
        this.guild = guild;
        this.name = name;

        this.allowedPermissions = new HashSet<>();
    }

    public String getGuild() {
        return guild;
    }

    public void togglePermissions(boolean on) {
        if (on) {
            Collections.addAll(this.allowedPermissions, GuildPermission.values());
        } else {
            for (GuildPermission value : GuildPermission.values()) {
                this.allowedPermissions.remove(value);
            }
        }
    }

    public void togglePermission(GuildPermission permission) {
        if (allowedPermissions.contains(permission)) {
            allowedPermissions.remove(permission);
        } else {
            allowedPermissions.add(permission);
        }
    }

    public String getName() {
        return name;
    }

    public Set<GuildPermission> getAllowedPermissions() {
        return allowedPermissions;
    }

    public void setAllowedPermissions(Set<GuildPermission> allowedPermissions) {
        this.allowedPermissions = allowedPermissions;
    }
}
