import core.Course;
import core.HibernateUtil;
import core.Student;

public class Main {

  public static void main(String[] args) {
    HibernateUtil hibernateUtil = new HibernateUtil();
    hibernateUtil.finishedSession();

    Student student = hibernateUtil.finishedSession().get(Student.class, 2);
    System.out.println(student.toString());

    Course course = hibernateUtil.finishedSession().get(Course.class, 1);
    System.out.println(course.toString());

    hibernateUtil.finishedSession().close();
  }
}