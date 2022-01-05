package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

public class SmallBusColorDecorator extends VehicleColorDecorator {

  /**
   * assigns respective rgb values to small bus.
   * @param vehicle vehicle to be passed to decorator
   */
  public SmallBusColorDecorator(Vehicle vehicle) {
    super(vehicle);
    red = 122;
    green = 0;
    blue = 25;
  }

  @Override
  public void report(PrintStream out) {
    vehicle.report(out);
  }

  @Override
  public int getCurrentCO2Emission() {
    return vehicle.getCurrentCO2Emission();
  }

  @Override
  public String getType() {
    return vehicle.getType();
  }
}