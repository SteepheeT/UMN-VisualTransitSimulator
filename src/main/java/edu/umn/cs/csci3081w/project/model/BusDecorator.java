package edu.umn.cs.csci3081w.project.model;

public abstract class BusDecorator extends Bus {
  /**
   * Constructor of Decorator for bus class.
   *
   * @param busObj the bus object
   */
  public BusDecorator(Bus busObj) {
    super(busObj.getId(), busObj.getLine(), busObj.getCapacity(), busObj.getSpeed());
  }

  /**
   * Abstract method for set color.
   */
  public abstract void setColor();

  /**
   * Abstract method for get color.
   *
   * @return the integer array represent RGB value
   */
  public abstract int[] getColor();

}
