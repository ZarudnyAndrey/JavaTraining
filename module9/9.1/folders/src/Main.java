import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
  private static long folderWeight;
  public static void main(String[] args) {
    System.out.println("Please enter the link to the folder whose weight you want to know:");
    String input = new Scanner(System.in).nextLine();
    File myFile = new File(input);
    if (myFile.exists()) {
      try {
        folderWeight = Files.walk(Paths.get(input))
            .map(Path::toFile)
            .map(File::length)
            .mapToLong(Long::longValue)
            .sum();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      System.out.println("Weight of all files in a directory: " + weightCalculation(folderWeight));
    } else {
      System.out.println("Input error");
    }
  }
  private static String weightCalculation(long folderWeight) {
    int count = 0;
    while (folderWeight > 1024) {
      folderWeight /= 1024;
      count++;
    }
    String unit = " KMGTPE";
    char unitValue = unit.charAt(count);
    return folderWeight + " " + unitValue + "b.";
  }
}