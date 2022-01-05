import java.io.BufferedReader;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

public class OPL_Election extends Election {
    private ArrayList<Candidate> candidates;
    private int seats;
    private ArrayList<Party> parties;
    private BufferedReader br1;
    private int quota;
    private int numCandidates;
    private String audit;
    private String media="Intial Distribution of Votes\n";
    private ArrayList<BufferedReader> br1112=new ArrayList<BufferedReader>();

    /**
     * Constructor for the OPL_Election class, initializes some of the variables
     * @param br BufferedReader of the input CSV file
     *
     */
    public OPL_Election(BufferedReader br) {
        candidates=new ArrayList<Candidate>();
        seats=0;
        parties=new ArrayList<Party>();
        br1=br;
        br1112.add(br1);
        /*String a=runElection();
        System.out.println(a);*/
    }

    /**
     * Constructor for the OPL_Election class, initializes some of the variables
     * @param br BufferedReader of the input CSV file.
     * @param br111 ArrayList of BufferedReader objects is useful if multiple csv files are inputed.
     *
     */
    public OPL_Election(BufferedReader br,ArrayList<BufferedReader> br111) {
        candidates=new ArrayList<Candidate>();
        seats=0;
        parties=new ArrayList<Party>();
        br1=br;
        br1112=br111;

        /*String a=runElection();
        System.out.println(a);*/
    }

    public ArrayList<Candidate> getCandidates() {
        return candidates;
    }

    public int getSeats() {
        return seats;
    }

    public int getQuota() {
        return quota;
    }

    public ArrayList<Party> getParties() {
        return parties;
    }

    public int getNumCandidates() {
        return numCandidates;
    }

    public int getTotalVote() {
        return totalVotes;
    }

    public String getAudit(){
        return audit;
    }

    /**
     * simulates running an election. contains logic for reading the first 4 lines of the CSV file,
     * then calls on other functions to read the ballots, assign ballots, distribute seats, and determine the outcome.
     * @return returns a string that contains information about how many seats each party won and which candidates got those seats
     * @throws IOException if there's an error in any part of reading/parsing the first 4 lines of the file
     */
    public String runElection(){
        String line1 = null;   //number of candidates
        try {
            line1 = br1.readLine();
            numCandidates = Integer.parseInt(line1);
            audit+="Number of Candidates: " +numCandidates+"\n";
            String line2 = br1.readLine();   //string of candidates
            audit+="Candidates and Parties: "+ line2+"\n";
            readCandidates(line2);
            String line3= br1.readLine();    //number of Seats
            seats=Integer.parseInt(line3);
            audit+="Number of seats: " + seats+"\n";
            /*String line4= br1.readLine();
            totalVotes=Integer.parseInt(line4); */    //number of Votes
            /*audit+="Number of votes: " + totalVotes+"\n";
            setQuota();
            media+="Quota to win a seat is: "+quota +"\n";*/
            processBallots();
            setQuota();
            media+="Quota to win a seat is: "+quota +"\n";
            distributeSeats();
            System.out.println(determineOutcome());
            createMediaFile();
            return audit;
        } catch (IOException e) {
            e.printStackTrace();
        }
       return audit;
    }

    /**
     * function for reading and creating an OPL_Ballot for each ballot in the CSV file
     * @throws IOException if there's an error in any part of reading/parsing the file
     */
    private void processBallots() throws IOException{
        totalVotes=0;
        int p=-1;
        int ballotID = 1;
        int totalVotes1=0;
        String audit1="";
        for(int j=0; j<br1112.toArray().length; j++) {
            if (j != 0) {
                br1 = br1112.get(j);
                br1.readLine();
                br1.readLine();
                br1.readLine();
                br1.readLine();
            }
            String line4 = br1.readLine();
            totalVotes1 = Integer.parseInt(line4);
            totalVotes += totalVotes1;


            for (int i = 0; i < totalVotes1; i++) {
                String bal = br1.readLine();
                int vote = bal.indexOf("1");
                OPL_Ballot newBal = new OPL_Ballot(ballotID, candidates.get(vote));
                ballots.add(newBal);
                assignBallot(newBal, candidates.get(vote));
                audit1 += "Ballot " + ballotID + " assigned to: " + newBal.getCandidate().getName() + "\n";
                ballotID++;
            }

            p=j;
        }
        p=br1112.size();
        audit+="Number of votes: "+ totalVotes+"\n";
        audit+=audit1;
    }

    /**
     * determines quota for the OPL election(votes/seats)
     */
    public void setQuota(){
        quota= totalVotes/seats;
    }

    /**
     * contains logic for parsing and creating Candidates object for the candidates in line 2 of the input CSV file
     * @param  line2 string read from line 2 of the input file that contains the candidates and their parties. 
     */
    private void readCandidates(String line2){
        String str = line2.replaceAll("\\s*,\\s*", " ");
        String str1 = str.replaceAll("\\[", "");
        String[] strings = str1.split("\\]");
        //[Pike,D],[Foster,D] -> Pike D] Foster D]-> ["Pike D","Foster D"]
        String[] canParty;
        //["pike","D"]
        for(int i = 0; i< strings.length; i++){
            strings[i]=strings[i].trim();
            canParty = strings[i].split(" ");
            Candidate newCandidate = new Candidate(canParty[0]);
            int c=0;
            Party currParty = null;
            for(int j=0;j<parties.size();j++){
                if(parties.get(j).getPartyName().equals(canParty[1])){
                    c=1;
                    currParty= (Party) parties.get(j);
                }
            }
            if(c==1){
                currParty.addCandidate(newCandidate);
                candidates.add(newCandidate);

            }
            else {
                Party newParty= new Party(canParty[1]);
                newParty.addCandidate(newCandidate);
                parties.add(newParty);
                candidates.add(newCandidate);
            }
        }
        //String str = line2.replaceAll("\\[(.*?)\\]", "$1");
    }
    //readCandidates takes in a string right now for testing purposes,
    //we can either have it so that this function take in line two of
    //the file when it gets called or change it so we pass in a BufferedReader param

    /**
     * uses quota to determine how many seats each party gets, assigns those seats
     * to the candidates in the party with the highest number of votes
     */
    private void distributeSeats(){
        setPartyVotes();
        int remainingSeats=catchTies();
        if(remainingSeats>0) {
            for (int i = 0; i < parties.size(); i++) {
                Party currParty = parties.get(i);
                int votes = currParty.getVotes();
                remainingSeats -= (votes / quota);
                currParty.setSeats(votes / quota);
                if ((votes / quota) > currParty.getCandidates().size()) {
                    currParty.setSeats(currParty.getCandidates().size()); //makes sure a party can't win more seats than the number of candidates they have
                    System.out.println(currParty.getSeats());
                }
                audit += currParty.getPartyName() + " has won " + currParty.getSeats() + " seats, with " + currParty.getVotes() + " votes.\n";
                media += parties.get(i).getPartyName() + " received " + parties.get(i).getVotes() + " votes: " + ((double) parties.get(i).getVotes() / (double) totalVotes) * 100 + "%\n";
                media += currParty.getPartyName() + " won " + currParty.getSeats() + " seats\n";
                currParty.setVotes(votes % quota);
            }
        }
        for(int i=0;i<remainingSeats;i++){
            Party max = parties.get(0);
            for(int j=1;j<parties.size();j++){
                if(parties.get(i).getVotes()>max.getVotes()){
                    max=parties.get(i);
                }
            }
            audit+=max.getPartyName()+" has won one remaining seat with " + max.getVotes()+ " votes.\n";
            media+=max.getPartyName()+" won one remaining seat with " + max.getVotes()+ " votes.\n";
            max.setSeats(max.getSeats()+1); //adds 1 to current number of seats the party has
            max.setVotes(0);
        }
    }

    /**
     * Sets the number of votes that each party gets and sorts the parties from highest to lowest number of votes.
     */
    private void setPartyVotes(){
        for(int i =0;i<parties.size();i++){
            Party currParty= (Party) parties.get(i);
            currParty.setTotalVotes();
            //System.out.println(currParty.getVotes());
        }
        Party currParty1=(Party) parties.get(0);
        for(int j=0; j<parties.size();j++){
            currParty1= (Party) parties.get(j);
            for(int k=0; k<parties.size();k++){
                Party newParty= (Party) parties.get(j);
                if(newParty.getVotes()>currParty1.getVotes()){
                    Party one=parties.get(j);
                    parties.set(j,parties.get(k));
                    parties.set(k,one);
                    break;
                }

            }

        }
    }


    /**
     * Generates a String containing the outcome of the election, specifically the Party, Seats Won, and Candidates
     * @return String containing the details of the election outcome
     */
    private String determineOutcome(){
        String output="Party, Seats Won, Candidates:\n";
        for(int i=0;i<parties.size();i++){
            Party currParty=parties.get(i);
            output+= currParty.getPartyName() + " , " +Integer.toString(currParty.getSeats());
            int numSeats=currParty.getSeats();
            Candidate[] cands=currParty.getTopCandidates(numSeats);
            audit+=currParty.getPartyName() +"'s seats have been won by:\n";
            for(int j=0;j<cands.length;j++){
                //System.out.println(cands[j]);
                if(cands[j]!=null) {


                    audit += cands[j].getName() + "\n";
                    output += " , " + cands[j].getName();
                }
            }
            output+="\n";
        }
        return output;
    }

    private void createMediaFile() throws IOException{
        BufferedWriter br11= new BufferedWriter(new FileWriter("mediaFile.txt"));
        br11.write(media);
        br11.close();
    }

    /**
     * catches and handles any ties that occur in the election
     * @return remaining number of seats to be distributed
     */
    private int catchTies() {
        int remainingSeats = seats;
        Set<Party> tied = new HashSet<Party>();   //using set so no duplicates are added
        for (int i = 0; i < parties.size() - 1; i++) {
            //if two parties have the same votes add them to the array of tied parties
            if (parties.get(i).getVotes() == parties.get(i + 1).getVotes()) {
                tied.add(parties.get(i));
                tied.add(parties.get(i + 1));
            }
        }
        ArrayList<Party> tiedParties = new ArrayList<Party>();
        tiedParties.addAll(tied);
        if(tiedParties.size()==0){
            return remainingSeats;
        }
        //distributing seats
        int votes = tiedParties.get(0).getVotes();
        int numSeats = (votes / quota) * tiedParties.size();
        //case where there's enough votes for each party
        if (numSeats <= remainingSeats) {
            return remainingSeats;
        }
        //case where there's not enough votes for each party
        else{
            //randomizes order for fairness
            Collections.shuffle(tiedParties);
            int i=0;
            while(remainingSeats>0){
                Party currParty=tiedParties.get(i);
                currParty.setSeats(currParty.getSeats()+1);
                currParty.setVotes(currParty.getVotes()-quota);
                i=(i+1)%tiedParties.size();
                remainingSeats-=1;
            }
            for(int j=0;j<parties.size();j++){
                for(int k=0;k<tiedParties.size();k++){
                    if(parties.get(j).getPartyName().equals(tiedParties.get(k).getPartyName())){
                        parties.set(j,tiedParties.get(k));
                    }
                }
            }
            return 0;
        }
    }
}
