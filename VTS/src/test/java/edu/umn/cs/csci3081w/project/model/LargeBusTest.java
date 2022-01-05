package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.webserver.WebServerSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class LargeBusTest {

  private Bus testBus;
  private Route testRouteIn;
  private Route testRouteOut;
  private WebServerSession session;


  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    List<Stop> stopsIn = new ArrayList<Stop>();
    Stop stop1 = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop stop2 = new Stop(1, "test stop 2", new Position(-93.235071, 44.973580));
    stopsIn.add(stop1);
    stopsIn.add(stop2);
    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.843774422231134);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.025);
    probabilitiesIn.add(0.3);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(stopsIn, probabilitiesIn);

    testRouteIn = new Route(0, "testRouteIn",
        stopsIn, distancesIn, generatorIn);

    List<Stop> stopsOut = new ArrayList<Stop>();
    stopsOut.add(stop2);
    stopsOut.add(stop1);
    List<Double> distancesOut = new ArrayList<>();
    distancesOut.add(0.843774422231134);
    List<Double> probabilitiesOut = new ArrayList<Double>();
    probabilitiesOut.add(0.3);
    probabilitiesOut.add(.025);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(stopsOut, probabilitiesOut);

    testRouteOut = new Route(1, "testRouteOut",
        stopsOut, distancesOut, generatorOut);

    testBus = new LargeBus(1, new Line(10000, "testLine", "BUS", testRouteOut, testRouteIn,
        new Issue()), 3, 1.0);
    session = spy(WebServerSession.class);
    doNothing().when(session).sendJson(Mockito.isA(JsonObject.class));
    testBus.setVehicleSubject(new VehicleConcreteSubject(session));
  }

  /**
   * Tests constructor.
   */
  @Test
  public void testConstructor() {
    assertEquals(1, testBus.getId());
    assertEquals("testRouteOut1", testBus.getName());
    assertEquals(3, testBus.getCapacity());
    assertEquals(1, testBus.getSpeed());
    assertEquals(testRouteOut, testBus.getLine().getOutboundRoute());
    assertEquals(testRouteIn, testBus.getLine().getInboundRoute());
  }

  /**
   * Tests if updateDistance function works properly.
   */
  @Test
  public void testReport() {
    testBus.loadPassenger(new Passenger(2, "Poggerman"));
    testBus.move();
    try {
      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      testBus.report(testStream);
      outputStream.flush();
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();
      String strToCompare =
          "####Large Bus Info Start####" + System.lineSeparator()
              + "ID: 1" + System.lineSeparator()
              + "Name: testRouteOut1" + System.lineSeparator()
              + "Speed: 1.0" + System.lineSeparator()
              + "Capacity: 3" + System.lineSeparator()
              + "Position: 44.97358,-93.235071" + System.lineSeparator()
              + "Distance to next stop: 0.843774422231134" + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num of passengers: 1" + System.lineSeparator()
              + "####Passenger Info Start####" + System.lineSeparator()
              + "Name: Poggerman" + System.lineSeparator()
              + "Destination: 2" + System.lineSeparator()
              + "Wait at stop: 0" + System.lineSeparator()
              + "Time on vehicle: 1" + System.lineSeparator()
              + "####Passenger Info End####" + System.lineSeparator()
              + "****Passengers Info End****" + System.lineSeparator()
              + "####Large Bus Info End####" + System.lineSeparator();
      assertEquals(strToCompare, data);
    } catch (IOException ioe) {
      fail();
    }
  }

  /**
   * Tests if updateDistance function works properly with a passenger.
   */
  @Test
  public void testReportWithPassenger() {
    testBus.move();
    try {
      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      testBus.report(testStream);
      outputStream.flush();
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();
      String strToCompare =
          "####Large Bus Info Start####" + System.lineSeparator()
              + "ID: 1" + System.lineSeparator()
              + "Name: testRouteOut1" + System.lineSeparator()
              + "Speed: 1.0" + System.lineSeparator()
              + "Capacity: 3" + System.lineSeparator()
              + "Position: 44.97358,-93.235071" + System.lineSeparator()
              + "Distance to next stop: 0.843774422231134" + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num of passengers: 0" + System.lineSeparator()
              + "****Passengers Info End****" + System.lineSeparator()
              + "####Large Bus Info End####" + System.lineSeparator();
      assertEquals(strToCompare, data);
    } catch (IOException ioe) {
      fail();
    }
  }

  /**
   * Test the co2 calculation for a bus.
   */
  @Test
  public void testCurrentCO2Emission() {
    assertEquals(3, testBus.getCurrentCO2Emission());
    Passenger testPassenger1 = new Passenger(3, "testPassenger1");
    testBus.loadPassenger(testPassenger1);
    assertEquals(4, testBus.getCurrentCO2Emission());
  }

  /**
   * Test if provideInfo works.
   */
  @Test
  public void testProvideInfo() {
    testBus.update();
    testBus.provideInfo();
    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(session).sendJson(messageCaptor.capture());
    JsonObject message = messageCaptor.getValue();
    String expectedText = "1" + System.lineSeparator()
        + "-----------------------------" + System.lineSeparator()
        + "* Type: LARGE_BUS_VEHICLE" + System.lineSeparator()
        + "* Position: (-93.235071,44.973580)" + System.lineSeparator()
        + "* Passengers: 0" + System.lineSeparator()
        + "* CO2: 3" + System.lineSeparator();
    assertEquals(expectedText, message.get("text").getAsString());
  }

  /**
   * Test if carbon emission history is accurate.
   */
  @Test
  public void testCarbonHistory() {
    testBus.update();
    testBus.update();
    testBus.update();
    testBus.provideInfo();
    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(session).sendJson(messageCaptor.capture());
    JsonObject message = messageCaptor.getValue();
    String expectedText = "1" + System.lineSeparator()
        + "-----------------------------" + System.lineSeparator()
        + "* Type: LARGE_BUS_VEHICLE" + System.lineSeparator()
        + "* Position: (-93.243774,44.972392)" + System.lineSeparator()
        + "* Passengers: 0" + System.lineSeparator()
        + "* CO2: 3, 3, 3" + System.lineSeparator();
    assertEquals(expectedText, message.get("text").getAsString());
  }

  /**
   * test if carbon emission history is accurate for 0 length list.
   */
  @Test
  public void testCarbonHistory0len() {
    testBus.provideInfo();
    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(session).sendJson(messageCaptor.capture());
    JsonObject message = messageCaptor.getValue();
    String expectedText = "1" + System.lineSeparator()
        + "-----------------------------" + System.lineSeparator()
        + "* Type: LARGE_BUS_VEHICLE" + System.lineSeparator()
        + "* Position: (-93.235071,44.973580)" + System.lineSeparator()
        + "* Passengers: 0" + System.lineSeparator()
        + "* CO2: " + System.lineSeparator();
    assertEquals(expectedText, message.get("text").getAsString());
  }

  /**
   * test provideInfo when trip is complete.
   */
  @Test
  public void testTripComplete() {
    Line testline = testBus.getLine();
    for (int i = 0; i < 3; i++) {
      testline.getOutboundRoute().nextStop();
      testline.getInboundRoute().nextStop();
    }
    testBus.update();
    testBus.provideInfo();
    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(session).sendJson(messageCaptor.capture());
    JsonObject message = messageCaptor.getValue();
    String expectedText = "";
    assertEquals(expectedText, message.get("text").getAsString());
  }

  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    testBus = null;
  }

}
