package pl.kithard.core.generator;

import org.apache.commons.lang.NotImplementedException;
import pl.kithard.core.api.database.mysql.DatabaseRepository;
import pl.kithard.core.api.database.mysql.DatabaseService;
import pl.kithard.core.util.LocationSerializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GeneratorRepository implements DatabaseRepository<Generator> {

    private final DatabaseService databaseService;

    public GeneratorRepository(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public void prepareTable() {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS kithard_generators(" +
                        "location TEXT NOT NULL)")
        ) {

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Generator data) {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO kithard_generators (" +
                        "location) VALUES(?)")
        ) {

            preparedStatement.setString(1, LocationSerializer.serialize(data.getLocation()));
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Generator data) {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM kithard_generators WHERE location = ?")
        ) {

            preparedStatement.setString(1, LocationSerializer.serialize(data.getLocation()));
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Generator> loadAll() {
        Set<Generator> generators = new HashSet<>();
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM kithard_generators");
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {

            while (resultSet.next()) {
                generators.add(new Generator(LocationSerializer.deserialize(resultSet.getString("location"))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generators;
    }

    @Override
    public void updateAll(Collection<Generator> toUpdate) {
        throw new NotImplementedException();
    }
}
