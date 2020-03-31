import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class Account {
  private String accNumber;
  private long money;
  private AtomicBoolean isBlock = new AtomicBoolean(false);

  public Account(String accNumber, long money) {
    this.accNumber = accNumber;
    this.money = money;
  }

  public long getMoney() {
    return money;
  }

  public void setMoney(long money) {
    this.money = money;
  }

  public void blockAccount() {
    isBlock.set(true);
  }

  public boolean getIsBlock() {
    return isBlock.get();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Account account = (Account) o;
    return money == account.money &&
        Objects.equals(accNumber, account.accNumber) &&
        Objects.equals(isBlock, account.isBlock);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accNumber, money, isBlock);
  }
}