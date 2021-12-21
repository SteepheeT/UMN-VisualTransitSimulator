package edu.umn.cs.csci3081w.project.model;

public abstract class TrainDecorator extends Train {
  /**
   * Constructor of Decorator for train class.
   *
   * @param train the train object
   */
  public TrainDecorator(Train train) {
    super(train.getId(), train.getLine(), train.getCapacity(), train.getSpeed());
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
