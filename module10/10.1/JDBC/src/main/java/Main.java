import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

  public static void main(String[] args) {
    String url = "jdbc:mysql://localhost:3306/skillbox" +
        "?verifyServerCertificate=false" +
        "&useSSL=false" +
        "&requireSSL=false" +
        "&useLegacyDatetimeCode=false" +
        "&amp" +
        "&serverTimezone=UTC";
    String user = "root";
    String pass = "eH3mVr7a";
//    String request = "SELECT course_name,"
//        + " (COUNT(subscription_date)) / (MAX(MONTH(subscription_date))"
//        + " - MIN(MONTH(subscription_date))) AS average_number_of_purchases_per_month"
//        + " FROM purchaselist GROUP BY course_name;";

    String request2 = "SELECT course_name,"
        + "IF (MAX(YEAR(subscription_date)) = MIN(YEAR(subscription_date)),"
        + " COUNT(subscription_date) / (MAX(MONTH(subscription_date))"
        + " - MIN(MONTH(subscription_date))), 0)"
        + " AS average_number_of_purchases_per_month, YEAR(subscription_date) as year"
        + " FROM purchaselist GROUP BY year, course_name;";

//        String request3 = "SELECT course_name,"
//        + " (COUNT(subscription_date)) / (MAX(MONTH(subscription_date))"
//        + " - MIN(MONTH(subscription_date))) AS average_number_of_purchases_per_month,"
//        + " YEAR(subscription_date) as year"
//        + " FROM purchaselist GROUP BY year, course_name;";

    try (Connection connection = DriverManager.getConnection(url, user, pass);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(request2)) {
      while (resultSet.next()) {
        String courseName = resultSet.getString("course_name");
        String averageNumberOfPurchasesPerMonth = resultSet
            .getString("average_number_of_purchases_per_month");
        String year = resultSet.getString("year");
        System.out.println(courseName + "\t" + averageNumberOfPurchasesPerMonth + "\t" + year);
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
}