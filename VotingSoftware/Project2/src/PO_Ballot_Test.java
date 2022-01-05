import static org.junit.Assert.*;
import org.junit.Test;
public class PO_Ballot_Test {
  /**
   * Tests PO_Ballot's constructor using getter methods
   */
  @Test
  public void testPOConstructor(){
    Candidate c1=new Candidate("Test");
    PO_Ballot test=new PO_Ballot(1,c1);
    assertTrue(1==test.getID());
    assertTrue(c1==test.getCandidate());
  }
  /**
   * Tests PO_Ballot's get and set Candidate methods
   */
  @Test
  public void testGetSetCandidate(){
    Candidate c1=new Candidate("Test");
    Candidate c2=new Candidate("Test2");
    PO_Ballot test=new PO_Ballot(1,c1);
    assertEquals(c1,test.getCandidate());
    test.setCandidate(c2);
    assertEquals(c2,test.getCandidate());
  }
}
