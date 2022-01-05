import java.io.*;
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files


public class Main {



    private static Scanner myReader;
    private static String string1;
    private static BufferedReader br=null;
    private static Election obj;
    private static ArrayList<BufferedReader> br111=new ArrayList<BufferedReader>();
    private static ArrayList<String> fileNames=new ArrayList<String>();
    private static int count=0;

    /**
     * Needed For testing in order to call on main function.
     *
     */
    static void mainCaller(String[] args){

        count++;
        if(count<2){
            main(args);
        }
    }

    /**
     * Needed For testing in order to retireve election object and to verify results.
     *
     * @return Election Object obj
     */
    public Election getObj(){
        return obj;
    }


    public static void main(String[] args) {
        // Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        if(args.length<1){
            System.out.println("Error, Wrong number of inputs: ");
            return;
        }
        String fileName;
        fileName=args[0];
        for(int i=0;i<(args.length);i++){
            fileNames.add(args[i]);
        }

        //Use a forloop to store all the file names.
        //Use another for loop to run through each file to create a Buffreader object.
       /* System.out.println("Please enter filename");  // Output user input
        fileName = myObj.nextLine();  // Read user input
        */

        try{
            for(int j=0;j<fileNames.toArray().length;j++){
                br = new BufferedReader(new FileReader(fileNames.get(j)));
                br111.add(br);
            }

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
            IR_Election IRE= new IR_Election(br,br111);
            obj=(Election)IRE;
        }
        else if(local.equals("OPL")){
            OPL_Election OP= new OPL_Election(br,br111);
            obj= (Election)OP;
        }
        else if(local.equals("PO")){
            PO_Election PO = new PO_Election(br,br111);
            obj = (Election)PO;
        }
    }

    /**
     * Gives the type of the election in a String format
     *
     * @return string containing the election type
     */
    private static String getElectionType(){
        return string1;
    }


}
