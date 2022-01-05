
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
    private ArrayList<IR_Ballot> invalidIRBallots=new ArrayList<IR_Ballot>(); // ballots that have been invalidated when running the election
    private ArrayList<BufferedReader> br1112=new ArrayList<BufferedReader>();
    private String Winner1="";

    /**
     * Constructor for the IR_Election class, initializes some of the variables
     *
     * @param br BufferedReader of the input CSV file
     */
    public IR_Election(BufferedReader br) {
        super();
        br1=br;
        br1112.add(br1);
    }

    /**
     * Constructor for the IR_Election class, initializes some of the variables
     *
     * @param br BufferedReader of the input CSV file.
     * @param br111 ArrayList of BufferedReader if multiple csv files are inputed.
     */
    public IR_Election(BufferedReader br, ArrayList<BufferedReader> br111) {
        super();
        br1=br;
        br1112=br111;
    }

    public ArrayList<Candidate> getCandidates() {
        return candidates;
    }

    public ArrayList<Candidate> getEliminatedCandidates() {
        return eliminatedCandidates;
    }

    public String getWinner1() {
        return Winner1;
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
        Winner1=winner.getName();
        audit+=winner.getName() +" has won the election with " + winner.getNumVotes() + " votes.\n";
        System.out.println(winner.getName()+ " won the election!");
        createMediaFile();
        return audit;
    }

    /**
     * Read in all the ballots from the input CSV file and assign them to their corresponding
     * first choice candidate, sets validity of each ballot depending on if half of the candidates have been ranked rounded up
     * @throws IOException if any part of reading/parsing the file fails
     */

    private void processBallots() throws IOException {
        totalVotes=0;
        int p=-1;
        int ballotID = 1;
        int totalVotes1=0;
        String audit1="";
        for(int k=0; k<br1112.toArray().length; k++) {
            if (k != 0) {
                br1 = br1112.get(k);
                br1.readLine();
                br1.readLine();
                br1.readLine();
            }
            String line3 = br1.readLine();
            totalVotes1 = Integer.parseInt(line3);
            totalVotes += totalVotes1;


            for (int i = 0; i < totalVotes1; i++) {
                String bal = br1.readLine();
                String[] choices = bal.split(","); // a ballot line into array form
                int minRankNeeded = (int) (numCandidates / 2.0 + .5); // min number of candidates ranked in order for the ballot to actually count (half of the num choices rounded up)
                //System.out.println("minRankNeeded is" + minRankNeeded); // debug
                Candidate[] temp = new Candidate[numCandidates]; // an array of candidates
                int countOfMinRanked = 0; // counter for min ranked in a ballot
                for (int j = 0; j < choices.length; j++) { // loop through each array repr. of ballot
                    if (choices[j].equals("")) { // if there is no ranking for that cand eg. index 2 of ["1","","2"]
                        //System.out.println("continued, j is" + j); // debug
                        continue;
                    }
                    countOfMinRanked++; // else candidate at index j was ranked, increment counter by 1
                    int choice = Integer.parseInt(choices[j]); // the ranking number of the cand of index j, eg. "1" to 1
                    temp[choice - 1] = candidates.get(j);
                }
                // if there is less than half the candidates in a ballot ranked, add ballot to invalid
                // ballots ArrayList and don't add to actual election counted ballots list
                IR_Ballot newBal;
                if (countOfMinRanked < minRankNeeded) {
                    //System.out.println("added invalid ballot" + countOfMinRanked); // debug
                    countOfMinRanked = 0; // reset counter for the iteration of loop

                    newBal = new IR_Ballot(ballotID, temp, false);
                    invalidIRBallots.add(newBal);
                    ballotID++;
                } else {
                    //System.out.println("added valid ballot" + countOfMinRanked); //debug
                    countOfMinRanked = 0; // reset counter for the iteration of loop

                    newBal = new IR_Ballot(ballotID, temp, true);
                    ballots.add(newBal);
                    assignBallot(newBal, newBal.getCurrentChoice());
                    audit += "Ballot " + ballotID + " assigned to: " + newBal.getCurrentChoice().getName() + "\n";
                    ballotID++;

                }
            }
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
