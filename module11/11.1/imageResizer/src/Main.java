import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
  private static int newWidth = 300;
  private static ConcurrentLinkedQueue<File> queue;
  private static int processors;
  private static ExecutorService pool;

  public static void main(String[] args) {
    String srcFolder = "c://pictures";
    String dstFolder = "c://destination";

    File srcDir = new File(srcFolder);
    File[] files = srcDir.listFiles();

    long start = System.currentTimeMillis();

    processors = Runtime.getRuntime().availableProcessors();
    pool = Executors.newFixedThreadPool(processors);
    queue = new ConcurrentLinkedQueue<>();

    if (files.length != 0) {
      for (File file : files) {
        if (file.getName().endsWith("jpg")) {
          queue.add(file);
        }
      }
    }

    if (!queue.isEmpty()) {
      for (int i = 0; i < processors; i++) {
        pool.submit(new ImageResizer(queue, newWidth, dstFolder, start));
      }
      pool.shutdown();
    }
  }
}