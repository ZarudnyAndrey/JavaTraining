package entrypoint;

import java.util.ArrayList;
import java.util.List;

public class Line implements Comparable<Line> {
  private String number;
  private String name;
  private String color;
  transient public List<String> lines;

  public Line(String number, String name, String color) {
    this.number = number;
    this.name = name;
    this.color = color;
    lines = new ArrayList<>();
  }

  public String getNumber() {
    return number;
  }

  public void addStation(String stationName) {
    lines.add(stationName);
  }

  @Override
  public int compareTo(Line line) {
    if (getNumber().equals(line.getNumber())) {
      return 0;
    } else {
      return 1;
    }
  }
}