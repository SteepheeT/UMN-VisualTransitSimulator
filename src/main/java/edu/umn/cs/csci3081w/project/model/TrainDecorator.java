package edu.umn.cs.csci3081w.project.model;

public abstract class TrainDecorator extends Train {

    public TrainDecorator(Train train) {
        super(train.getId(), train.getLine(), train.getCapacity(), train.getSpeed());
    }

    public abstract void setColor();

    public abstract int[] getColor();
}
