package pl.kithard.core.generator;

import org.bukkit.Location;
import pl.kithard.core.CorePlugin;

public class GeneratorFactory {

    private final CorePlugin plugin;

    public GeneratorFactory(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void create(Location location) {

        Generator generator = new Generator(location);
        this.plugin.getGeneratorCache().add(generator);

        this.plugin.getExecutorService().execute(() ->
                this.plugin.getMongoService().insert(generator));
    }

    public void delete(Generator generator) {
        this.plugin.getGeneratorCache().remove(generator);

        this.plugin.getExecutorService().execute(() ->
                this.plugin.getMongoService().delete(generator));
    }

    public void loadAll() {
        this.plugin.getMongoService()
                .loadAll(Generator.class)
                .forEach(generator -> this.plugin.getGeneratorCache().add(generator));
    }


}
