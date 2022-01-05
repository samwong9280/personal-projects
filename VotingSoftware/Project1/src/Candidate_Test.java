import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class Candidate_Test {
    /**
     * Test case for the constructor, utilizes getName() and getBallots() functions to test if 
     * it initilizes a Candidate's 'name' field to a String, and initializes its 'votes' field
     */
    @Test
    public void testCandidateConstructor(){
        String testString = "test";
        Candidate testCandidate = new Candidate(testString);
        assertTrue(testCandidate.getName() instanceof String);
        assertTrue(testCandidate.getBallots() != null);
    }

    /**
     * Test case for getNumVotes(), utilizes appendBallot()
     * Tests if appending 3 separate Ballot objects to a new Candidate's 'votes' field
     * then calling getNumVotes() will return the same number appended, being 3
     */
    @Test
    public void testgetNumVotes(){
        String testString = "test";
        Candidate testCandidate = new Candidate(testString);
        Ballot testBallot1 = new Ballot(420);
        Ballot testBallot2 = new Ballot(421);
        Ballot testBallot3 = new Ballot(422);
        testCandidate.appendBallot(testBallot1);
        testCandidate.appendBallot(testBallot2);
        testCandidate.appendBallot(testBallot3);
        assertEquals(3, testCandidate.getNumVotes());
    }

    // the tests for testappendBallot() and testgetBallots() are the same so are combined here
    /**
     * Test case for appendBallot() and getBallots(), tests if appending a Ballot to a Candidate
     * with appendBallot() then calling getBallots() will return an ArrayList<Ballot> containing just the
     * added Ballot
     */
    @Test
    public void testAppendGetBallots(){
        String testString = "test";
        Candidate testCandidate = new Candidate(testString);
        Ballot testBallot = new Ballot(420);
        ArrayList votes = new ArrayList<Ballot>();
        votes.add(testBallot);
        testCandidate.appendBallot(testBallot);
        assertEquals(testCandidate.getBallots(), votes);
    }

    /**
     * Test case for getName(), tests if instantiating a Candidate with 'name' being "test"
     * then calling getName() will return the same String, "test"
     */
    @Test
    public void testgetName(){
        String testString = "test";
        Candidate testCandidate = new Candidate(testString);
        assertEquals(testCandidate.getName(), testString);
    }
}
