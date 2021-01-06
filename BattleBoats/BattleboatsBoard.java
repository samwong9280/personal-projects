
//written by wong0613
import java.util.Scanner;
public class BattleboatsBoard
{
    private int[][] board;
    private Boat[] boats;
    private int size;
    private int totalShots;
    private int turns;
    private int drones;
    private int missiles;
    
    public static void main(String [] args){
        BattleboatsBoard test=new BattleboatsBoard(1);
        test.placeBoats();
        System.out.println(test);
        test.drone();
        test.missile();
        System.out.println(test);
        test.display();
    }
    
    public int getTurns(){
        return turns;
    }
    
    public int getShots(){
        return totalShots;
    }
        
    public BattleboatsBoard(int d){
        totalShots=0;
        turns=0;
        //sets game board size based on given difficulty
        if(d==1){
           board=new int[8][8];
           size=8;
           drones=1;
           missiles=1;
        }
        else if(d==2){
           board=new int[12][12];
           size=12;
           drones=2;
           missiles=2;
        }
        //sets appropriate number of boats based on difficulty
        if(d==1){
           boats= new Boat[]{new Boat(5),new Boat(4), new Boat(3), new Boat(3), new Boat(2)};
        }
        else if(d==2){
           boats= new Boat[]{new Boat(5), new Boat(5), new Boat(4), new Boat(4), new Boat(3), new Boat(3), new Boat(3), new Boat(3), new Boat(2), new Boat(2)};
        }
        placeBoats();
    }

    public void placeBoats(){
        //places all boats in boats array
        for(int i=0;i<boats.length;i++){            
            boolean placed=false;
            //tries to find a valid spot until the boat is placed
            while(!placed){
                //gets random coordinate on board
                int row=(int)(Math.random()*(size));
                int col=(int)(Math.random()*(size));
                //System.out.println(row +" " + col);
                if(isValidSpot(row,col,boats[i])){
                    //places boat in the correct orientation if the spot is valid
                    if(boats[i].getOrientation()=='v'){
                        for(int j=0;j<boats[i].getSize();j++){
                            board[row+j][col]=i+1;
                        }
                        //stores starting coordinates for placed boat
                        boats[i].setIndexRow(row);
                        boats[i].setIndexCol(col);
                        }
                    else{
                        for(int j=0;j<boats[i].getSize();j++){
                            board[row][col+j]=i+1;
                        }
                        boats[i].setIndexRow(row);
                        boats[i].setIndexCol(col);
                    }
                    placed=true;
                }
            }
        }
    }
    
    public boolean isValidSpot(int row, int col, Boat b){
        //if boat is horizontal checks if the given random coordinate will cause the boat to go out of bounds.
        if(b.getOrientation()=='h'){
            if(col+b.getSize()>board.length-1){
               return false;
            }
            //if boat will not go out of bounds checks to see if all potential spots are empty
            for(int i=0;i<b.getSize();i++){
                if(board[row][col+i]!=0){
                    return false;
                }
            }
        }   
        //if boat is vertical checks if the given random coordinate will cause the boat to go out of bounds.
        else{
            if(row+b.getSize()>board.length-1){
               return false;
            }
            //again, checks if all potential spots are empty
            for(int j=0;j<b.getSize();j++){
               if(board[row+j][col]!=0){
                   return false;
                }
            }
        }
      //if there's no conflicts then the spot is valid
        return true;
    }
    
    public void fire(int x, int y){
        //-1 indicates a hit,-2 indicates a miss
        int value=board[x][y];
        if(value>0){
            //establishes which boat was hit
            Boat hit=boats[board[x][y]-1];
            board[x][y]=-1;
            if(checkSunk(hit)){
                System.out.println("Sunk!");
                hit.setState(true);
                     
            }
            else{
                System.out.println("Hit!");
                
            }
            turns++;
            totalShots++;
        }
        //checks if shot missed
        else if(value==0){
            System.out.println("miss!");
            turns++;
            totalShots++;
            board[x][y]=-2;
        }
        //checks if shot is out of bounds
        else if(value<0||x>board.length||y<board.length){
            System.out.println("penalty!");
            turns+=2;
            totalShots++;
        }
        
    }
    
    public boolean checkSunk(Boat b){
       boolean sunk=true;  
       int row=b.getIndexRow();
       int col=b.getIndexCol();
       //checks to see if each part of the boat has been hit
        for(int i=0;i<b.getSize();i++){
           if(b.getOrientation()=='v'){
               if(board[row+i][col]!=-1){
                   sunk=false;
                }
            }
           else{
               if(board[row][col+i]!=-1){
                   sunk=false;
                }
            }
        } 
       b.setState(sunk);
       return sunk;
    }
    
    public void drone(){
        Scanner s=new Scanner(System.in);
        if(drones==0){
            System.out.println("No Drones Left");
        }
        else{
        //prompts for input until valid input is given
        System.out.println("Would you like to scan a row or column?");
        String choice=s.nextLine();
        while(!(choice.equals("row"))&&!(choice.equals("column"))){
            System.out.println("Invalid input. Please choose 'row' or 'column'");
            choice=s.nextLine();
        }
        //gets a row/col to scan from the player
        System.out.println("Which row/column would you like to scan?");
        int input=Integer.parseInt(s.nextLine());
        while(input<0||input>board.length-1){
            System.out.println("Invalid input. Please choose a valid row/column.");
            input=Integer.parseInt(s.nextLine());
        }
        //iterates through specified row/column and counts how many spaces contain boats.
        int boatsFound=0;
        switch(choice){
            case "row":
                for(int i=0;i<board.length;i++){
                    if(board[input][i]>0||board[input][i]==-1){
                        boatsFound++;
                    }
                }
                break;
            case "column":
                for(int j=0;j<board.length;j++){
                    if(board[j][input]>0||board[j][input]==-1){
                        boatsFound++;
                    }
                }
                break;
        }
        System.out.println("Drone has spotted " + boatsFound + " targets in the specified area!");
        turns++;
        drones--;
      }
    }
    
    public void missile(){
        if(missiles==0){
            System.out.println("No missiles left");
        }
        else{
        //gets row/col from player and stores the input
        System.out.println("Please enter a coordinate (x,y).");
        Scanner s=new Scanner(System.in);
        String input=s.nextLine();
        String[] data= input.split(",");
        int row=Integer.parseInt(data[0]);
        int col=Integer.parseInt(data[1]);
        //makes sure input is valid
        while(row>board.length||col>board.length||row<0||col<0){
            System.out.println("Invalid input");
            input=s.nextLine();
            data=input.split(" ");
            row=Integer.parseInt(data[0]);
            col=Integer.parseInt(data[1]);
        }
        //fires at a 3x3 square centered on given coordinate
        for(int i=-1;i<2;i++){
            for(int j=-1;j<2;j++){
                
                //only fires on coordinates in the board(for corner/edge spots)
                if(row+i>=0&&row+i<board.length&&col+j>=0&&col+j<board.length){
                  if(board[row+i][col+j]>0){
                      
                      Boat thisBoat=boats[board[row+i][col+j]-1];
                      board[row+i][col+j]=-1;
                      //checks if the missile sunk a boat
                      if(checkSunk(thisBoat)){
                          thisBoat.setState(true);
                          System.out.println("Sunk!");
                        }
                      
                    }
                  //checks which spaces missed
                  else if(board[row+i][col+j]==0){
                      board[row+i][col+j]=-2;
                    }
                }
            }
        }
        turns++;
        missiles--;
      }
    }
    
    public void display(){
        String display="";
        //shows the board from the players view
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board.length;j++){
                if(board[i][j]>=0){              
                    display += "- ";
                }
                else if(board[i][j]==-1){
                    display += "X ";
                }
                else if(board[i][j]==-2){
                    display += "0 ";
                }
            }
            display+="\n";
        }
        System.out.println(display);
    }
  
    public boolean checkWin(){
        for(int i=0;i<boats.length;i++){
           if(!boats[i].getState()){
               return false;
            }
        }
        return true;
    }
    
    public String toString(){
        //prints out completely revealed board
        String output="";
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board.length;j++){
                output+=board[i][j] + " ";
            }
            output+="\n";
        }
        return output;
    }
}
