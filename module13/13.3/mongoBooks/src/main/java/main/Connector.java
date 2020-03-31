package main;

    import com.mongodb.MongoClient;
    import com.mongodb.client.MongoDatabase;

public class Connector {

  public MongoDatabase init() {
    String host = "127.0.0.1";
    int port = 27017;
    String database = "local";
    MongoClient mongoClient = new MongoClient(host, port);
    MongoDatabase mongoDatabase = mongoClient.getDatabase(database);
    return mongoDatabase;
  }
}