package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

public class LargeBusColorDecorator extends VehicleColorDecorator {

  /**
   * assigns respective rgb values to large bus.
   * @param vehicle vehicle to be passed to decorator
   */
  public LargeBusColorDecorator(Vehicle vehicle) {
    super(vehicle);
    red = 239;
    green = 130;
    blue = 238;
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