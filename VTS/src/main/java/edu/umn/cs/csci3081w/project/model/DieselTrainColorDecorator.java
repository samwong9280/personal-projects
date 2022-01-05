package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

public class DieselTrainColorDecorator extends VehicleColorDecorator {

  /**
   * assigns respective rgb values to diesel train.
   * @param vehicle vehicle to be passed to decorator
   */
  public DieselTrainColorDecorator(Vehicle vehicle) {
    super(vehicle);
    red = 255;
    green = 204;
    blue = 51;
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
