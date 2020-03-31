package core;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class Main {
  private static final String URL = "https://skillbox.ru/";
  static final Logger LOGGER = LogManager.getLogger(Main.class);
  static final Marker EXCEPTION_MARKER = MarkerManager.getMarker("EXCEPTION");

  public static void main(String[] args) {
    System.out.println("Search links. This may take several minutes.");
    Node root = getRootNode();

    try {
      new ForkJoinPool().invoke(new LinkCollection(root));
    } catch (ConcurrentModificationException | NullPointerException ex) {
      LOGGER.error(EXCEPTION_MARKER, "Приложение вызвало исключение: " + ex.getMessage());
    }

    Comparator<String> comparator = Comparator.comparing(link -> getCountSlash(link));
    List<String> sortedList = new ArrayList<>(LinkCollection.linkStorage);
    sortedList.sort(comparator);

    System.out.println("Record.");
    try {
      FileWriter fileWriter = new FileWriter("siteMap.txt");
      for (String link : sortedList) {
        fileWriter.write(tabulator(link));
      }
      fileWriter.flush();
      fileWriter.close();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    System.out.println("Record successful!");
  }

  private static Node getRootNode() {
    return new LinkThief(URL, URL);
  }

  private static String tabulator(String link) {
    StringBuffer stringBuffer = new StringBuffer(link);
    int countOfSlash = getCountSlash(link);
    int countOfTabs = countOfSlash - 3;
    if (countOfTabs > 0) {
      for (int i = 0; i < countOfTabs; i++) {
        stringBuffer.insert(0, "\t");
      }
    }

    stringBuffer.append("\n");
    return stringBuffer.toString();
  }

  private static int getCountSlash(String link) {
    int countOfSlash = 0;

    for (char element : link.toCharArray()) {
      if (element == '/')
        countOfSlash++;
    }

    return countOfSlash;
  }
}