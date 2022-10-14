package pl.kithard.core.player.incognito;

import com.mojang.authlib.GameProfile;
import pl.kithard.core.api.util.RandomStringUtil;

import java.util.concurrent.atomic.AtomicBoolean;

public class PlayerIncognito {

    private boolean tagIncognito;
    private boolean nameIncognito;
    private boolean skinIncognito;
    private boolean pointsIncognito;
    private GameProfile cachedGameProfile;

    public boolean isTagIncognito() {
        return tagIncognito;
    }

    public void setTagIncognito(boolean tagIncognito) {
        this.tagIncognito = tagIncognito;
    }

    public boolean isNameIncognito() {
        return nameIncognito;
    }

    public void setNameIncognito(boolean nameIncognito) {
        this.nameIncognito = nameIncognito;
    }

    public boolean isSkinIncognito() {
        return skinIncognito;
    }

    public void setSkinIncognito(boolean skinIncognito) {
        this.skinIncognito = skinIncognito;
    }

    public boolean isPointsIncognito() {
        return pointsIncognito;
    }

    public void setPointsIncognito(boolean pointsIncognito) {
        this.pointsIncognito = pointsIncognito;
    }

    public GameProfile getCachedGameProfile() {
        return cachedGameProfile;
    }

    public void setCachedGameProfile(GameProfile cachedGameProfile) {
        this.cachedGameProfile = cachedGameProfile;
    }
}
