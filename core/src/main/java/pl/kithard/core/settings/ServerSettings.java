package pl.kithard.core.settings;

import com.google.gson.annotations.SerializedName;
import pl.kithard.core.api.database.entity.DatabaseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerSettings {

    private long turboDrop, freeze;
    private final List<ServerSettingsType> enabledSettings = new ArrayList<>();

    public ServerSettings() {
        this.enabledSettings.addAll(Arrays.asList(ServerSettingsType.values()));
    }

    public boolean isEnabled(ServerSettingsType serverSettingsType) {
        return this.enabledSettings.contains(serverSettingsType);
    }

    public void addEnabledSetting(ServerSettingsType serverSettingsType) {
        this.enabledSettings.add(serverSettingsType);
    }

    public void removeEnabledSetting(ServerSettingsType serverSettingsType) {
        this.enabledSettings.remove(serverSettingsType);
    }

    public long getTurboDrop() {
        return turboDrop;
    }

    public void setTurboDrop(long turboDrop) {
        this.turboDrop = turboDrop;
    }

    public long getFreeze() {
        return freeze;
    }

    public void setFreeze(long freeze) {
        this.freeze = freeze;
    }
}
