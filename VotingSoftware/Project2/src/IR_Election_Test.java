import static org.junit.Assert.*;
import org.junit.Test;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class IR_Election_Test {
  
  @Test
  public void testRunElectionIRValidInput() {
    String parentPath= Paths.get(".").toAbsolutePath().normalize().toString();
    String fileName = parentPath+"/testing/IRTest100.csv";
    BufferedReader br = null;

    try {
      br = new BufferedReader(new FileReader(fileName));
      br.readLine();
      IR_Election ir = new IR_Election(br);
      String winner = null;
      try {
        winner = ir.runElection();
      } catch (IOException e) {
        e.printStackTrace();
      }
      assertEquals(4, ir.getNumCandidates());
      assertEquals(3, ir.getEliminatedCandidates().size());
      assertEquals("Rosen(D)", ir.getWinner1());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
  
//  @Test
//  public void testRunElectionIRIOException() {
//    String fileName = "IRTest100.csv";
//    BufferedReader br = new BufferedReader(new FileReader(fileName));
//    IR_Election ir = new IR_Election(br);
//    String winner = ir.runElection();
//    assertEquals(4, ir.getNumCandidates());
//    assertEquals(3, ir.getEliminatedCandidates().size());
//    assertEquals("Rosen", winner);
//  }

/*
*   Tests if the IRTestInv1.csv test file is being run correctly with the invalidation of ballots
*/
@Test
  public void testRunElectionIRInvalidationTestInput() {
    String parentPath= Paths.get(".").toAbsolutePath().normalize().toString();
    String fileName = parentPath+"/testing/IRTestInv1.csv";
    BufferedReader br = null;

    try {
      br = new BufferedReader(new FileReader(fileName));
      br.readLine();
      IR_Election ir = new IR_Election(br);
      String winner = null;
      try {
        winner = ir.runElection();
      } catch (IOException e) {
        e.printStackTrace();
      }
      assertEquals(4, ir.getNumCandidates());
      assertEquals(3, ir.getEliminatedCandidates().size());
      assertEquals("Royce(L)", ir.getWinner1());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
