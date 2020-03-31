import java.util.Date;

public class Costs {

  private String typeOfAccount;
  private String numberAccount;
  private String currency;
  private Date dateOfOperation;
  private String wiringReference;
  private String operationDescription;
  private Double coming;
  private Double consumption;

  public Costs(String typeOfAccount, String numberAccount, String currency,
      Date dateOfOperation, String wiringReference, String operationDescription,
      Double coming, Double consumption) {
    this.typeOfAccount = typeOfAccount;
    this.numberAccount = numberAccount;
    this.currency = currency;
    this.dateOfOperation = dateOfOperation;
    this.wiringReference = wiringReference;
    this.operationDescription = operationDescription;
    this.coming = coming;
    this.consumption = consumption;
  }

  public String getTypeOfAccount() {
    return typeOfAccount;
  }

  public void setTypeOfAccount(String typeOfAccount) {
    this.typeOfAccount = typeOfAccount;
  }

  public String getNumberAccount() {
    return numberAccount;
  }

  public void setNumberAccount(String numberAccount) {
    this.numberAccount = numberAccount;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public Date getDateOfOperation() {
    return dateOfOperation;
  }

  public void setDateOfOperation(Date dateOfOperation) {
    this.dateOfOperation = dateOfOperation;
  }

  public String getWiringReference() {
    return wiringReference;
  }

  public void setWiringReference(String wiringReference) {
    this.wiringReference = wiringReference;
  }

  public String getOperationDescription() {
    return operationDescription;
  }

  public void setOperationDescription(String operationDescription) {
    this.operationDescription = operationDescription;
  }

  public Double getComing() {
    return coming;
  }

  public void setComing(Double coming) {
    this.coming = coming;
  }

  public Double getConsumption() {
    return consumption;
  }

  public void setConsumption(Double consumption) {
    this.consumption = consumption;
  }

  public String toString() {
    return typeOfAccount + ", " + numberAccount + ", " + currency + ", " +
        dateOfOperation + ", " + wiringReference + ", " + operationDescription + ", " +
        coming + ", " + consumption;
  }
}
