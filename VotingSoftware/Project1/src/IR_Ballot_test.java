import static org.junit.Assert.*;
import org.junit.Test;

import junit.framework.AssertionFailedError;

public class IR_Ballot_test {
    /**
     * Tests IR_Ballot's constructor using getter methods.
     */
    @Test 
    public void testIRConstructor(){
        Candidate[] testArr=new Candidate[1];
        IR_Ballot test=new IR_Ballot(1,testArr);
        assertEquals(1,test.getID());
        assertArrayEquals(new Candidate[1],test.getCandidates());
        assertEquals(0,test.getChoice());
    }

    /**
     * Tests IR_Ballots getChoice getter
     */
    @Test 
    public void testGetChoice(){
        Candidate[] testArr=new Candidate[1];
        IR_Ballot test=new IR_Ballot(1,testArr);
        assertEquals(0,test.getChoice());
    }
    /**
     * Tests IR_Ballot's setChoice setter
     */
    @Test
    public void testSetChoice(){
        Candidate[] testArr=new Candidate[1];
        IR_Ballot test=new IR_Ballot(1,testArr);
        assertEquals(0,test.getChoice());
        test.setChoice(1);
        assertEquals(1,test.getChoice());
    }

    /**
     * Test IR_Ballot's getCurrentChoice() method
     */
    @Test
    public void testGetCurrentChoice(){
        Candidate cand1=new Candidate("Test1");
        Candidate cand2=new Candidate("Test2");
        Candidate[] testArr=new Candidate[]{cand1,cand2};
        IR_Ballot bal=new IR_Ballot(1,testArr);
        assertEquals(cand1,bal.getCurrentChoice());
        bal.setChoice(1);
        assertEquals(cand2,bal.getCurrentChoice());
        bal.setChoice(2);
        
    }
}
