package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class TrainStrategyDayTest {

  /**
   * Test constructor normal.
   */
  @Test
  public void testConstructor() {
    TrainStrategyDay trainStrategyDay = new TrainStrategyDay();
    assertEquals(0, trainStrategyDay.getCounter());
  }

  /**
   * Testing to get correct vehicle according to the strategy.
   */
  @Test
  public void testGetTypeOfVehicle() {
    StorageFacility storageFacility = new StorageFacility(0, 0, 3, 1);
    StorageFacility nullStorageFacility = new StorageFacility(0, 0, 0, 0);
    StorageFacility secondNullStorageFacility = new StorageFacility(0, 0, 3, 0);
    TrainStrategyDay trainStrategyDay = new TrainStrategyDay();
    String strToCmpr;
    for (int i = 0; i < 1; i++) {
      strToCmpr = trainStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, strToCmpr);
      strToCmpr = trainStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, strToCmpr);
      strToCmpr = trainStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, strToCmpr);
      strToCmpr = trainStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(DieselTrain.DIESEL_TRAIN_VEHICLE, strToCmpr);
    }
    for (int j = 0; j < 1; j++) {
      strToCmpr = trainStrategyDay.getTypeOfVehicle(nullStorageFacility);
      assertEquals(null, strToCmpr);
    }
    for (int k = 0; k < 1; k++) {
      strToCmpr = trainStrategyDay.getTypeOfVehicle(secondNullStorageFacility);
      strToCmpr = trainStrategyDay.getTypeOfVehicle(secondNullStorageFacility);
      strToCmpr = trainStrategyDay.getTypeOfVehicle(secondNullStorageFacility);
      strToCmpr = trainStrategyDay.getTypeOfVehicle(secondNullStorageFacility);
      assertEquals(null, strToCmpr);
    }
  }

  /**
   * Testing to see if getType handles null cases.
   */
  @Test
  public void testGetTypeOfVehicleNull() {
    StorageFacility storageFacility = new StorageFacility(0, 0, 0, 0);
    TrainStrategyDay trainStrategyDay = new TrainStrategyDay();
    assertNull(trainStrategyDay.getTypeOfVehicle(storageFacility));
  }
}
