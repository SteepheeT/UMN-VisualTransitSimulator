package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

public abstract class Train extends Vehicle {
  public static final String TRAIN_VEHICLE = "TRAIN_VEHICLE";

  /**
   * Constructor for a train.
   *
   * @param id       train identifier
   * @param line     route of in/out bound
   * @param capacity capacity of the train
   * @param speed    speed of the train
   */
  public Train(int id, Line line, int capacity, double speed) {
    super(id, line, capacity, speed, new PassengerLoader(), new PassengerUnloader());
  }

  /**
   * Report the information of current train.
   * @param out stream for printing
   */
  public void report(PrintStream out) {

  }
}
