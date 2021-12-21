package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

public class DieselTrainDecorator extends TrainDecorator {
  private int red;
  private int green;
  private int blue;
  private int alpha;

  /**
   * Constructor for the Diesel Train Decorator.
   * @param dieselTrain the diesel train object
   */
  public DieselTrainDecorator(DieselTrain dieselTrain) {
    super(dieselTrain);
    setColor();
  }

  /**
   * Method to get the emission for diesel train.
   * @return integer represent the co2 emission
   */
  @Override
  public int getCurrentCO2Emission() {
    return (2 * getPassengers().size()) + 6;
  }

  /**
   * Method can set color for current train to yellow.
   */
  @Override
  public void setColor() {
    red = 255;
    green = 204;
    blue = 51;
    alpha = 255;
  }

  /**
   * get current train's color in RGB format.
   * @return integer array represent RGB value
   */
  @Override
  public int[] getColor() {
    int[] color = new int[]{red, green, blue, alpha};
    return color;
  }

  /**
   * When train stop set it transparency.
   */
  @Override
  public void setVehicleAlphaStop() {
    alpha = 155;
  }

  /**
   * When train move set it not have a transparent color.
   */
  @Override
  public void setVehicleAlphaMove() {
    alpha = 255;
  }
}
