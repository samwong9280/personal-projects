package edu.umn.cs.csci3081w.project.webserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.PassengerFactory;
import edu.umn.cs.csci3081w.project.model.RandomPassengerGenerator;
import edu.umn.cs.csci3081w.project.model.Vehicle;
import java.util.ArrayList;
import javax.websocket.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class WebServerSessionTest {
  /**
   * Setup deterministic operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    PassengerFactory.DETERMINISTIC = true;
    PassengerFactory.DETERMINISTIC_NAMES_COUNT = 0;
    PassengerFactory.DETERMINISTIC_DESTINATION_COUNT = 0;
    RandomPassengerGenerator.DETERMINISTIC = true;
  }

  /**
   * Test command for initializing the simulation.
   */
  @Test
  public void testSimulationInitialization() {
    WebServerSession webServerSessionSpy = spy(WebServerSession.class);
    doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
    Session sessionDummy = mock(Session.class);
    webServerSessionSpy.onOpen(sessionDummy);
    JsonObject commandFromClient = new JsonObject();
    commandFromClient.addProperty("command", "initLines");
    webServerSessionSpy.onMessage(commandFromClient.toString());
    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(webServerSessionSpy).sendJson(messageCaptor.capture());
    JsonObject commandToClient = messageCaptor.getValue();
    assertEquals("2", commandToClient.get("numLines").getAsString());
  }


  /**
   * Test command for getting Routes.
   */
  @Test
  public void testGetRoutes() {
    WebServerSession webServerSessionSpy = spy(WebServerSession.class);
    doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
    Session sessionDummy = mock(Session.class);
    webServerSessionSpy.onOpen(sessionDummy);
    JsonObject commandFromClient = new JsonObject();
    commandFromClient.addProperty("command", "getRoutes");
    webServerSessionSpy.onMessage(commandFromClient.toString());
    System.out.println(commandFromClient.toString());
    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(webServerSessionSpy).sendJson(messageCaptor.capture());
    JsonObject commandToClient = messageCaptor.getValue();
    assertEquals(4, commandToClient.get("routes").getAsJsonArray().size());
  }

  /**
   * Test command for getting Vehicles.
   */
  @Test
  public void testGetVehicles() {
    WebServerSession webServerSessionSpy = spy(WebServerSession.class);
    doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
    Session sessionDummy = mock(Session.class);
    webServerSessionSpy.onOpen(sessionDummy);
    JsonObject commandFromClient = new JsonObject();
    commandFromClient.addProperty("command", "getVehicles");
    JsonObject command2 = new JsonObject();
    command2.addProperty("command", "start");
    command2.addProperty("numTimeSteps", 5);
    JsonArray times = new JsonArray();
    times.add(1);
    times.add(1);
    command2.add("timeBetweenVehicles", times);
    webServerSessionSpy.onMessage(command2.toString());
    JsonObject command3 = new JsonObject();
    command3.addProperty("command", "update");
    webServerSessionSpy.onMessage(command3.toString());
    webServerSessionSpy.onMessage(commandFromClient.toString());
    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(webServerSessionSpy).sendJson(messageCaptor.capture());
    JsonObject commandToClient = messageCaptor.getValue();
    assertEquals(2, commandToClient.get("vehicles").getAsJsonArray().size());
  }

  /**
   * Tests VTS by simulating running it.
   */
  @Test
  public void testVts() {
    WebServerSession webServerSessionSpy = spy(WebServerSession.class);
    doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
    Session sessionDummy = mock(Session.class);
    webServerSessionSpy.onOpen(sessionDummy);
    JsonObject commandFromClient = new JsonObject();
    commandFromClient.addProperty("command", "getVehicles");
    JsonObject command2 = new JsonObject();
    command2.addProperty("command", "start");
    command2.addProperty("numTimeSteps", 100);
    JsonArray times = new JsonArray();
    times.add(2);
    times.add(2);
    command2.add("timeBetweenVehicles", times);
    webServerSessionSpy.onMessage(command2.toString());
    JsonObject command3 = new JsonObject();
    command3.addProperty("command", "update");
    for (int i = 0; i < 102; i++) {
      webServerSessionSpy.onMessage(command3.toString());
      if (i == 90) {
        JsonObject commandIssue = new JsonObject();
        commandIssue.addProperty("command", "lineIssue");
        commandIssue.addProperty("id", 10000);
        webServerSessionSpy.onMessage(commandIssue.toString());
        JsonObject commandIssue2 = new JsonObject();
        commandIssue2.addProperty("command", "lineIssue");
        commandIssue2.addProperty("id", 10001);
        webServerSessionSpy.onMessage(commandIssue2.toString());
      }
    }
    webServerSessionSpy.onMessage(commandFromClient.toString());
    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(webServerSessionSpy).sendJson(messageCaptor.capture());
    JsonObject commandToClient = messageCaptor.getValue();
    assertEquals(8, commandToClient.get("vehicles").getAsJsonArray().size());
  }

  /**
   * Tests register vehicle command.
   */
  @Test
  public void testRegisterVehicle() {
    WebServerSession webServerSessionSpy = spy(WebServerSession.class);
    doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
    Session sessionDummy = mock(Session.class);
    webServerSessionSpy.onOpen(sessionDummy);
    JsonObject commandFromClient = new JsonObject();
    commandFromClient.addProperty("command", "getVehicles");
    JsonObject command2 = new JsonObject();
    command2.addProperty("command", "start");
    command2.addProperty("numTimeSteps", 100);
    JsonArray times = new JsonArray();
    times.add(1);
    times.add(1);
    command2.add("timeBetweenVehicles", times);
    webServerSessionSpy.onMessage(command2.toString());
    JsonObject command3 = new JsonObject();
    command3.addProperty("command", "update");
    for (int i = 0; i < 100; i++) {
      webServerSessionSpy.onMessage(command3.toString());
    }
    webServerSessionSpy.onMessage(commandFromClient.toString());
    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(webServerSessionSpy).sendJson(messageCaptor.capture());
    JsonObject commandToClient = messageCaptor.getValue();
    JsonArray arr = commandToClient.get("vehicles").getAsJsonArray();
    JsonObject vehicle = arr.get(1).getAsJsonObject();
    int id = vehicle.get("id").getAsInt();
    JsonObject commandReg = new JsonObject();
    commandReg.addProperty("command", "registerVehicle");
    commandReg.addProperty("id", id);
    webServerSessionSpy.onMessage(commandReg.toString());
    assertEquals(9, commandToClient.get("vehicles").getAsJsonArray().size());
  }

}
