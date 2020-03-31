package entrypoint;

public class Connection implements Comparable<Connection> {
  private String line;
  private String name;

  public Connection(String line, String name) {
    this.line = line;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public int compareTo(Connection connection) {
    return name.compareTo(connection.getName());
  }
}