package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

public class DieselTrainDecorator extends TrainDecorator {
  private int red;
  private int green;
  private int blue;
  private int alpha;

  public DieselTrainDecorator(DieselTrain dieselTrain) {
    super(dieselTrain);
    setColor();
  }

  @Override
  public int getCurrentCO2Emission() {
    return (2 * getPassengers().size()) + 6;
  }

  @Override
  public void setColor() {
    red = 255;
    green = 204;
    blue = 51;
    alpha = 255;
  }

  @Override
  public int[] getColor() {
    int[] color = new int[]{red, green, blue, alpha};
    return color;
  }

  @Override
  public void setVehicleAlphaStop() {
    alpha = 155;
  }

  @Override
  public void setVehicleAlphaMove() {
    alpha = 255;
  }
}
