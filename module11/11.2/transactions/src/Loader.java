import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Loader {
  public static Double BIG_TRANSFERS_PERCENT = 0.05;
  public static Integer TRANSFERS_QUANTITY = 100_000;
  public static Integer TREADS_QUANTITY = 8;
  public static Integer ACCOUNTS_QUANTITY = 10;
  public static List<Account> accounts;

  static Bank bank;

  public static void main(String[] args) {
    bank = new Bank();

    List<Account> accountList = new ArrayList();

    for (int i = 0; i < ACCOUNTS_QUANTITY; i++) {
      accountList.add(new Account("account" + i, 100000));
    }

    accounts = Collections.synchronizedList(accountList);

    ExecutorService pool = Executors.newFixedThreadPool(TREADS_QUANTITY);

    for (int i = 0; i < TRANSFERS_QUANTITY; i++) {
      pool.submit(new Thread(new TransactionThread(getRandomAccount(), getRandomAccount(), 1)));
    }

    shutdownAndAwaitTermination(pool);
  }

  private static long getTotalBills() {
    return accounts.stream().mapToLong(Account::getMoney).sum();
  }

  private static int getRandomAccount() {
    return (int) (accounts.size() * Math.random());
  }

  private static void shutdownAndAwaitTermination(ExecutorService pool) {
    pool.shutdown();
;    try {
      if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
        pool.shutdownNow();
        if (!pool.awaitTermination(60, TimeUnit.SECONDS))
          System.err.println("Пул не прекратился");
      }
    } catch (InterruptedException ie) {
      pool.shutdownNow();
      Thread.currentThread().interrupt();
    }
  }
}