package pl.kithard.core.api.database;

import com.google.gson.Gson;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;
import pl.kithard.core.api.database.entity.DatabaseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class MongoService {

    private final Map<Class<?>, String> collections = new ConcurrentHashMap<>();
    private final Map<Class<?>, String> databases = new ConcurrentHashMap<>();

    private final MongoClient mongoClient;
    private final Gson gson;

    public MongoService(String connectionUrl, Gson gson) {
        this.mongoClient = MongoClients.create(connectionUrl);
        this.gson = gson;
    }

    public <V, T> V load(T key, Class<V> type)  {
        MongoCollection<Document> collection = getCollection(type);

        Document document = collection.find(Filters.eq("_id", key)).first();

        if (document == null) {
            return null;
        }

        String json = document.toJson();

        return gson.fromJson(json, type);
    }

    public <V> List<V> loadAll(Class<V> type) {
        MongoCollection<Document> collection = getCollection(type);
        List<V> list = new ArrayList<>();

        collection
                .find()
                .forEach((Consumer<? super Document>) document -> {
                    String json = document.toJson();
                    V entity = gson.fromJson(json, type);
                    if (entity != null) {
                        list.add(entity);
                    }
                });

        return list;
    }

    public <V> void save(V entity) {
        String json = gson.toJson(entity);
        Document document = Document.parse(json);

        if (document == null) {
            return;
        }

        MongoCollection<Document> collection = this.getCollection(entity.getClass());

        Object key = document.get("_id");
        collection.replaceOne(Filters.eq("_id", key), document, new ReplaceOptions().upsert(true));
    }

    public <V> void insert(V entity) {
        String json = gson.toJson(entity);
        Document document = Document.parse(json);

        if (document == null) {
            return;
        }

        MongoCollection<Document> collection = this.getCollection(entity.getClass());
        collection.insertOne(document);
    }

    public <V> void delete(V entity) {
        String json = gson.toJson(entity);
        Document document = Document.parse(json);

        if (document == null) {
            return;
        }

        MongoCollection<Document> collection = getCollection(entity.getClass());

        Object key = document.get("_id");
        collection.findOneAndDelete(Filters.eq("_id", key));
    }

    public MongoCollection<Document> getCollection(Class<?> clazz) {
        String collection = collections
                .computeIfAbsent(clazz, c -> clazz.isAnnotationPresent(DatabaseEntity.class)
                        ? clazz.getAnnotation(DatabaseEntity.class).collection()
                        : clazz.getSimpleName());
        String database = databases
                .computeIfAbsent(clazz, c -> clazz.isAnnotationPresent(DatabaseEntity.class)
                        ? clazz.getAnnotation(DatabaseEntity.class).database()
                        : clazz.getSimpleName());

        return mongoClient.getDatabase(database).getCollection(collection);
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }
}
