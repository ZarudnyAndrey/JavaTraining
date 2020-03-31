package core;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "students_orders")
public class StudentsOrders {
  @EmbeddedId
  private StudentsOrdersKey studentOrdersKey;

  public StudentsOrders(StudentsOrdersKey studentOrdersKey) {
    this.studentOrdersKey = studentOrdersKey;
  }
}