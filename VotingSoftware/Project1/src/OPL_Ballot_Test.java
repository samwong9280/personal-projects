import static org.junit.Assert.*;
import org.junit.Test;

public class OPL_Ballot_Test {
    /**
     * Tests OPL_Ballot's constructor using getter methods
     */
    @Test
    public void testOPLConstructor(){
        Candidate c1=new Candidate("Test");
        OPL_Ballot test=new OPL_Ballot(1,c1);
        assertTrue(1==test.getID());
        assertTrue(c1==test.getCandidate());
    }
    /**
     * Tests OPL_Ballot's get and set Candidate methods
     */
    @Test
    public void testGetSetCandidate(){
        Candidate c1=new Candidate("Test");
        Candidate c2=new Candidate("Test2");
        OPL_Ballot test=new OPL_Ballot(1,c1);
        assertEquals(c1,test.getCandidate());
        test.setCandidate(c2);
        assertEquals(c2,test.getCandidate());
    }
    
}
