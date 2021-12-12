package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TrainStrategyDayTest {

  /**
   * Test constructor normal.
   */
  @Test
  public void testConstructor() {
    TrainStrategyDay trainStrategyDay = new TrainStrategyDay();
    assertEquals(0, trainStrategyDay.getCounter());
  }

  /**
   * Testing to get correct vehicle according to the strategy.
   */
  @Test
  public void testGetTypeOfVehicle() {
    StorageFacility storageFacility = new StorageFacility(0, 0, 3, 1);
    TrainStrategyDay trainStrategyDay = new TrainStrategyDay();
    String strToCmpr;
    for (int i = 0; i < 1; i++) {
      strToCmpr = trainStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, strToCmpr);
      strToCmpr = trainStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, strToCmpr);
      strToCmpr = trainStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, strToCmpr);
      strToCmpr = trainStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(DieselTrain.DIESEL_TRAIN_VEHICLE, strToCmpr);
    }

    StorageFacility storageFacility2 = new StorageFacility(0, 0, 0, 0);
    TrainStrategyDay trainStrategyDay2 = new TrainStrategyDay();
    String strToCmpr2;
    for (int i = 0; i < 1; i++) {
      strToCmpr2 = trainStrategyDay2.getTypeOfVehicle(storageFacility2);
      assertEquals(null, strToCmpr2);
    }

    StorageFacility storageFacility3 = new StorageFacility(0, 0, 3, 0);
    TrainStrategyDay trainStrategyDay3 = new TrainStrategyDay();
    String strToCmpr3;
    for (int i = 0; i < 1; i++) {
      strToCmpr3 = trainStrategyDay3.getTypeOfVehicle(storageFacility3);
      assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, strToCmpr3);
      strToCmpr3 = trainStrategyDay3.getTypeOfVehicle(storageFacility3);
      assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, strToCmpr3);
      strToCmpr3 = trainStrategyDay3.getTypeOfVehicle(storageFacility3);
      assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, strToCmpr3);
      strToCmpr3 = trainStrategyDay3.getTypeOfVehicle(storageFacility3);
      assertEquals(null, strToCmpr3);
    }
  }
}
