package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrainFactoryTest {
  private StorageFacility storageFacility;
  private StorageFacility emptyStorageFacility;
  private TrainFactory trainFactory;
  private TrainFactory trainFactoryNight;
  private TrainFactory trainFactoryInvalid;
  private TrainFactory trainFactoryEmpty;

  /**
   * Setting up variables for tests.
   */
  @BeforeEach
  public void setUp() {
    storageFacility = new StorageFacility(0, 0, 3, 3);
    emptyStorageFacility = new StorageFacility(0, 0, 0, 0);
    trainFactory = new TrainFactory(storageFacility, new Counter(), 9);
    trainFactoryNight = new TrainFactory(storageFacility, new Counter(), 22);
    trainFactoryInvalid = new TrainFactory(storageFacility, new Counter(), -1);
    trainFactoryEmpty = new TrainFactory(emptyStorageFacility, new Counter(), 22);
  }

  /**
   * Testing the constructor.
   */
  @Test
  public void testConstructor() {
    assertTrue(trainFactory.getGenerationStrategy() instanceof TrainStrategyDay);
  }

  /**
   * Testing constructor for night strategy.
   */
  @Test
  public void testConsturctorNight() {
    TrainFactory testFactory = new TrainFactory(storageFacility, new Counter(), 7);
    assertTrue(testFactory.getGenerationStrategy() instanceof TrainStrategyNight);
  }

  /**
   * Testing if generated vehicle is working according to strategy.
   */
  @Test
  public void testGenerateVehicleElectricTrain() {
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

    Route testRouteIn = new Route(0, "testRouteIn",
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

    Route testRouteOut = new Route(1, "testRouteOut",
        stopsOut, distancesOut, generatorOut);

    Line line = new Line(10000, "testLine", "TRAIN", testRouteOut, testRouteIn,
        new Issue());

    Vehicle vehicle1 = trainFactory.generateVehicle(line);
    assertTrue(vehicle1 instanceof ElectricTrainColorDecorator);
    Vehicle vehicle2 = trainFactory.generateVehicle(line);
    assertTrue(vehicle2 instanceof ElectricTrainColorDecorator);
    Vehicle vehicle3 = trainFactory.generateVehicle(line);
    assertTrue(vehicle3 instanceof ElectricTrainColorDecorator);
    Vehicle vehicle4 = trainFactory.generateVehicle(line);
    assertTrue(vehicle4 instanceof DieselTrainColorDecorator);
  }

  /**
   * Test if generate vehicle works with null vehicle cases.
   */
  @Test
  public void testGenerateVehicleNull() {
    StorageFacility testStorage = new StorageFacility(0, 0, 0, 0);
    TrainFactory testfact = new TrainFactory(testStorage, new Counter(), 9);
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

    Route testRouteIn = new Route(0, "testRouteIn",
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

    Route testRouteOut = new Route(1, "testRouteOut",
        stopsOut, distancesOut, generatorOut);

    Line line = new Line(10000, "testLine", "BUS", testRouteOut, testRouteIn,
        new Issue());

    Vehicle vehicle1 = testfact.generateVehicle(line);
    assertNull(vehicle1);
  }

  /**
   * Testing if vehicle got returned.
   */
  @Test
  public void testReturnVehicleTrain() {
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

    Route testRouteIn = new Route(0, "testRouteIn",
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

    Route testRouteOut = new Route(1, "testRouteOut",
        stopsOut, distancesOut, generatorOut);

    ElectricTrainColorDecorator testElectricTrain = new ElectricTrainColorDecorator(
        new ElectricTrain(1, new Line(10000, "testLine", "BUS",
        testRouteOut, testRouteIn, new Issue()), 3, 1.0));

    DieselTrainColorDecorator testDieselTrain = new DieselTrainColorDecorator(
        new ElectricTrain(1, new Line(10000, "testLine", "BUS",
        testRouteOut, testRouteIn, new Issue()), 3, 1.0));

    LargeBusColorDecorator testLargeBus = new LargeBusColorDecorator(
        new LargeBus(1, new Line(10000, "testLine", "BUS",
        testRouteOut, testRouteIn, new Issue()), 3, 1.0));

    assertEquals(3, trainFactory.getStorageFacility().getElectricTrainsNum());
    assertEquals(3, trainFactory.getStorageFacility().getDieselTrainsNum());
    trainFactory.returnVehicle(testElectricTrain);
    trainFactory.returnVehicle(testDieselTrain);
    assertEquals(4, trainFactory.getStorageFacility().getElectricTrainsNum());
    assertEquals(4, trainFactory.getStorageFacility().getDieselTrainsNum());

    trainFactory.returnVehicle(testLargeBus);
  }

  /**
   * Testing if vehicle got returned for diesel train.
   */
  @Test
  public void testReturnVehicleDieselTrain() {
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

    Route testRouteIn = new Route(0, "testRouteIn",
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

    Route testRouteOut = new Route(1, "testRouteOut",
        stopsOut, distancesOut, generatorOut);

    DieselTrainColorDecorator testTrain = new DieselTrainColorDecorator(
        new DieselTrain(1, new Line(10000, "testLine", "BUS",
        testRouteOut, testRouteIn, new Issue()), 3, 1.0));

    assertEquals(3, trainFactory.getStorageFacility().getElectricTrainsNum());
    assertEquals(3, trainFactory.getStorageFacility().getDieselTrainsNum());
    trainFactory.returnVehicle(testTrain);
    assertEquals(3, trainFactory.getStorageFacility().getElectricTrainsNum());
    assertEquals(4, trainFactory.getStorageFacility().getDieselTrainsNum());
  }
}
