package core;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class StudentsOrdersKey implements Serializable {
  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id", referencedColumnName = "id")
  private Student student;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "course_id", referencedColumnName = "id")
  private Course course;

  public StudentsOrdersKey(Course course) {
    this.course = course;
  }

  public StudentsOrdersKey(Student student, Course course) {
    this.student = student;
    this.course = course;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StudentsOrdersKey that = (StudentsOrdersKey) o;
    return Objects.equals(student, that.student) &&
        Objects.equals(course, that.course);
  }

  @Override
  public int hashCode() {
    return Objects.hash(student, course);
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }
}
