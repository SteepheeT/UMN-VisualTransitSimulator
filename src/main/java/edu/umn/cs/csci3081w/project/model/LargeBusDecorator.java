package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

public class LargeBusDecorator extends BusDecorator {
  private int red;
  private int green;
  private int blue;
  private int alpha;

  /**
   * Constructor for the larger bus Decorator.
   * @param largeBus the electric train object
   */
  public LargeBusDecorator(LargeBus largeBus) {
    super(largeBus);
    setColor();
  }

  /**
   * Method to get the emission for larger bus.
   * @return integer represent the co2 emission
   */
  @Override
  public int getCurrentCO2Emission() {
    return (getPassengers().size()) + 3;
  }

  /**
   * Method can set color for current bus to pink.
   */
  @Override
  public void setColor() {
    red = 239;
    green = 130;
    blue = 238;
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
