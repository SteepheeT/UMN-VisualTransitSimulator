package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

public class ElectricTrainDecorator extends TrainDecorator {
  private int red;
  private int green;
  private int blue;
  private int alpha;

  public ElectricTrainDecorator(ElectricTrain electricTrain) {
    super(electricTrain);
    setColor();
  }

  @Override
  public int getCurrentCO2Emission() {
    return 0;
  }

  @Override
  public void setColor() {
    red = 60;
    green = 179;
    blue = 113;
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
