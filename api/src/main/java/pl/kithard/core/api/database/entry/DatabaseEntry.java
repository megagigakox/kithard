package pl.kithard.core.api.database.entry;

public class DatabaseEntry {

    private transient boolean needSave;

    public boolean isNeedSave() {
        return needSave;
    }

    public void setNeedSave(boolean needSave) {
        this.needSave = needSave;
    }
}
