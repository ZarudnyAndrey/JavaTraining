import core.Line;
import core.Station;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

public class RouteCalculatorTest extends TestCase {

  RouteCalculator routeCalculator;
  StationIndex stationIndex;

  ArrayList<Station> route;
  ArrayList<Station> route1;
  ArrayList<Station> route2;
  ArrayList<Station>route3;
  ArrayList<Station>route4;

  Line line1;
  Line line2;
  Line line3;

  Station a1;
  Station a2;
  Station a3;
  Station a4;
  Station b1;
  Station b2;
  Station c1;
  Station c2;

  ArrayList<Station> connect1;
  ArrayList<Station> connect2;

  /**
   *     b2    c2
   *     b1    c1
      a1 a2 a3 a4
   */

  @Override
  protected void setUp() {
    stationIndex = new StationIndex();
    routeCalculator = new RouteCalculator(stationIndex);

    line1 = new Line(1, "Вымышленная");
    line2 = new Line(2, "Баснослованая");
    line3 = new Line(3, "Мифическая");

    a1 = new Station("Грецкая", line1);
    a2 = new Station("Банановая", line1);
    a3 = new Station("Кукурузная", line1);
    a4 = new Station("Вишневская", line1);

    b1 = new Station("Народная", line2);
    b2 = new Station("Купеческая", line2);

    c1 = new Station("Рыночная", line3);
    c2 = new Station("Широкая", line3);

    line1.addStation(a1);
    line1.addStation(a2);
    line1.addStation(a3);
    line1.addStation(a4);
    line2.addStation(b1);
    line2.addStation(b2);
    line3.addStation(c1);
    line3.addStation(c2);

    stationIndex.addStation(a1);
    stationIndex.addStation(a2);
    stationIndex.addStation(a3);
    stationIndex.addStation(a4);
    stationIndex.addStation(b1);
    stationIndex.addStation(b2);
    stationIndex.addStation(c1);
    stationIndex.addStation(c2);

    connect1 = new ArrayList<>();
    connect1.add(a2);
    connect1.add(b1);
    stationIndex.addConnection(connect1);

    connect2 = new ArrayList<>();
    connect2.add(a4);
    connect2.add(c1);
    stationIndex.addConnection(connect2);

    route = new ArrayList<>();
    route.add(a1);
    route.add(a2);
    route.add(a3);
    route.add(a4);

    route1 = new ArrayList<>();
    route1.add(a1);
    route1.add(a2);
    route1.add(b1);
    route1.add(b2);

    route2 = new ArrayList<>();
    route2.add(b1);
    route2.add(a2);
    route2.add(a3);
    route2.add(a4);
    route2.add(c1);
    route2.add(c2);

    route3 = new ArrayList<>();
    route3.add(a3);
    route3.add(a3);

    route4 = new ArrayList<>();
    route4.add(a2);
    route4.add(b1);
  }

  public void test_duration_from_one_station_to_it() {
    double actual = RouteCalculator.calculateDuration(route3);
    double expected = 0;
    assertEquals(expected, actual);
  }

  public void test_branch_change_duration() {
    double actual = RouteCalculator.calculateDuration(route4);
    double expected = 3.5;
    assertEquals(expected, actual);
  }

  public void test_trip_duration_on_one_line() {
    double actual = RouteCalculator.calculateDuration(route);
    double expected = 7.5;
    assertEquals(expected, actual);
  }

  public void test_opposite_stations_on_single_line() {
    List<Station> actual = routeCalculator.getShortestRoute(a1, a4);
    List<Station> expected = route;
    assertEquals(expected, actual);
  }

  public void test_opposite_stations_with_two_transfer() {
    List<Station> actual = routeCalculator.getShortestRoute(a1, b2);
    List<Station> expected = route1;
    assertEquals(expected, actual);
  }

  public void test_opposite_stations_with_one_transfers() {
    List<Station> actual = routeCalculator.getShortestRoute(b1, c2);
    List<Station> expected = route2;
    assertEquals(expected, actual);
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }
}