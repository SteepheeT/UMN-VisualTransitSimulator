package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TrainStrategyNightTest {

  /**
   * Test constructor normal.
   */
  @Test
  public void testConstructor() {
    TrainStrategyNight trainStrategyNight = new TrainStrategyNight();
    assertEquals(0, trainStrategyNight.getCounter());
  }

  /**
   * Testing to get correct vehicle according to the strategy.
   */
  @Test
  public void testGetTypeOfVehicle() {
    StorageFacility storageFacility = new StorageFacility(0, 0, 1, 1);
    TrainStrategyNight trainStrategyNight = new TrainStrategyNight();
    String strToCmpr;
    for (int i = 0; i < 1; i++) {
      strToCmpr = trainStrategyNight.getTypeOfVehicle(storageFacility);
      assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, strToCmpr);
      strToCmpr = trainStrategyNight.getTypeOfVehicle(storageFacility);
      assertEquals(DieselTrain.DIESEL_TRAIN_VEHICLE, strToCmpr);
    }

    StorageFacility storageFacility2 = new StorageFacility(0, 0, 0, 0);
    TrainStrategyNight trainStrategyNight2 = new TrainStrategyNight();
    String strToCmpr2;
    for (int i = 0; i < 1; i++) {
      strToCmpr2 = trainStrategyNight2.getTypeOfVehicle(storageFacility2);
      assertEquals(null, strToCmpr2);
    }

    StorageFacility storageFacility3 = new StorageFacility(0, 0, 1, 0);
    TrainStrategyNight trainStrategyNight3 = new TrainStrategyNight();
    String strToCmpr3;
    for (int i = 0; i < 1; i++) {
      strToCmpr3 = trainStrategyNight3.getTypeOfVehicle(storageFacility3);
      assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, strToCmpr3);
      strToCmpr3 = trainStrategyNight3.getTypeOfVehicle(storageFacility3);
      assertEquals(null, strToCmpr3);
    }
  }
}
