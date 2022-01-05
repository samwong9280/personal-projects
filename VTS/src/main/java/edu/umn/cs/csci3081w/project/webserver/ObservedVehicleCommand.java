package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;

public class ObservedVehicleCommand extends SimulatorCommand {
  private VisualTransitSimulator simulator;

  public ObservedVehicleCommand(VisualTransitSimulator simulator) {
    this.simulator = simulator;
  }

  @Override
  public void execute(WebServerSession session, JsonObject command) {
    session.sendJson(command);
  }
}
