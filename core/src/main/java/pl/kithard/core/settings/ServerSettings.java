package pl.kithard.core.settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerSettings {

    private long turboDrop, freeze;
    private List<ServerSettingsType> enabledSettings = new ArrayList<>();

    public ServerSettings(long turboDrop, long freeze, List<ServerSettingsType> enabledSettings) {
        this.turboDrop = turboDrop;
        this.freeze = freeze;
        this.enabledSettings = enabledSettings;
    }

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

    public List<ServerSettingsType> getEnabledSettings() {
        return enabledSettings;
    }
}
