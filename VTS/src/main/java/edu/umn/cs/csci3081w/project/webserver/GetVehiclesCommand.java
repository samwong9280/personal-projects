package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.*;
import java.util.List;

public class GetVehiclesCommand extends SimulatorCommand {

  private VisualTransitSimulator simulator;

  public GetVehiclesCommand(VisualTransitSimulator simulator) {
    this.simulator = simulator;
  }

  /**
   * Retrieves vehicles information from the simulation.
   *
   * @param session current simulation session
   * @param command the get vehicles command content
   */
  @Override
  public void execute(WebServerSession session, JsonObject command) {
    List<Vehicle> vehicles = simulator.getActiveVehicles();
    JsonObject data = new JsonObject();
    data.addProperty("command", "updateVehicles");
    JsonArray vehiclesArray = new JsonArray();
    for (int i = 0; i < vehicles.size(); i++) {
      Vehicle currVehicle = vehicles.get(i);
      JsonObject s = new JsonObject();
      s.addProperty("id", currVehicle.getId());
      s.addProperty("numPassengers", currVehicle.getPassengers().size());
      s.addProperty("capacity", currVehicle.getCapacity());
      String vehicleType = currVehicle.getType();
      s.addProperty("type", vehicleType);
      s.addProperty("co2", currVehicle.getCurrentCO2Emission());
      JsonObject positionJsonObject = new JsonObject();
      positionJsonObject.addProperty("longitude", currVehicle.getPosition().getLongitude());
      positionJsonObject.addProperty("latitude", currVehicle.getPosition().getLatitude());
      s.add("position", positionJsonObject);
      JsonObject colorJsonObject = new JsonObject();
      colorJsonObject.addProperty("r", currVehicle.getR());
      colorJsonObject.addProperty("g", currVehicle.getG());
      colorJsonObject.addProperty("b", currVehicle.getB());
      colorJsonObject.addProperty("alpha", currVehicle.getAcvVal());
      s.add("color", colorJsonObject);
      vehiclesArray.add(s);
    }
    data.add("vehicles", vehiclesArray);
    session.sendJson(data);
  }

}
