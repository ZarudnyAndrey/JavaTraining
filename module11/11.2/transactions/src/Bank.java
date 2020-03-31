import java.util.Random;

public class Bank {
  private final Random random = new Random();

  public Bank() {
  }

  private synchronized boolean isFraud(int fromAccountNum, int toAccountNum, long amount)
    throws InterruptedException {
    Thread.sleep(1000);
    return random.nextBoolean();
  }

  public void transfer(int fromAccNumber, int toAccNumber, long amount)
      throws InterruptedException {
    Account fromAccount = Loader.accounts.get(fromAccNumber);
    Account toAccount = Loader.accounts.get(toAccNumber);
    if (amount > 0) {
      if (!(fromAccount.equals(toAccount))) {
        if (!(fromAccount.getIsBlock() && toAccount.getIsBlock())) {
          if (getBalance(fromAccount) >= amount) {
            if (Math.random() <= Loader.BIG_TRANSFERS_PERCENT) {
              if (isFraud(fromAccNumber, toAccNumber, amount)) {
                accountsBlocking(fromAccount, toAccount);
              } else {
                Account lowSyncAccount = fromAccount;
                Account topSyncAccount = toAccount;
                if (fromAccount.hashCode() < toAccount.hashCode()) {
                  lowSyncAccount = toAccount;
                  topSyncAccount = fromAccount;
                }
                synchronized (lowSyncAccount) {
                  synchronized (topSyncAccount) {
                    withdrawal(fromAccount, amount);
                    replenishment(toAccount, amount);
                  }
                }
                System.out.println("Операция успешно завершена!");
              }
            } else {
              Account lowSyncAccount = fromAccount;
              Account topSyncAccount = toAccount;
              if (fromAccount.hashCode() < toAccount.hashCode()) {
                lowSyncAccount = toAccount;
                topSyncAccount = fromAccount;
              }
              synchronized (lowSyncAccount) {
                synchronized (topSyncAccount) {
                  withdrawal(fromAccount, amount);
                  replenishment(toAccount, amount);
                }
              }
              System.out.println("Операция успешно завершена!");
            }
          } else {
            System.out.println("Не хватает средств для перевода!");
          }
        } else {
          System.out.println("Перевод невозможен из-за блокировки аккаунта");
        }
      } else {
        System.out.println("Вы не можете отправить самому себе!");
      }
    }
  }

  private void withdrawal(Account fromAccount, long amount) {
    fromAccount.setMoney(getBalance(fromAccount) - amount);
  }

  private void replenishment(Account toAccount, long amount) {
    toAccount.setMoney(getBalance(toAccount) + amount);
  }

  private void accountsBlocking(Account fromAccount, Account toAccount) {
    fromAccount.blockAccount();
    toAccount.blockAccount();
    System.out.println("Аккаунты заблокированы!");
  }

  public long getBalance(Account accNumber) {
    return accNumber.getMoney();
  }
}