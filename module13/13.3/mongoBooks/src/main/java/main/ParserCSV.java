package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bson.Document;

public class ParserCSV {
  private List<Document> students = new ArrayList<>();
  private String data;

  public ParserCSV(String data){
    this.data = data;
  }

  public List<Document> parseCSV() throws IOException {
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(data))) {
      String limiter = ",";
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        String[] columns = line.split(limiter, 3);
        String[] courses = columns[2].split(limiter);
        students.add(new Document()
          .append("name", columns[0])
          .append("age", columns[1])
          .append("courses", Arrays.asList(courses))
        );
      }
    }
    return students;
  }
}