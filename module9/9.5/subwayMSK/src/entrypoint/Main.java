package entrypoint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

  public static void main(String[] args) throws IOException {
    String link = "https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA"
        + "_%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B9"
        + "_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE%D0%B3%D0%BE"
        + "_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0";
    Document document = Jsoup.connect(link).maxBodySize(0).get();
    Element table = document.select("table[class = standard sortable]").get(0);
    Elements trs = table.select("tr");
    Subway subway = new Subway();

    for (int i = 1; i < trs.size(); i++) {
      Element tr = trs.get(i);
      Elements tds = tr.select("td");
      String lineNumber = tds.get(0).child(0).text();
      String lineColor = tds.get(0).attr("style").split(":")[1];
      String lineName = tds.get(0).child(1).attr("title");
      String stationName = tds.get(1).select("a").get(0).text(); //corrected
      Elements connections = tds.get(3).select("span[title]");
      List<Connection> connection = new ArrayList<>();
      connection.add(new Connection(lineNumber, stationName));

      for (int j = 0; j < connections.size(); j++) {
        if (connections.get(j).attr("title") != null) {
          String connectionStationName = connections.get(j).attr("title");
          String connectionStationNumberLine = connections.get(j).previousElementSibling().text();
          connection.add(new Connection(connectionStationNumberLine, connectionStationName));
        }
      }

      if (connection.size() > 1) {
        subway.connections.add(connection);
      }
      subway.addLine(lineNumber, lineName, lineColor);
      subway.addStationInLine(lineNumber, stationName);
    }

    try (FileWriter writer = new FileWriter("map.json")) {
      GSON.toJson(subway, writer);
      System.out.println("JSON-file successfully created!");
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    String json = GSON.toJson(subway);
    Subway subwayStationCount = GSON.fromJson(json, Subway.class);
    System.out.println("Number of stations by lines:");
    subwayStationCount.getCountStations();
  }
}