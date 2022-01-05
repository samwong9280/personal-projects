import static org.junit.Assert.*;
import org.junit.Test;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class IR_Election_Test {
  @Test
  public void testRunElectionIRValidInput() {
    String fileName = "Project1/testing/IRTest1.csv";
    BufferedReader br = null;

    try {
      br = new BufferedReader(new FileReader(fileName));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    IR_Election ir = new IR_Election(br);
    String winner = null;
    try {
      winner = ir.runElection();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(4, ir.getNumCandidates());
    assertEquals(3, ir.getEliminatedCandidates().size());
    assertEquals("Rosen", winner);
  }
//  @Test
//  public void testRunElectionIRIOException() {
//    String fileName = "IRTest1.csv";
//    BufferedReader br = new BufferedReader(new FileReader(fileName));
//    IR_Election ir = new IR_Election(br);
//    String winner = ir.runElection();
//    assertEquals(4, ir.getNumCandidates());
//    assertEquals(3, ir.getEliminatedCandidates().size());
//    assertEquals("Rosen", winner);
//  }
}
