package main;

import com.mongodb.client.MongoCollection;
import java.io.IOException;
import org.bson.BsonDocument;
import org.bson.Document;

public class Main {

  public static void main(String[] args) throws IOException {
    Connector connector = new Connector();
    String data = "data/mongo.csv";

    MongoCollection<Document> collection = connector.init().getCollection("students");
    collection.drop();

    ParserCSV parser = new ParserCSV(data);
    collection.insertMany(parser.parseCSV());

    System.out.println("Total number of students in the database: " +
        collection.countDocuments());

    BsonDocument query = BsonDocument.parse("{age: {$gt: '40'}}");
    System.out.println("Count of students over 40: " +
        collection.countDocuments(query));

    query = BsonDocument.parse("{age: 1}");
    System.out.println("Name of the youngest student: " +
        collection.find().sort(query).limit(1).first().get("name"));

    query = BsonDocument.parse("{age: -1}");
    System.out.println("List of oldest student's courses: " +
        collection.find().sort(query).first().get("courses"));
  }
}