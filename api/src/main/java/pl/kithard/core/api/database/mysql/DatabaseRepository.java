package pl.kithard.core.api.database.mysql;

import java.util.Set;

public abstract class DatabaseRepository<T> {

    public abstract Set<T> loadAll();

    public abstract void batchUpdate(Set<T> toUpdate);

}
