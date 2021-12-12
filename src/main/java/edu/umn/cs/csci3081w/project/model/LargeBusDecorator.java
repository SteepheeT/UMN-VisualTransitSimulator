package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

public class LargeBusDecorator extends BusDecorator {
  private int red;
  private int green;
  private int blue;
  private int alpha;

  public LargeBusDecorator(LargeBus largebus) {
    super(largebus);
    setColor();
  }

  @Override
  public int getCurrentCO2Emission() {
    return (getPassengers().size()) + 3;
  }

  @Override
  public void setColor() {
    red = 239;
    green = 130;
    blue = 238;
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
