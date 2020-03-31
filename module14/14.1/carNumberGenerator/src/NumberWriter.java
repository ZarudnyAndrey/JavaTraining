import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Queue;

public class NumberWriter implements Runnable {
  char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
  private Queue<Integer> queue;
  private File file = new File("res/numbers.txt");

  public NumberWriter(Queue<Integer> queue) {
    this.queue = queue;
  }

  @Override
  public void run() {
    writeNumbers();
  }

  private void writeNumbers() {
    while (!queue.isEmpty()) {
      int regionCode = queue.poll();
        try (FileOutputStream writer = new FileOutputStream(file, true)) {
          StringBuilder builder = new StringBuilder();
          for (int number = 1; number < 1000; number++) {
            for (char firstLetter : letters) {
              for (char secondLetter : letters) {
                for (char thirdLetter : letters) {
                  builder.append(firstLetter);
                  builder.append(padNumber(number, 3));
                  builder.append(secondLetter);
                  builder.append(thirdLetter);
                  builder.append(padNumber(regionCode, 2));
                  builder.append("\n");
                }
              }
            }
          }
          writer.write(builder.toString().getBytes());
          writer.flush();
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
  }

  private StringBuilder padNumber(int number, int numberLength) {
    StringBuilder numberStr = new StringBuilder(Integer.toString(number));
      int padSize = numberLength - numberStr.length();
      for (int i = 0; i < padSize; i++) {
        numberStr.insert(0, '0');
      }
    return numberStr;
  }
}