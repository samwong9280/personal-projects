import java.io.*;
import java.util.Scanner; // Import the Scanner class to read text files


public class Main {



    private static Scanner myReader;
    private static String string1;
    private static BufferedReader br=null;
    private static Election obj;

    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String fileName;
        System.out.println("Please enter filename");  // Output user input
        fileName = myObj.nextLine();  // Read user input

        try{
            br = new BufferedReader(new FileReader(fileName));
            BufferedWriter br11= new BufferedWriter(new FileWriter("audit.txt"));

            //One way of reading the file
            System.out.println("Reading the file using readLine() method:");
            String contentLine = br.readLine();
            string1=contentLine;

            createElection(); /*Creates Type of election*/

            try{
                String output=obj.runElection();
                br11.write(output);
                }
                catch(IOException e){
                    System.out.println("Error occured when reading file");
                }


            br.close();
            br11.close();

        }
        catch (IOException ioe)
        {
            System.out.println("File not found");
        }

    }

    /**
     * Create a new election based on which election type is specified in the input CSV file
     */
    private static void createElection() throws IOException {
        String local= getElectionType();
        if(local.equals("IR")){
            IR_Election IRE= new IR_Election(br);
            obj=(Election)IRE;
        }
        else if(local.equals("OPL")){
            OPL_Election OP= new OPL_Election(br);
            obj= (Election)OP;
        }
    }

    /** DO WE NEED THIS METHOD?
     * Gives the type of the election in a String format
     *
     * @return string containing the election type
     */
    private static String getElectionType(){
        return string1;
    }


}
