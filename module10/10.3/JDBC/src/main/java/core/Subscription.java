package core;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Subscriptions")
public class Subscription {
  @EmbeddedId
  private SubscriptionPK subscriptionPK;

  @Column(name = "subscription_date")
  private Date subscriptionDate;

  public SubscriptionPK getSubscriptionPK() {
    return subscriptionPK;
  }

  public void setSubscriptionPK(SubscriptionPK subscriptionPK) {
    this.subscriptionPK = subscriptionPK;
  }

  public Date getSubscriptionDate() {
    return subscriptionDate;
  }

  public void setSubscriptionDate(Date subscriptionDate) {
    this.subscriptionDate = subscriptionDate;
  }
}