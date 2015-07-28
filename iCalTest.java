package iCal;

import static org.junit.Assert.*;
import org.junit.Test;

//Tests the ICal class
public class ICalTest {

//Tests the getDistance method
  @Test
  public void testGetDistance() {
    String result = ICal.getDistance(21.3114, 157.7964, 22, 158);
    assertEquals("46.24048048230705;74.41665485939603" , result);
  }
}
