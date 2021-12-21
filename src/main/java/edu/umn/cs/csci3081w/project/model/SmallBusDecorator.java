package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

public class SmallBusDecorator extends BusDecorator {
  private int red;
  private int green;
  private int blue;
  private int alpha;

  /**
   * Constructor for the small bus Decorator.
   * @param smallBus the electric train object
   */
  public SmallBusDecorator(SmallBus smallBus) {
    super(smallBus);
    setColor();
  }

  /**
   * Method to get the emission for small bus.
   * @return integer represent the co2 emission
   */
  @Override
  public int getCurrentCO2Emission() {
    return (getPassengers().size()) + 1;
  }

  /**
   * Method can set color for current bus to maroon.
   */
  @Override
  public void setColor() {
    red = 122;
    green = 0;
    blue = 25;
    alpha = 255;
  }

  /**
   * get current bus's color in RGB format.
   * @return integer array represent RGB value
   */
  @Override
  public int[] getColor() {
    int[] color = new int[]{red, green, blue, alpha};
    return color;
  }

  /**
   * When bus stop set it transparency.
   */
  @Override
  public void setVehicleAlphaStop() {
    alpha = 155;
  }

  /**
   * When bus move set it not have a transparent color.
   */
  @Override
  public void setVehicleAlphaMove() {
    alpha = 255;
  }
}
