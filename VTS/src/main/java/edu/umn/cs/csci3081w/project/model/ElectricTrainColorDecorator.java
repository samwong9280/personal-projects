package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

public class ElectricTrainColorDecorator extends VehicleColorDecorator {

  /**
   * assigns respective rgb values to electric train.
   * @param vehicle vehicle to be passed to decorator
   */
  public ElectricTrainColorDecorator(Vehicle vehicle) {
    super(vehicle);
    red = 60;
    green = 179;
    blue = 113;
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