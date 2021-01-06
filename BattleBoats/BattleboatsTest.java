public class BattleboatsTest {
    public BattleboatsTest() {
    }
    //fires on every possible spot on the board, until game is over in both standard and expert mode
    public static void main(String [] args) {
        BattleboatsBoard test = new BattleboatsBoard(1);
        System.out.println("Playing in standard mode");
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                System.out.println("firing on "+ i+","+j);
                test.fire(i,j);
                System.out.println("fully revealed board");
                System.out.println(test);
                System.out.println("players view");
                test.display();
                if(test.checkWin()) {
                    break;
                }
            }
            if(test.checkWin()){
                System.out.println("All boats sunk! The total number of turns is " + test.getTurns() + " but the total number of cannon shots is " + test.getShots());
                break;
            }
        }
         test = new BattleboatsBoard(2);
        System.out.println("Playing in expert mode");
        for(int i=0;i<12;i++){
            for(int j=0;j<12;j++){
                System.out.println("firing on"+ i+","+j);
                test.fire(i,j);
                System.out.println("fully revealed board");
                System.out.println(test);
                System.out.println("players view");
                test.display();
                if(test.checkWin()){
                    break;
                }
            }
            if(test.checkWin()) {
                System.out.println("All boats sunk! The total number of turns is " + test.getTurns() + " but the total number of cannon shots is " + test.getShots());
                break;
            }
        }

    }
}
