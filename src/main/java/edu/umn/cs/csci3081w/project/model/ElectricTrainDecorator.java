package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

public class ElectricTrainDecorator extends TrainDecorator {
  private int red;
  private int green;
  private int blue;
  private int alpha;

  /**
   * Constructor for the electric Train Decorator.
   * @param electricTrain the electric train object
   */
  public ElectricTrainDecorator(ElectricTrain electricTrain) {
    super(electricTrain);
    setColor();
  }

  /**
   * Method to get the emission for electric train.
   * @return integer represent the co2 emission
   */
  @Override
  public int getCurrentCO2Emission() {
    return 0;
  }

  /**
   * Method can set color for current train to green.
   */
  @Override
  public void setColor() {
    red = 60;
    green = 179;
    blue = 113;
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
