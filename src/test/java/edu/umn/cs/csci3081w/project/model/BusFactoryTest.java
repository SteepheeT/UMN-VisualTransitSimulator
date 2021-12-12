package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BusFactoryTest {
  private StorageFacility storageFacility1;
  private StorageFacility storageFacility2;
  private BusFactory busFactoryDay;
  private BusFactory busFactoryNight;
  private TrainFactory trainFactoryDay;

  /**
   * Set up before each test.
   */
  @BeforeEach
  public void setUp() {
    storageFacility1 = new StorageFacility(2, 3, 1, 1);
    storageFacility2 = new StorageFacility(2, 3, 1, 1);
    busFactoryDay = new BusFactory(storageFacility1, new Counter(), 9);
    busFactoryNight = new BusFactory(storageFacility2, new Counter(), 20);
    trainFactoryDay = new TrainFactory(storageFacility1, new Counter(), 9);
    BusFactory busFactoryTimeNegative = new BusFactory(storageFacility1, new Counter(), -9);
  }

  /**
   * Testing the constructor.
   */
  @Test
  public void testConstructor() {
    assertTrue(busFactoryDay.getGenerationStrategy() instanceof BusStrategyDay);
  }

  /**
   * Testing if generated vehicle is working according to strategy.
   */
  @Test
  public void testGenerateVehicle() {
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

    Route testRouteIn = new Route(0, "testRouteIn",
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

    Route testRouteOut = new Route(1, "testRouteOut",
        stopsOut, distancesOut, generatorOut);

    Line line = new Line(10000, "testLine", "BUS", testRouteOut, testRouteIn,
        new Issue());

    Vehicle vehicle = busFactoryDay.generateVehicle(line);
    assertTrue(vehicle instanceof LargeBus || vehicle instanceof LargeBusDecorator);

    Vehicle vehicle2 = busFactoryNight.generateVehicle(line);
    assertTrue(vehicle2 instanceof LargeBus || vehicle instanceof LargeBusDecorator);

    StorageFacility storageFacility2 = new StorageFacility(2, 1, 0, 0);
    GenerationStrategy strategyStub = mock(GenerationStrategy.class);
    BusFactory busFactoryDay3 = new BusFactory(storageFacility1, new Counter(), 9);
    busFactoryDay3.setGenerationStrategy(strategyStub);
    when(strategyStub.getTypeOfVehicle(storageFacility2)).thenReturn(null);

    Vehicle vehicle4 = busFactoryDay3.generateVehicle(line);
    assertEquals(null, vehicle4);
  }

  /**
   * Testing if vehicle got returned.
   */
  @Test
  public void testReturnVehicleLargeBus() {
    Bus testBus = mock(LargeBusDecorator.class);

    assertEquals(2, busFactoryDay.getStorageFacility().getSmallBusesNum());
    assertEquals(3, busFactoryDay.getStorageFacility().getLargeBusesNum());
    busFactoryDay.returnVehicle(testBus);
    assertEquals(2, busFactoryDay.getStorageFacility().getSmallBusesNum());
    assertEquals(4, busFactoryDay.getStorageFacility().getLargeBusesNum());

    Bus testBus2 = mock(SmallBusDecorator.class);

    assertEquals(2, busFactoryDay.getStorageFacility().getSmallBusesNum());
    assertEquals(4, busFactoryDay.getStorageFacility().getLargeBusesNum());
    busFactoryDay.returnVehicle(testBus2);
    assertEquals(3, busFactoryDay.getStorageFacility().getSmallBusesNum());
    assertEquals(4, busFactoryDay.getStorageFacility().getLargeBusesNum());

    Train testTrain1 = mock(DieselTrainDecorator.class);

    assertEquals(3, busFactoryDay.getStorageFacility().getSmallBusesNum());
    assertEquals(4, busFactoryDay.getStorageFacility().getLargeBusesNum());
    busFactoryDay.returnVehicle(testTrain1);
    assertEquals(3, busFactoryDay.getStorageFacility().getSmallBusesNum());
    assertEquals(4, busFactoryDay.getStorageFacility().getLargeBusesNum());

  }
}
