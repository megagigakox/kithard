package pl.kithard.core.guild;

import pl.kithard.core.guild.permission.GuildPermission;

import java.util.*;

public class GuildMember {

    private final String guild;

    private final UUID uuid;
    private String name;
    private boolean periscope;

    private Set<GuildPermission> allowedPermissions;

    public GuildMember(String guild, UUID uuid, String name) {
        this.guild = guild;
        this.uuid = uuid;
        this.name = name;

        this.allowedPermissions = new HashSet<>();
    }

    public String getGuild() {
        return guild;
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

    public void setAllowedPermissions(Set<GuildPermission> allowedPermissions) {
        this.allowedPermissions = allowedPermissions;
    }

    public boolean isPeriscope() {
        return periscope;
    }

    public void setPeriscope(boolean periscope) {
        this.periscope = periscope;
    }
}
