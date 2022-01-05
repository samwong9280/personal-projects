package edu.umn.cs.csci3081w.project.model;

public abstract class VehicleColorDecorator extends Vehicle {
  protected Vehicle vehicle;

  /**
   * constructor for initializing the decorator and vehicle inside.
   * @param vehicle vehicle to be passed to the decorator
   */
  public VehicleColorDecorator(Vehicle vehicle) {
    super(vehicle.getId(), vehicle.getLine(), vehicle.getCapacity(), vehicle.getSpeed(),
            vehicle.getPassengerLoader(), vehicle.getPassengerUnloader());
    this.vehicle = vehicle;
  }

}
