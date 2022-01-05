import java.util.ArrayList;

public class Party {
  private ArrayList<Candidate> candidates = new ArrayList<Candidate>();
  private int numSeats=0;
  private String partyName;
  private int votes;

  /**
   * Constructor for creating a new Party object
   * 
   * @param name name of the Party
   */
  public Party(String name) {
    this.partyName = name;
  }

  /**
   * Get an ArrayList of the top 'num' number of Candidates with the most votes from the Party
   *
   * @param num the number of Candidates with the highest votes needed from this Party
   * @return ArrayList of the top 'num' number of candidates specified in the parameter
   */
  public Candidate[] getTopCandidates(int num) {
    Candidate[] topCands=new Candidate[num];
    //sorting array of candidates by number of votes
        int n = candidates.size();
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (candidates.get(j).getNumVotes() < candidates.get(j+1).getNumVotes())
                {
                    // swap temp and arr[i]
                    Candidate temp = candidates.get(j);
                    candidates.add(j,candidates.get(j+1));
                    candidates.add(j+1,temp);
                }
    //gets top n candidates
      for (int i=0;i<num;i++){
        if(i<candidates.size()) {
          if(i<num) {
            topCands[i] = candidates.get(i);
          }
        }
      }
      return topCands;
  }

  /**
   * Gives the name of the Party object
   *
   * @return the name of the Party
   */
  public String getPartyName() {
    return partyName;
  }

  /**
   * Gives the ArrayList of all the candidates belonging to this Party
   *
   * @return an ArrayList of all the candidates belonging to the Party
   */
  public ArrayList<Candidate> getCandidates() {
    return candidates;
  }

  /**
   * Sets the list of Candidates belonging to this Party
   *
   * @param candidates1 a list of Candidates to be assigned as belonging to this Party
   */
  private void setCandidates(ArrayList<Candidate> candidates1) {
    candidates = candidates1;
  }

  /**
   * Add a candidate to the ArrayList of candidates belonging to this Party
   * 
   * @param cand the Candidate object to be added
   */
  public void addCandidate(Candidate cand){
    candidates.add(cand);
  }

  /**
   * Calculates the total number of votes the Party has from each Candidate belonging to the Party
   */
  //added getPartyName and getCandidates to make OPL Election functions easier
  public void setTotalVotes() {
    int votes=0;
    for(int i=0;i<candidates.size();i++){
      votes+=candidates.get(i).getNumVotes();
    }
    this.votes=votes;
  }

  /**
   * Gives the total number of votes the Party has received
   * 
   * @return number of votes the Party has received
   */
  public int getVotes(){
    return votes;
  }


  /**
   * Sets the number of votes of the Party
   * 
   * @param n the number of votes that the Party will possesses
   */
  public void setVotes(int n){
    votes=n;
  }

  /**
   * Sets the number of seats that the Party will possess
   * 
   * @param n the number of seats the party will have 
   */
  public void setSeats(int n){
    numSeats=n;
  }

  /**
   * Gives the number of seats that the Party has
   */
  public int getSeats(){
    return numSeats;
  }
}
