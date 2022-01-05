import static org.junit.Assert.*;
import org.junit.Test;

public class Party_Test {
    /**
     * Tests Party(Class)'s constructor using getter methods to see whether it is initalizing the party string.
     * UT10
     */
    @Test
    public void testPartyConstructor(){
        Party c1=new Party("D"); /*D stands for Democrat Party*/
        assertEquals("D",c1.getPartyName());/*Gets the party name and checks if the constructor initialized it corrctly.*/
    }

    /**
     * We are testing the Party class's setVotes() and getVotes() methods in on function since they are related.
     * UT11
     */
    @Test
    public void testPartyGetVotesSetVotes(){
        Party c1=new Party("D"); /*D stands for Democrat Party*/
        c1.setVotes(5);
        int num= c1.getVotes();
        assertEquals(5,c1.getVotes());/*Gets the party name and checks if the constructor initialized it corrctly.*/
    }

    /**
     * We are testing the Party class's setSeats() and getSeats() methods in on function since they are related.
     * UT12
     */
    @Test
    public void testPartyGetSeatsSetSeats(){
        Party c1=new Party("D"); /*D stands for Democrat Party*/
        c1.setSeats(5);
        int num= c1.getSeats();
        assertEquals(5,c1.getSeats());/*Gets the party name and checks if the constructor initialized it corrctly.*/
    }

    /**
     * We are testing the Party class's addCandidate() and setTotalVotes()
     * UT13
     */
    @Test
    public void testPartySetTotalVotes(){

        Party c1=new Party("D"); /*D stands for Democrat Party*/
        String testString = "test";
        Candidate testCandidate = new Candidate(testString);
        Candidate testCandidate2 = new Candidate(testString);
        Ballot testBallot1 = new Ballot(420);
        Ballot testBallot2 = new Ballot(421);
        Ballot testBallot3 = new Ballot(422);
        testCandidate.appendBallot(testBallot1);
        testCandidate.appendBallot(testBallot2);
        testCandidate2.appendBallot(testBallot3);
        c1.addCandidate(testCandidate);
        c1.addCandidate(testCandidate2);
        c1.setTotalVotes();
        assertEquals(3,c1.getVotes());/*Gets the party name and checks if the constructor initialized it corrctly.*/
    }

    /**
     * We are testing the Party class's getTopCandidates method which gets you a number of specified top candidates in a party based on the number of ballots they have
     * UT14
     */
    @Test
    public void testPartyGetTopCandidates(){

        Party c1=new Party("D"); /*D stands for Democrat Party*/
        String testString = "test";
        Candidate testCandidate = new Candidate(testString);
        Candidate testCandidate2 = new Candidate(testString);
        Candidate testCandidate3 = new Candidate(testString);
        Ballot testBallot1 = new Ballot(420);
        Ballot testBallot2 = new Ballot(421);
        Ballot testBallot3 = new Ballot(422);
        testCandidate.appendBallot(testBallot1); /*Has 2 ballots*/
        testCandidate.appendBallot(testBallot2);
        testCandidate2.appendBallot(testBallot3); /*Has One ballot*/
        c1.addCandidate(testCandidate);
        c1.addCandidate(testCandidate2);
        c1.addCandidate(testCandidate3);
        /*We are going to get the the candidate with the highest number of votes in the party using getTopCandidate below*/
        assertEquals(testCandidate,c1.getTopCandidates(1)[0]);/*Gets the party name and checks if the constructor initialized it corrctly.*/
    }



}
