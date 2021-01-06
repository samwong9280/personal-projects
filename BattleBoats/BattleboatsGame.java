import java.util.Scanner;
//written by wong0613
public class BattleboatsGame
{
   public static void main(String[] args){
       //asks player for difficulty and starts game
       Scanner scan=new Scanner(System.in);
       System.out.println("Hello welcome to BattleBoats! Please enter 1 to play in standard mode or 2 to play in expert!");
       String input=scan.nextLine();
       while(!(input.equals("1")||input.equals("2"))){
          System.out.println("Invalid input, Please enter 1 to play in standard mode or 2 to play in expert.");          
          input=scan.nextLine(); 
        }
       BattleboatsBoard game=new BattleboatsBoard(Integer.parseInt(input));
       //asks for input each turn until game is over
       while(!game.checkWin()){
           System.out.println("Fire, Drone, or Missile?");
           game.display();
           
           String choice= scan.nextLine();
           switch(choice){
               case "Fire":
                System.out.println("Enter Coordinates: x,y");                
                String[] coords=scan.nextLine().split(",");
                game.fire(Integer.parseInt(coords[0]),Integer.parseInt(coords[1]));              
                break;
               case "Drone":
                game.drone();
                break;
               case "Missile":
                game.missile();
                break;
               case "display":
               //prints out fully revealed board rather than X's and 0's
                System.out.println(game);
                break;
               
            }
        }
       System.out.println("All boats sunk! The total number of turns is " + game.getTurns() + " but the total number of cannon shots is " + game.getShots());
       
    }
}
