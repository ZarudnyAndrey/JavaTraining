import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
  private static final int threadCount = 8;
  private static final int regionCount = 10;

  public static void main(String[] args) throws Exception {
    ExecutorService pool = Executors.newFixedThreadPool(threadCount);
    ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();

    long start = System.currentTimeMillis();

    for (int regionCode = 1; regionCode <= regionCount; regionCode++) {
      queue.add(regionCode);
    }

    if (!queue.isEmpty()) {
      for (int i = 0; i < threadCount; i++) {
        pool.submit(new NumberWriter(queue));
      }
      pool.shutdown();
    }
    pool.awaitTermination(20, TimeUnit.MINUTES);

    System.out.println((System.currentTimeMillis() - start) + "ms");
  }
}