package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.webserver.WebServerSession;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class VehicleTest {

  private Vehicle testVehicle;
  private Vehicle testVehicleWithNegativeSpeed;
  private Vehicle testVehicleWithIssue;
  private SmallBus testSmallBus;
  private Route testRouteIn;
  private Route testRouteOut;


  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    List<Stop> stopsIn = new ArrayList<Stop>();
    Stop stop1 = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop stop2 = new Stop(1, "test stop 2", new Position(-93.235071, 44.973580));
    stopsIn.add(stop1);
    stopsIn.add(stop2);
    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.843774422231134);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.025);
    probabilitiesIn.add(0.3);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(stopsIn, probabilitiesIn);

    testRouteIn = new Route(0, "testRouteIn",
        stopsIn, distancesIn, generatorIn);

    List<Stop> stopsOut = new ArrayList<Stop>();
    stopsOut.add(stop2);
    stopsOut.add(stop1);
    List<Double> distancesOut = new ArrayList<>();
    distancesOut.add(0.843774422231134);
    List<Double> probabilitiesOut = new ArrayList<Double>();
    probabilitiesOut.add(0.3);
    probabilitiesOut.add(.025);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(stopsOut, probabilitiesOut);

    testRouteOut = new Route(1, "testRouteOut",
        stopsOut, distancesOut, generatorOut);

    testVehicle = new VehicleTestImpl(1, new Line(10000, "testLine",
        "VEHICLE_LINE", testRouteOut, testRouteIn,
        new Issue()), 3, 1.0, new PassengerLoader(), new PassengerUnloader());

    testVehicleWithNegativeSpeed = new VehicleTestImpl(1, new Line(10000, "testLine",
        "VEHICLE_LINE", testRouteOut, testRouteIn,
        new Issue()), 3, -1.0, new PassengerLoader(), new PassengerUnloader());

    Issue testIssue = new Issue();
    testIssue.createIssue();
    testVehicleWithIssue = new VehicleTestImpl(1, new Line(10000, "testLine",
        "VEHICLE_LINE", testRouteOut, testRouteIn,
        testIssue), 3, -1.0, new PassengerLoader(), new PassengerUnloader());
  }

  /**
   * Tests constructor.
   */
  @Test
  public void testConstructor() {
    assertEquals(1, testVehicle.getId());
    assertEquals("testRouteOut1", testVehicle.getName());
    assertEquals(3, testVehicle.getCapacity());
    assertEquals(1, testVehicle.getSpeed());
    assertEquals(testRouteOut, testVehicle.getLine().getOutboundRoute());
    assertEquals(testRouteIn, testVehicle.getLine().getInboundRoute());
  }

  /**
   * Tests if testIsTripComplete function works properly.
   */
  @Test
  public void testIsTripComplete() {
    assertFalse(testVehicle.isTripComplete());
    testVehicle.move();
    testVehicle.move();
    testVehicle.move();
    testVehicle.move();
    testVehicle.move();
    testVehicle.move();
    assertTrue(testVehicle.isTripComplete());


    Vehicle negativeSpeedVehicle = new VehicleTestImpl(1, new Line(10000, "testLine",
        "VEHICLE_LINE", testRouteOut, testRouteIn,
        new Issue()), 3, -1, new PassengerLoader(), new PassengerUnloader());

    negativeSpeedVehicle.move();

  }


  /**
   * Tests if loadPassenger function works properly.
   */
  @Test
  public void testLoadPassenger() {

    Passenger testPassenger1 = new Passenger(3, "testPassenger1");
    Passenger testPassenger2 = new Passenger(2, "testPassenger2");
    Passenger testPassenger3 = new Passenger(1, "testPassenger3");
    Passenger testPassenger4 = new Passenger(1, "testPassenger4");

    assertEquals(1, testVehicle.loadPassenger(testPassenger1));
    assertEquals(1, testVehicle.loadPassenger(testPassenger2));
    assertEquals(1, testVehicle.loadPassenger(testPassenger3));
    assertEquals(0, testVehicle.loadPassenger(testPassenger4));
  }


  /**
   * Tests if move function works properly.
   */
  @Test
  public void testMove() {

    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());
    testVehicle.move();

    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.move();
    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.move();
    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());

    testVehicle.move();
    assertNull(testVehicle.getNextStop());

  }

  /**
   * Tests if move function works properly with a negative train.
   */
  @Test
  public void testMoveWithNegativeSpeed() {

    assertEquals("test stop 2", testVehicleWithNegativeSpeed.getNextStop().getName());
    assertEquals(1, testVehicleWithNegativeSpeed.getNextStop().getId());
    testVehicleWithNegativeSpeed.move();

    assertEquals("test stop 1", testVehicleWithNegativeSpeed.getNextStop().getName());
    assertEquals(0, testVehicleWithNegativeSpeed.getNextStop().getId());

    testVehicleWithNegativeSpeed.move();
    assertEquals("test stop 1", testVehicleWithNegativeSpeed.getNextStop().getName());
    assertEquals(0, testVehicleWithNegativeSpeed.getNextStop().getId());

    testVehicleWithNegativeSpeed.move();
    assertEquals("test stop 1", testVehicleWithNegativeSpeed.getNextStop().getName());
    assertEquals(0, testVehicleWithNegativeSpeed.getNextStop().getId());

    testVehicleWithNegativeSpeed.move();
    assertEquals("test stop 1", testVehicleWithNegativeSpeed.getNextStop().getName());
    assertEquals(0, testVehicleWithNegativeSpeed.getNextStop().getId());

    testVehicleWithNegativeSpeed.move();
    assertEquals("test stop 1", testVehicleWithNegativeSpeed.getNextStop().getName());
    assertEquals(0, testVehicleWithNegativeSpeed.getNextStop().getId());
  }

  /**
   * Tests if move function works properly when train is completed.
   */
  @Test
  public void testMoveWhenCompleted() {

    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());
    testVehicle.move();

    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.move();
    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.move();
    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());

    testVehicle.move();
    assertEquals(null, testVehicle.getNextStop());

    testVehicle.move();
    assertEquals(true, testVehicle.isTripComplete());

  }

  /**
   * Tests if move function works properly with passenger.
   */
  @Test
  public void testMoveWithPassenger() {

    Passenger passenger = new Passenger(1, "testpassenger");
    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());
    testVehicle.move();

    testVehicle.loadPassenger(passenger);
    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.move();
    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.move();
    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());

    testVehicle.move();
    assertEquals(null, testVehicle.getNextStop());

  }


  /**
   * Tests if update function works properly.
   */
  @Test
  public void testUpdate() {

    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());
    testVehicle.update();

    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.update();
    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.update();
    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());

    testVehicle.update();
    assertNull(testVehicle.getNextStop());

  }

  /**
   * Tests if update function works properly with issue.
   */
  @Test
  public void testUpdateWithIssue() {

    assertEquals("test stop 2", testVehicleWithIssue.getNextStop().getName());
    assertEquals(1, testVehicleWithIssue.getNextStop().getId());
    testVehicleWithIssue.update();

    assertEquals("test stop 2", testVehicleWithIssue.getNextStop().getName());
    assertEquals(1, testVehicleWithIssue.getNextStop().getId());

    testVehicleWithIssue.update();
    assertEquals("test stop 2", testVehicleWithIssue.getNextStop().getName());
    assertEquals(1, testVehicleWithIssue.getNextStop().getId());

    testVehicleWithIssue.update();
    assertEquals("test stop 2", testVehicleWithIssue.getNextStop().getName());
    assertEquals(1, testVehicleWithIssue.getNextStop().getId());

  }

  /**
   * Tests if update function works properly with passenger.
   */
  @Test
  public void testUpdateWithPassenger() {

    Passenger passenger = new Passenger(1, "test passenger");
    testVehicle.loadPassenger(passenger);
    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());
    testVehicle.update();

    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.update();
    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.update();
    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());

    testVehicle.update();
    assertEquals(null, testVehicle.getNextStop());

  }

  /**
   * Test to see if observer got attached.
   */
  @Test
  public void testProvideInfo() {
    WebServerSession webServerSessionSpy = spy(WebServerSession.class);
    Session sessionDummy = mock(Session.class);
    webServerSessionSpy.onOpen(sessionDummy);
    VehicleConcreteSubject vehicleConcreteSubject = new VehicleConcreteSubject(webServerSessionSpy);
    doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));

    testVehicle.setVehicleSubject(vehicleConcreteSubject);
    testVehicle.update();
    testVehicle.provideInfo();

    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(webServerSessionSpy).sendJson(messageCaptor.capture());
    JsonObject commandToClient = messageCaptor.getValue();

    String command = commandToClient.get("command").getAsString();
    String expectedCommand = "observedVehicle";
    assertEquals(expectedCommand, command);

    String observedText = commandToClient.get("text").getAsString();
    String expectedText = "1" + System.lineSeparator()
        + "-----------------------------" + System.lineSeparator()
        + "* Type: " + System.lineSeparator()
        + "* Position: (-93.235071,44.973580)" + System.lineSeparator()
        + "* Passengers: 0" + System.lineSeparator()
        + "* CO2: 0" + System.lineSeparator();
    assertEquals(expectedText, observedText);
  }


  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanEach() {
    testVehicle = null;
  }

}
