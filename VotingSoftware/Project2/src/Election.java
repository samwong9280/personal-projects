
import java.io.File;
import java.util.Random;
import java.util.ArrayList;
import java.io.IOException;;

public abstract class Election {

    protected ArrayList<Ballot> ballots= new ArrayList<Ballot>();
    int totalVotes=0;



    /* remove this or turn into abstract method
    public ArrayList<Ballot> processBallots(File file1){
        return ballots;
    }
    */
    abstract String runElection() throws IOException;

    /**
     * Assigns a given Ballot to a given Candidate by appening the Ballot to the Candidate's Ballot array list
     * 
     * @param ballot Ballot object to be assigned
     * @param cand Candidate who will receive the object
     */
    public void assignBallot(Ballot ballot, Candidate cand){
        //adds ballot to corresponding candidate's ballot array
        cand.appendBallot(ballot);
    }

    /**
     * When no Candidate has more votes than another, randomly select a Candidate to become the winner
     *
     * @param arr The list of tied Candidates of which a winner will be chosen from
     */
    public Candidate breakTie(Candidate[] arr){
        // create a variable to hold random values
        Random rand = new Random();
        int randInt=0;
        // take the 1000th randomly generated int from 0 to length of candidate array
        for(int i = 0; i < 1001; i++){
            randInt = rand.nextInt(arr.length);
        }
        // return winner
        Candidate winner = arr[randInt];
        return winner;
    }



//    public File createAuditFile(){
//        File audit;
//        return audit;
//    }

//    public File createMediaFile(){
//        File media;
//        return media;
//    }


}
