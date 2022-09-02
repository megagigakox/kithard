package pl.kithard.core.guild;

import pl.kithard.core.guild.permission.GuildPermission;

import java.util.*;

public class GuildMember {

    private final UUID uuid;
    private String name;
    private boolean periscope;

    private final Set<GuildPermission> allowedPermissions;

    public GuildMember(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;

        this.allowedPermissions = new HashSet<>();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isNotAllowed(GuildPermission permission) {
        return !this.allowedPermissions.contains(permission);
    }

    public Set<GuildPermission> getAllowedPermissions() {
        return allowedPermissions;
    }

    public boolean isPeriscope() {
        return periscope;
    }

    public void setPeriscope(boolean periscope) {
        this.periscope = periscope;
    }
}
