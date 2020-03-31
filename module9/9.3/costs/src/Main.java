import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

  private static String staffFile = "data/movementList.csv";
  private static String dateFormat = "dd.MM.yyyy";

  public static void main(String[] args) {
    ArrayList<Costs> staff = loadStaffFromFile();

    double totalParish = staff.stream()
        .map(Costs::getComing)
        .mapToDouble(Double::doubleValue).sum();

    System.out.println("Общий приход: " + totalParish + " рублей.\n");

    double totalConsumption = staff.stream()
        .map(Costs::getConsumption)
        .mapToDouble(Double::doubleValue).sum();

    System.out.println("Общий расход: " + totalConsumption + " рублей.\n");

    System.out.println("Разбивка по операциям:\n");
    Map<String, Double> operation2consumption = staff.stream()
        .collect(Collectors.groupingBy(costs -> costs.getOperationDescription()
                .substring(20, 60).trim(),
            Collectors.summingDouble(Costs::getConsumption)));

    for (Map.Entry<String, Double> calculation : operation2consumption.entrySet()) {
      System.out.println(calculation.getKey() + " - " + calculation.getValue() + " рублей.");
    }
  }

  private static ArrayList<Costs> loadStaffFromFile() {
    ArrayList<Costs> staff = new ArrayList<>();
    try {
      List<String> lines = Files.readAllLines(Paths.get(staffFile));
      for (String line : lines) {
        String[] fragments = line.split("\\,");
        if (fragments.length != 8) {
          if (fragments.length == 9) {
            fragments[7] = (fragments[7] + "." + fragments[8])
                .replaceAll("\"", "");
          } else {
            System.out.println("Wrong line: " + line);
            continue;
          }
        }
        staff.add(new Costs(fragments[0], fragments[1], fragments[2],
            (new SimpleDateFormat(dateFormat)).parse(fragments[3]),
            fragments[4], fragments[5],
            Double.parseDouble(fragments[6]),
            Double.parseDouble(fragments[7])));
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return staff;
  }
}