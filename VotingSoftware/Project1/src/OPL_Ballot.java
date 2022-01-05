public class OPL_Ballot extends Ballot {
    private Candidate candidate;

    /**
     * Constructor for the creation of a new OPL_Ballot object
     * 
     * @param ID ID for the OPL_Ballot
     * @param candidate name of the candidate for the OPL_Ballot
     */
    public OPL_Ballot(int ID, Candidate candidate){
        super(ID);
        this.candidate=candidate;
    }

    /**
     * The candidate appears on the OPL_Ballot
     *
     * @return the Candidate who is on the OPL_Ballot
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
