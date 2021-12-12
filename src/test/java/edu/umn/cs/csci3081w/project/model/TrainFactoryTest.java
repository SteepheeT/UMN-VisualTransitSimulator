package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrainFactoryTest {

  private TrainFactory trainFactoryDay;
  private TrainFactory trainFactoryNight;

  /**
   * Set up before each test.
   */
  @BeforeEach
  public void setUp() {
    StorageFacility storageFacility = new StorageFacility(0, 0, 3, 3);
    trainFactoryDay = new TrainFactory(storageFacility, new Counter(), 9);
    trainFactoryNight = new TrainFactory(storageFacility, new Counter(), 21);
  }

  /**
   * Testing the constructor.
   */
  @Test
  public void testConstructorDay() {
    assertTrue(trainFactoryDay.getGenerationStrategy() instanceof TrainStrategyDay);
  }

  @Test
  public void testConstructorNight() {
    assertTrue(trainFactoryNight.getGenerationStrategy() instanceof TrainStrategyNight);
  }

  @Test
  public void testConstructor() {
    StorageFacility storageFacilityDummy = mock(StorageFacility.class);

    TrainFactory trainFactory = new TrainFactory(storageFacilityDummy, new Counter(), 1);

    assertTrue(trainFactory.getGenerationStrategy() instanceof TrainStrategyNight);

  }

  /**
   * Testing if generated vehicle is working according to strategy.
   */
  @Test
  public void testGenerateVehicle() {
    List<Stop> stopsIn = new ArrayList<Stop>();
    Stop stop1 = new Stop(0, "test stop 1",
        new Position(-93.243774, 44.972392));
    Stop stop2 = new Stop(1, "test stop 2",
        new Position(-93.235071, 44.973580));
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

    Line line = new Line(10000, "testLine", "TRAIN", testRouteOut, testRouteIn,
        new Issue());


    Vehicle vehicleDay1 = trainFactoryDay.generateVehicle(line);
    Vehicle vehicleDay2 = trainFactoryDay.generateVehicle(line);
    Vehicle vehicleNight1 = trainFactoryNight.generateVehicle(line);
    Vehicle vehicleNight2 = trainFactoryNight.generateVehicle(line);

    assertTrue(vehicleDay2 instanceof ElectricTrain
        || vehicleDay2 instanceof ElectricTrainDecorator);
    assertTrue(vehicleNight2 instanceof DieselTrain
        || vehicleNight2 instanceof DieselTrainDecorator);

    //null type
    Vehicle vehicleNight3 = trainFactoryNight.generateVehicle(line);
    Vehicle vehicleNightNull = trainFactoryNight.generateVehicle(line);
    assertNull(vehicleNightNull);
    Vehicle vehicleDay3 = trainFactoryDay.generateVehicle(line);
    Vehicle vehicleDayNull = trainFactoryDay.generateVehicle(line);
    assertNull(vehicleDayNull);
  }


  /**
   * Testing if vehicle got returned.
   */
  @Test
  public void testReturnVehicleElectricTrain() {
    Train testTrain = mock(DieselTrainDecorator.class);

    assertEquals(3, trainFactoryDay.getStorageFacility().getElectricTrainsNum());
    assertEquals(3, trainFactoryDay.getStorageFacility().getDieselTrainsNum());
    trainFactoryDay.returnVehicle(testTrain);
    assertEquals(3, trainFactoryDay.getStorageFacility().getElectricTrainsNum());
    assertEquals(4, trainFactoryDay.getStorageFacility().getDieselTrainsNum());

    Train testTrain2 = mock(ElectricTrainDecorator.class);

    assertEquals(3, trainFactoryDay.getStorageFacility().getElectricTrainsNum());
    assertEquals(4, trainFactoryDay.getStorageFacility().getDieselTrainsNum());
    trainFactoryNight.returnVehicle(testTrain2);
    assertEquals(4, trainFactoryDay.getStorageFacility().getElectricTrainsNum());
    assertEquals(4, trainFactoryNight.getStorageFacility().getDieselTrainsNum());

    Bus testBus = mock(SmallBusDecorator.class);

    assertEquals(4, trainFactoryDay.getStorageFacility().getElectricTrainsNum());
    assertEquals(4, trainFactoryNight.getStorageFacility().getDieselTrainsNum());
    trainFactoryDay.returnVehicle(testBus);
    assertEquals(4, trainFactoryDay.getStorageFacility().getElectricTrainsNum());
    assertEquals(4, trainFactoryNight.getStorageFacility().getDieselTrainsNum());


  }
}
