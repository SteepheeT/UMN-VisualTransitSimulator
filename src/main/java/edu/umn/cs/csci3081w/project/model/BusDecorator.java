package edu.umn.cs.csci3081w.project.model;

public abstract class BusDecorator extends Bus {

    public BusDecorator(Bus busObj) {
        super(busObj.getId(), busObj.getLine(), busObj.getCapacity(), busObj.getSpeed());
    }

    public abstract void setColor();

    public abstract int[] getColor();
}
