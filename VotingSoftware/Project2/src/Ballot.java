public class Ballot {
    private int ID;

    /**
     * Constructor for the creation of a new Ballot object, assigns an ID to this ballot object
     *
     * @param ID the ID number to be assigned to this Ballot
     */
    public Ballot(int ID){
        this.ID=ID;
    }

    /**
     * Gets the ID number assigned to the Ballot 
     *
     * @return the ID number of the Ballot
     */
    public int getID(){
        return this.ID;
    }

    /**
     * Set the Ballot ID to a different number
     *
     * @param newID new ID number to be assigned to the Ballot
     */
    public void setID(int newID){
        this.ID=newID;
    }
}
