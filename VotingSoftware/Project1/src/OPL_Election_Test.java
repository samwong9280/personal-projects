import static org.junit.Assert.*;

import org.junit.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class OPL_Election_Test {
  @Test
  public void testOPLElectionConstructor() {
    String fileName = "Project1/testing/OPLTest1.csv";
    try {
      BufferedReader br = new BufferedReader(new FileReader(fileName));
      //have to change br to read in the test file for OPL
      OPL_Election ol = new OPL_Election(br);
      assertTrue(ol.getCandidates() != null);
      assertEquals(0, ol.getSeats());
      assertTrue(ol.getParties() != null);
    }
    catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  /**
   * Tests the run election function in OPL Election with a correctly formatted file being read in.
   */
  @Test
  public void testRunElectionOPLValid(){
    String fileName = "Project1/testing/OPLTest1.csv";
    Path currentRelativePath = Paths.get("");
    String s = currentRelativePath.toAbsolutePath().toString();
    String newPath="";
    newPath+=s;
    newPath+="/";
    newPath+=fileName;
    try {
      BufferedReader br = new BufferedReader(new FileReader(newPath));
      br.readLine();
      OPL_Election ol = new OPL_Election(br);
      String outcome = ol.runElection();
      assertEquals(6, ol.getNumCandidates());
      assertEquals(3, ol.getSeats());
      assertEquals(9, ol.getTotalVote());
      assertEquals(3, ol.getQuota());
      String expected = "Party, Seats Won, Candidates:\n" + "D , 2 , Pike , Foster\n" +
          "R , 1 , Borg\n" + "I , 0\n"; // expected is the output of calling run election
      assertEquals(expected, outcome);
    }
    catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  /**
   * Tests the run election function in OPL Election with the line that specifies the number of candidates missing.
   */


  @Test
  public void testAssignBallot() {
    String fileName = "Project1/testing/OPLTest1.csv";
    try {
      BufferedReader br = new BufferedReader(new FileReader(fileName));
      br.readLine();
      Ballot ballot = new Ballot(100);
      Candidate can = new Candidate("Pike");
      OPL_Election opl = new OPL_Election(br);

      opl.assignBallot(ballot, can);
      ArrayList<Ballot> newVotes = can.getBallots();
      assertEquals(ballot, newVotes.get(-1));
    }
    catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
