import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class PO_Election extends Election{
  private ArrayList<Candidate> candidates;
  private ArrayList<Party> parties;
  private BufferedReader br1;
  private int numCandidates;
  private String audit;
  private String media="Intial Distribution of Votes\n";
  private ArrayList<BufferedReader> br1112=new ArrayList<BufferedReader>();

  /**
   * Constructor for the PO_Election class, initializes some of the variables
   * @param br BufferedReader of the input CSV file
   */
  public PO_Election(BufferedReader br) {
    candidates=new ArrayList<Candidate>();
    parties=new ArrayList<Party>();
    br1=br;
    audit="";
    br1112.add(br1);
  }

  public PO_Election(BufferedReader br,ArrayList<BufferedReader> br111) {
    candidates=new ArrayList<Candidate>();
    parties=new ArrayList<Party>();
    br1=br;
    br1112=br111;
    audit="";
  }

  public String runElection() {
    String line1 = null;   //number of candidates
    String result = "Hooray";
    try {
      line1 = br1.readLine();
      numCandidates = Integer.parseInt(line1);
      String line2 = br1.readLine();   //string of candidates
      readCandidates(line2);
           //number of Votes
      processBallots();
      System.out.println(ballots);
      return result;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  public ArrayList<Candidate> getCandidates() {
    return candidates;
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

  public ArrayList<Ballot> getBallots() {
    return ballots;
  }

  /**
   * function for reading and creating a PO_Ballot for each ballot in the CSV file
   * @throws IOException if there's an error in any part of reading/parsing the file
   */
  private void processBallots() throws IOException{
    int ballotID = 1;

    int totalVotes1=0;
    for(int j=0; j<br1112.toArray().length; j++) {
      if (j != 0) {
        br1 = br1112.get(j);
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
        PO_Ballot newBal = new PO_Ballot(ballotID, candidates.get(vote));
        ballots.add(newBal);
        assignBallot(newBal, candidates.get(vote));
        ballotID++;
      }
    }
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
}
