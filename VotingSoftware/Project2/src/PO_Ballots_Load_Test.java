import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PO_Ballots_Load_Test {
  @Test
  public void testPOElectionConstructor() {
    String fileName = "testing/POTest1.csv";
    try {
      BufferedReader br = new BufferedReader(new FileReader(fileName));
      //have to change br to read in the test file for OPL
      PO_Election po = new PO_Election(br);
      assertTrue(po.getCandidates() != null);
      assertTrue(po.getParties() != null);
    }
    catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  /**
   * Tests the run election function that just handles assigning ballots
   * in PO Election with a correctly formatted file being read in.
   */
  @Test
  public void testRunElectionOPLValid(){
    String fileName = "testing/POTest1.csv";
    Path currentRelativePath = Paths.get("");
    String s = currentRelativePath.toAbsolutePath().toString();
    String newPath="";
    newPath+=s;
    newPath+="/";
    newPath+=fileName;
    try {
      BufferedReader br = new BufferedReader(new FileReader(newPath));
      br.readLine();
      PO_Election po = new PO_Election(br);
      String outcome = po.runElection();
      assertEquals(6, po.getNumCandidates());
      assertEquals(9, po.getTotalVote());
      String expected = "Hooray"; // expected is the output of calling run election
      assertEquals(expected, outcome);
      ArrayList<Candidate> candidates = new ArrayList<Candidate>();
      Candidate cand1 = new Candidate("Pike");
      Candidate cand2 = new Candidate("Foster");
      Candidate cand3 = new Candidate("Deutsch");
      Candidate cand4 = new Candidate("Borg");
      Candidate cand5 = new Candidate("Jones");
      Candidate cand6 = new Candidate("Smith");
      candidates.add(cand1);
      candidates.add(cand2);
      candidates.add(cand3);
      candidates.add(cand4);
      candidates.add(cand5);
      candidates.add(cand6);
      assertEquals("Pike",po.getCandidates().get(0).getName());
      assertEquals("Foster",po.getCandidates().get(1).getName());
      assertEquals("Deutsch",po.getCandidates().get(2).getName());
      assertEquals("Borg",po.getCandidates().get(3).getName());
      assertEquals("Jones",po.getCandidates().get(4).getName());
      assertEquals("Smith",po.getCandidates().get(5).getName());
      assertEquals("D",po.getParties().get(0).getPartyName());
      assertEquals("R",po.getParties().get(1).getPartyName());
      assertEquals("I",po.getParties().get(2).getPartyName());
      assertEquals(1,po.getBallots().get(0).getID());
      assertEquals(2,po.getBallots().get(1).getID());
      assertEquals(3,po.getBallots().get(2).getID());
      assertEquals(4,po.getBallots().get(3).getID());
      assertEquals(5,po.getBallots().get(4).getID());
      assertEquals(6,po.getBallots().get(5).getID());
      assertEquals(7,po.getBallots().get(6).getID());
      assertEquals(8,po.getBallots().get(7).getID());
      assertEquals(9,po.getBallots().get(8).getID());
    }
    catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
