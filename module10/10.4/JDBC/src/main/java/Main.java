import core.Course;
import core.Purchase;
import core.Student;
import core.StudentsOrdersKey;
import core.StudentsOrders;
import java.util.List;
import org.hibernate.Transaction;

public class Main {

  public static void main(String[] args) {
    HibernateUtil hibernateUtil = new HibernateUtil();
    hibernateUtil.finishedSession();

    String hqlCourse = "From " + Course.class.getSimpleName();
    List<Course> courseList = hibernateUtil.finishedSession().createQuery(hqlCourse).getResultList();

//    for(Course course : courseList) {
//      System.out.println(course);
//    }

    String hqlStudents = "From " + Student.class.getSimpleName();
    List<Student> studentList = hibernateUtil.finishedSession().createQuery(hqlStudents).getResultList();
//
//    for (Student student : studentList) {
//      System.out.println(student);
//    }

//    String hqlPurchase = "From " + Purchase.class.getSimpleName();
//    List<Purchase> purchaseList = hibernateUtil.finishedSession().createQuery(hqlPurchase).getResultList();

//    for (Purchase purchase : purchaseList) {
//      System.out.println(purchase);
//    }

//    System.out.println("Course size: " + courseList.size());
//    System.out.println("Student size: " + studentList.size());
//    System.out.println("Purchase size: " + purchaseList.size());

    Transaction transaction = hibernateUtil.finishedSession().beginTransaction();
    for (int i = 1; i <= courseList.size(); i++) {
      Course course = hibernateUtil.finishedSession().get(Course.class, i);
      for (int j = 0; j < course.getStudents().size(); j++) {
        Student student = course.getStudents().get(j);
        StudentsOrders studentsOrders = new StudentsOrders(new StudentsOrdersKey(student, course));
        hibernateUtil.finishedSession().save(studentsOrders);
      }
    }
    transaction.commit();

    hibernateUtil.finishedSession().close();
  }
}