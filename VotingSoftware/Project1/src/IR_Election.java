
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedWriter;

public class IR_Election extends Election {
    private ArrayList<Candidate> candidates=new ArrayList<Candidate>();
    private ArrayList<Candidate> eliminatedCandidates=new ArrayList<Candidate>();
    private int numCandidates;
    private String audit="Election Type: IR\n";
    private BufferedReader br1;
    private String media="Intial vote distribution:\n";

    /**
     * Constructor for the IR_Election class, initializes some of the variables
     *
     * @param br BufferedReader of the input CSV file
     */
    public IR_Election(BufferedReader br) {
        super();
        br1=br;
    }

    public ArrayList<Candidate> getCandidates() {
        return candidates;
    }

    public ArrayList<Candidate> getEliminatedCandidates() {
        return eliminatedCandidates;
    }

    /**
     * redistributeVotes, redistributes the votes of specified Candidate.
     * @param candidate specifies which candidate's votes are to be redistributed
     */
    private void redistributeVotes(Candidate candidate){
        ArrayList<Ballot> votes=candidate.getBallots();
        for(int i=0;i<votes.size();i++){
            try{
                IR_Ballot currBallot=(IR_Ballot)votes.get(i);
                currBallot.setChoice(currBallot.getChoice()+1);
                assignBallot(currBallot,currBallot.getCurrentChoice());
                audit+="Ballot " + currBallot.getID() + " reassigned to: " + currBallot.getCurrentChoice().getName()+"\n";
            }
            catch(NullPointerException e){ /*Making sure to not count nay badly formatted ballots*/
                //throws out ballot if they have no more choices on their ballot
                continue;
            }
        }
    }

    /**
     * returns the Candidate which is the winner
     * 
     * @return the first Candidate in the arraylist, which is the winner
     */
    private Candidate determineWinner(){
        return candidates.get(0);
    }

    /**
     * determines the Candidate with the lowest number of votes, redistributes their votes
     * and takes them out of the running
     */
    private void eliminateCandidate(){
        Candidate minVotes=candidates.get(0);
        for(int i=1;i<candidates.size();i++){
            if(candidates.get(i).getNumVotes()<minVotes.getNumVotes()){
                minVotes=candidates.get(i);
            }
            if(candidates.get(i).getNumVotes()==minVotes.getNumVotes()){
                minVotes=breakTie(new Candidate[]{candidates.get(i),minVotes});
            }
        }
        audit+=minVotes.getName() + " eliminated\n";
        redistributeVotes(minVotes);
        eliminatedCandidates.add(minVotes);
        candidates.remove(minVotes);

    }

    /**
     * Reads the candidates from the file
     * 
     * @param line a line from the inputted CSV file containing the list of candidates
     */
    private void readCandidates(String line){
        String[] cands=line.split(",");
        for(int i=0;i<cands.length;i++){
            Candidate temp=new Candidate(cands[i]);
            candidates.add(temp);
        }
    }

    /**
     * Logic for parsing the input CSV file and calculating an election
     * 
     * @return the result of calling determineWinner() which is the election winner
     * @throws IOException if there's an error in any part of reading/parsing the file
     */
    public String runElection() throws IOException {
        int round=1;
        String line1 =br1.readLine();
        numCandidates= Integer.parseInt(line1); //gets number of candidates
        audit+="Number of Candidates: " +numCandidates+"\n";
        String line2= br1.readLine();    //string of candidates
        audit+="Candidates: "+ line2+"\n";
        readCandidates(line2);  //stores candidates in local var
        audit+="Round: " + round + "\n";
        processBallots();   //reads ballots from file and stores in ballot Arraylist
        for(int i=0;i<candidates.size();i++){ //writes relevant information to audit file
            audit+=candidates.get(i).getName()+" has " + candidates.get(i).getNumVotes()+"\n";
            media+=candidates.get(i).getName()+" recieved " + candidates.get(i).getNumVotes()+": (" + ((double)candidates.get(i).getNumVotes()/(double)totalVotes)*100 +"%).\n";
        }
        while(candidates.size()>1){ //eliminates candidates until one is remaining
            round++;
            audit+="Round: " + round + "\n";
            for(int i=0;i<candidates.size();i++){
                audit+=candidates.get(i).getName()+" has " + candidates.get(i).getNumVotes()+" votes.\n";
            }
            eliminateCandidate();
        }
        Candidate winner=determineWinner();
        audit+=winner.getName() +" has won the election with " + winner.getNumVotes() + " votes.\n";
        System.out.println(winner.getName()+ " won the election!");
        createMediaFile();
        return audit;
    }

    /**
     * Read in all the ballots from the input CSV file and assign them to their corresponding
     * first choice candidate
     * @throws IOException if any part of reading/parsing the file fails
     */

    private void processBallots() throws IOException {
        int ballotID = 1;
        String numy=br1.readLine();
        totalVotes = Integer.parseInt(numy); //reads number of ballots
        for (int i = 0; i < totalVotes; i++) {
            String bal = br1.readLine();
            String[] choices = bal.split(",");
            Candidate[] temp = new Candidate[numCandidates];
            for (int j = 0; j < choices.length; j++) {
                if(choices[j].equals("")){
                    continue;
                }
                int choice = Integer.parseInt(choices[j]);
                temp[choice-1] = candidates.get(j);
            }
            IR_Ballot newBal = new IR_Ballot(ballotID, temp);
            ballots.add(newBal);
            assignBallot(newBal, newBal.getCurrentChoice());
            audit+="Ballot " + ballotID + " assigned to: " + newBal.getCurrentChoice().getName()+"\n";
            ballotID++;
        }
    }


    public int getNumCandidates() {
        return numCandidates;
    }
    /**
     * creates a short media file containing simple statistics from the election.
     */
    private void createMediaFile() throws IOException{
        BufferedWriter br11= new BufferedWriter(new FileWriter("mediaFile.txt"));
        br11.write(media);
        br11.write("Candidates eliminated during Election:\n");
        for(int i=0;i<eliminatedCandidates.size();i++){
            br11.write(eliminatedCandidates.get(i).getName()+"\n");
        }
        br11.write(determineWinner().getName() +" won the election.");
        br11.close();
    }
}
