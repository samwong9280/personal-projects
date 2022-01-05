package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class BusStrategyNightTest {

  /**
   * Test constructor normal.
   */
  @Test
  public void testConstructor() {
    BusStrategyNight busStrategyNight = new BusStrategyNight();
    assertEquals(0, busStrategyNight.getCounter());
  }

  /**
   * Testing to get correct vehicle according to the strategy.
   */
  @Test
  public void testGetTypeOfVehicle() {
    StorageFacility storageFacility = new StorageFacility(3, 1, 0, 0);
    StorageFacility nullStorageFacility = new StorageFacility(0, 0, 0, 0);
    StorageFacility secondNullStorageFacility = new StorageFacility(1, 0, 0, 0);
    BusStrategyNight busStrategyNight = new BusStrategyNight();
    String strToCmpr;
    for (int i = 0; i < 1; i++) {
      strToCmpr = busStrategyNight.getTypeOfVehicle(storageFacility);
      assertEquals(SmallBus.SMALL_BUS_VEHICLE, strToCmpr);
      strToCmpr = busStrategyNight.getTypeOfVehicle(storageFacility);
      assertEquals(SmallBus.SMALL_BUS_VEHICLE, strToCmpr);
      strToCmpr = busStrategyNight.getTypeOfVehicle(storageFacility);
      assertEquals(SmallBus.SMALL_BUS_VEHICLE, strToCmpr);
      strToCmpr = busStrategyNight.getTypeOfVehicle(storageFacility);
      assertEquals(LargeBus.LARGE_BUS_VEHICLE, strToCmpr);
    }
    for (int j = 0; j < 1; j++) {
      strToCmpr = busStrategyNight.getTypeOfVehicle(nullStorageFacility);
      assertEquals(null, strToCmpr);
    }
    for (int k = 0; k < 1; k++) {
      strToCmpr = busStrategyNight.getTypeOfVehicle(secondNullStorageFacility);
      strToCmpr = busStrategyNight.getTypeOfVehicle(secondNullStorageFacility);
      strToCmpr = busStrategyNight.getTypeOfVehicle(secondNullStorageFacility);
      strToCmpr = busStrategyNight.getTypeOfVehicle(secondNullStorageFacility);
      assertEquals(null, strToCmpr);
    }
  }

  /**
   * Testing to see if getType handles null cases.
   */
  @Test
  public void testGetTypeOfVehicleNull() {
    StorageFacility storageFacility = new StorageFacility(0, 0, 0, 0);
    BusStrategyNight busStrategyNight = new BusStrategyNight();
    assertNull(busStrategyNight.getTypeOfVehicle(storageFacility));
  }
}
