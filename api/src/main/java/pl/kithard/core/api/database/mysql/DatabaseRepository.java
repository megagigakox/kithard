package pl.kithard.core.api.database.mysql;

import java.util.Collection;

public interface DatabaseRepository<V> {

    void prepareTable();

    void insert(V data);

    void delete(V data);

    Collection<V> loadAll();

    void updateAll(Collection<V> toUpdate);

}
