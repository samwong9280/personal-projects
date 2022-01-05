package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class BusStrategyDayTest {

  /**
   * Test constructor normal.
   */
  @Test
  public void testConstructor() {
    BusStrategyDay busStrategyDay = new BusStrategyDay();
    assertEquals(0, busStrategyDay.getCounter());
  }

  /**
   * Testing to get correct vehicle according to the strategy.
   */
  @Test
  public void testGetTypeOfVehicle() {
    StorageFacility storageFacility = new StorageFacility(1, 2, 0, 0);
    StorageFacility nullStorageFacility = new StorageFacility(0, 0, 0, 0);
    StorageFacility secondNullStorageFacility = new StorageFacility(0, 2, 0, 0);
    BusStrategyDay busStrategyDay = new BusStrategyDay();
    String strToCmpr;
    for (int i = 0; i < 1; i++) {
      strToCmpr = busStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(LargeBus.LARGE_BUS_VEHICLE, strToCmpr);
      strToCmpr = busStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(LargeBus.LARGE_BUS_VEHICLE, strToCmpr);
      strToCmpr = busStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(SmallBus.SMALL_BUS_VEHICLE, strToCmpr);
    }
    for (int j = 0; j < 1; j++) {
      strToCmpr = busStrategyDay.getTypeOfVehicle(nullStorageFacility);
      assertEquals(null, strToCmpr);
    }
    for (int k = 0; k < 1; k++) {
      strToCmpr = busStrategyDay.getTypeOfVehicle(secondNullStorageFacility);
      strToCmpr = busStrategyDay.getTypeOfVehicle(secondNullStorageFacility);
      strToCmpr = busStrategyDay.getTypeOfVehicle(secondNullStorageFacility);
      assertEquals(null, strToCmpr);
    }
  }

  /**
   * Testing to see if getType handles null cases.
   */
  @Test
  public void testGetTypeOfVehicleNull() {
    StorageFacility storageFacility = new StorageFacility(0, 0, 0, 0);
    BusStrategyDay busStrategyDay = new BusStrategyDay();
    assertNull(busStrategyDay.getTypeOfVehicle(storageFacility));
  }
}
