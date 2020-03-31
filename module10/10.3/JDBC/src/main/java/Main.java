import core.*;

public class Main {

  public static void main(String[] args) {
    HibernateUtil hibernateUtil = new HibernateUtil();
    hibernateUtil.finishedSession();

//    Student student = hibernateUtil.finishedSession().get(Student.class, 2);
//    System.out.println(student.toString());

//    Course course = hibernateUtil.finishedSession().get(Course.class, 1);
////    System.out.println(course.getTeacher().getName());
//    for (Student student : course.getStudents()) {
//      System.out.println(student.toString());
//    }

    Student student = hibernateUtil.finishedSession().get(Student.class, 2);
    Course course = hibernateUtil.finishedSession().get(Course.class, 1);
    SubscriptionPK subscriptionPK = new SubscriptionPK(student, course);

    Subscription subscription = hibernateUtil.finishedSession().get(Subscription.class, subscriptionPK);
    System.out.println(subscription.getSubscriptionPK().getCourse());
    System.out.println(subscription.getSubscriptionPK().getStudent());
    System.out.println(subscription.getSubscriptionDate());

    hibernateUtil.finishedSession().close();
  }
}