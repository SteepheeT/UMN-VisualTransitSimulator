package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

public class SmallBusDecorator extends BusDecorator {
  private int red;
  private int green;
  private int blue;
  private int alpha;

  public SmallBusDecorator(SmallBus smallbus) {
    super(smallbus);
    setColor();
  }

  @Override
  public int getCurrentCO2Emission() {
    return (getPassengers().size()) + 1;
  }

  @Override
  public void setColor() {
    red = 122;
    green = 0;
    blue = 25;
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
