package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class TrainStrategyNightTest {

  /**
   * Test constructor normal.
   */
  @Test
  public void testConstructor() {
    TrainStrategyNight trainStrategyNight = new TrainStrategyNight();
    assertEquals(0, trainStrategyNight.getCounter());
  }

  /**
   * Testing to get correct vehicle according to the strategy.
   */
  @Test
  public void testGetTypeOfVehicle() {
    StorageFacility storageFacility = new StorageFacility(0, 0, 1, 1);
    StorageFacility nullStorageFacility = new StorageFacility(0, 0, 0, 0);
    StorageFacility secondNullStorageFacility = new StorageFacility(0, 0, 1, 0);
    TrainStrategyNight trainStrategyNight = new TrainStrategyNight();
    String strToCmpr;
    for (int i = 0; i < 1; i++) {
      strToCmpr = trainStrategyNight.getTypeOfVehicle(storageFacility);
      assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, strToCmpr);
      strToCmpr = trainStrategyNight.getTypeOfVehicle(storageFacility);
      assertEquals(DieselTrain.DIESEL_TRAIN_VEHICLE, strToCmpr);
    }
    for (int j = 0; j < 1; j++) {
      strToCmpr = trainStrategyNight.getTypeOfVehicle(nullStorageFacility);
      assertEquals(null, strToCmpr);
    }
    for (int k = 0; k < 1; k++) {
      strToCmpr = trainStrategyNight.getTypeOfVehicle(secondNullStorageFacility);
      strToCmpr = trainStrategyNight.getTypeOfVehicle(secondNullStorageFacility);
      assertEquals(null, strToCmpr);
    }
  }

  /**
   * Testing to see if getType handles null cases.
   */
  @Test
  public void testGetTypeOfVehicleNull() {
    StorageFacility storageFacility = new StorageFacility(0, 0, 0, 0);
    TrainStrategyNight trainStrategyNight = new TrainStrategyNight();
    assertNull(trainStrategyNight.getTypeOfVehicle(storageFacility));
  }
}
