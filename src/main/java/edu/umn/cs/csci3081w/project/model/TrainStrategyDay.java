package edu.umn.cs.csci3081w.project.model;

public class TrainStrategyDay implements GenerationStrategy {
  private int counter;

  /**
   * Constructor for the bus strategy during daytime.
   */
  public TrainStrategyDay() {
    this.counter = 0;
  }

  /**
   * Get type of current Vehicle.
   * @param storageFacility storage facility object
   * @return string represent the type of current vehicle
   */
  @Override
  public String getTypeOfVehicle(StorageFacility storageFacility) {
    String typeOfVehicle = null;
    if (counter < 3) {
      if (storageFacility.getElectricTrainsNum() > 0) {
        typeOfVehicle = ElectricTrain.ELECTRIC_TRAIN_VEHICLE;
      }
    } else {
      if (storageFacility.getDieselTrainsNum() > 0) {
        typeOfVehicle = DieselTrain.DIESEL_TRAIN_VEHICLE;
      }
    }

    if (typeOfVehicle != null) {
      counter++;
      counter = counter % 4;
    }

    return typeOfVehicle;
  }

  /**
   * Get counter.
   * @return counter number
   */
  public int getCounter() {
    return counter;
  }
}
