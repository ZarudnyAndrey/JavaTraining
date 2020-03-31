public class TransactionThread implements Runnable {
  private int fromAccNumber;
  private int toAccNumber;
  private long amount;

  public TransactionThread(int fromAccNumber, int toAccNumber, long amount) {
    this.fromAccNumber = fromAccNumber;
    this.toAccNumber = toAccNumber;
    this.amount = amount;
  }

  @Override
  public void run() {
    try {
      Loader.bank.transfer(fromAccNumber, toAccNumber, amount);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}