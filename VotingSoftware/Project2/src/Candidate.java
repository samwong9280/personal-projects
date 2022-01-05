import java.util.ArrayList;

public class Candidate {
  //private Ballot[] votes;
  private ArrayList votes = new ArrayList<Ballot>();
  private String name;

  /**
   * Constructor for the creation of a new Candidate object
   *
   * @param name name for the new Candidate
   */
  public Candidate(String name){
    this.name=name;
  }

  /**
   * Counts the number of Ballots the Candidate has won
   *
   * @return the number of Ballots the Candidate has won
   */
  public int getNumVotes() {
    return votes.size();
  }

  /**
   * Append a Ballot onto the ArrayList of ballots the Candidate has
   *
   * @param b the Ballot to be appended
   */
  public void appendBallot(Ballot b){
    votes.add(b);
  }

  /**
   * Gets the ArrayList of Ballots this Candidate has won
   *
   * @return ArrayList of Ballots won by the Candidate
   */
  public ArrayList<Ballot> getBallots() {
    return votes;
  }

  /**
   * Gets the name of the Candidate
   *
   * @return name of the Candidate
   */
  public String getName(){
    return name;
  }
  // added because we have no way of changing contents of BallotArrayList
  // even when we had just Ballot[] we needed a setter method which
  // we just didn't think of/include on our class diagram
  
}

