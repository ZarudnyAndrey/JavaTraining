package entrypoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Subway {
  public Map<String, ArrayList<String>> stations = new TreeMap<>();
  public List<List<Connection>> connections = new ArrayList<>();
  public Set<Line> lines = new TreeSet<>();

  public Subway() {
  }

  public void addLine(String lineNumber, String lineName, String lineColor) {
    Line line = new Line(lineNumber, lineName, lineColor);
    if (!(lines.contains(line))) {
      lines.add(line);
    }
  }

  public void addStationInLine(String lineNumber, String stationName) {
    for (Line line : lines) {
      if (line.getNumber().equals(lineNumber)) {
        line.addStation(stationName);
      }
    }
    for (Line line : lines) {
      stations.put(lineNumber, (ArrayList<String>) line.lines);
    }
  }

  public void getCountStations() {
    stations.values().stream().mapToInt(List::size).forEach(System.out::println);
  }
}