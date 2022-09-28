package pl.kithard.core.generator;

import pl.kithard.core.api.database.mysql.DatabaseRepository;

import java.util.Collection;

public class GeneratorRepository implements DatabaseRepository<Generator> {
    @Override
    public void prepareTable() {

    }

    @Override
    public void insert(Generator data) {

    }

    @Override
    public void delete(Generator data) {

    }

    @Override
    public Collection<Generator> loadAll() {
        return null;
    }

    @Override
    public void updateAll(Collection<Generator> toUpdate) {

    }
}
