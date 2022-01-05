public class PO_Ballot extends Ballot{
  private Candidate candidate;

  /**
   * Constructor for the creation of a new PO_Ballot object
   *
   * @param ID ID for the PO_Ballot
   * @param candidate name of the candidate for the PO_Ballot
   */
  public PO_Ballot(int ID, Candidate candidate){
    super(ID);
    this.candidate=candidate;
  }

  /**
   * The candidate appears on the PO_Ballot
   *
   * @return the Candidate who is on the PO_Ballot
   */
  public Candidate getCandidate(){
    return candidate;
  }

  /**
   * Logic for parsing the input CSV file and calculating an election
   *
   * @param newCan the new Candidate who will be on the ballot
   */
  public void setCandidate(Candidate newCan){
    candidate=newCan;
  }
}
