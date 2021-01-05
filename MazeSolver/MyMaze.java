// Names: Samuel Wong
// x500s: wong0613

import java.util.Random;

public class MyMaze{
    Cell[][] maze;

    public MyMaze(int rows, int cols) {
        maze= new Cell[rows][cols];
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                maze[i][j]=new Cell();
            }
        }
    }

    /* TODO: Create a new maze using the algorithm found in the writeup. */
    public static MyMaze makeMaze(int rows, int cols) {
        MyMaze thisMaze=new MyMaze(rows,cols);
        Stack1Gen<int[]> stack=new Stack1Gen();
        stack.push(new int[]{0,0});
        thisMaze.getMaze()[0][0].setVisited(true);
        while(!stack.isEmpty()){
            int[] current=stack.top();
            int[] neighbor=chooseRandom(current[0],current[1],thisMaze);
            if(neighbor==null){
                stack.pop();
            }
            else {
                //sets neighbor to visited
               Cell currentCell=thisMaze.getMaze()[current[0]][current[1]];
               Cell neighborCell= thisMaze.getMaze()[neighbor[0]][neighbor[1]];
               neighborCell.setVisited(true);
               stack.push(new int[]{neighbor[0],neighbor[1]});
                //gets direction from the neighboring cell
                int direction=neighbor[2];
                if (direction == 0) {
                    neighborCell.setBottom(false);
                }
                else if(direction==1) {
                    currentCell.setRight(false);
                }
                else if(direction==2) {
                    currentCell.setBottom(false);
                }
                else if(direction==3) {
                    neighborCell.setRight(false);
                }
            }
        }

        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                thisMaze.getMaze()[i][j].setVisited(false);
            }
        }
        //opens bottom right of the maze
        thisMaze.getMaze()[rows-1][cols-1].setRight(false);
        return thisMaze;
    }

    /* TODO: Print a representation of the maze to the terminal */
    public void printMaze(boolean path) {
        //print top boundary
        System.out.print("|");
        for(int i=0;i<maze[0].length;i++){
            System.out.print("---|");
        }
        System.out.println("");
        for(int i=0;i<maze.length;i++){
            //starts each row with it's left boundary except the entrance
            String middle="|";
            if (i == 0) {
                middle=" ";
            }
            String bottom="|";
            for(int j=0;j<maze[i].length;j++){
                //if the cell has a bottom
                if(maze[i][j].getBottom()){
                    bottom+="---|";
                }
                //open bottomed cell
                else{
                    bottom+="   |";
                }
                //cell with a right wall
                if(maze[i][j].getRight()){
                    if(path&&maze[i][j].getVisited()){
                        middle+=" * |";
                    }
                    else{
                        middle+="   |";
                    }
                }
                //open right wall
                else{
                    if(path&&maze[i][j].getVisited()){
                        middle+="  * ";
                    }
                    else {
                        middle += "    ";
                    }
                }
            }
            System.out.println(middle);
            System.out.println(bottom);
        }
    }

 
    public void solveMaze() {
        Q1Gen<int[]> queue=new Q1Gen<int[]>();
        queue.add(new int[]{0,0});
        while(!queue.isEmpty()){
            //dequeues front item, sets it as visited
            int[] current=queue.remove();
            maze[current[0]][current[1]].setVisited(true);
            //checks if maze is solved
            if(current[0]==maze.length-1&&current[1]==maze[0].length-1){
                break;
            }
            //gets all unvisited neighbors and enqueues them
            int[][] neighbors=getNeighbors(current[0],current[1]);
            for(int i=0;i<neighbors.length;i++){
                queue.add(neighbors[i]);
            }
        }
        printMaze(true);
    }

    //helper function to choose a random neighbor from a given cell
    public static int[] chooseRandom(int row, int col,MyMaze maze){
        Random rand=new Random();
        int num=rand.nextInt(4);
        Cell[][] maze1=maze.getMaze();
        //adds all valid neighbors to a list to be chosen from
        int[][] choices= new int[][]{{-1,-1},{-1,-1},{-1,-1},{-1,-1}};
        //up represented by direction num 0
        if((row-1)>=0&&!maze1[row-1][col].getVisited()){
            choices[0]=new int[]{row-1,col,0};
        }
        //down represented by 2
        if(row+1<maze1.length&&!maze1[row+1][col].getVisited()){
            choices[1]=new int[]{row+1,col,2};
        }
        //left represented by 3
        if(col-1>=0&&!maze1[row][col-1].getVisited()){
            choices[2]=new int[]{row,col-1,3};
        }
        //right represented by 1
        if(col+1<maze1[0].length&&!maze1[row][col+1].getVisited()){
            choices[3]=new int[]{row,col+1,1};
        }
        //checks if all neighbors have been visited
        int numAvailable=0;
        int numVisited=0;
        for(int i=0;i<choices.length;i++){
            if(choices[i][0]!=-1){
                numAvailable++;
                if(maze1[choices[i][0]][choices[i][1]].getVisited()){
                    numVisited++;
                }
            }
        }
        //returns null to signify there's no valid neighbors
        if(numAvailable==numVisited){
            return null;
        }
        int[] choice={-1,-1};
        //picks a random choice from possible neighbors until a non-empty spot is chosen(to account for cells on a edge)
        while(choice[0]==-1){
            choice=choices[rand.nextInt(4)];
        }
        return choice;
    }
    //returns 2d array containing all indexes of the given cell's unvisited neighbors
    public int[][] getNeighbors(int row, int col){
        int[][] neighbors=new int[4][];
        //adds neighbors in all directions to the array
        //up
        if((row-1)>=0&&!maze[row-1][col].getVisited()&&!maze[row-1][col].getBottom()){
            neighbors[0]=new int[]{row-1,col,0};
        }
        //down
        if(row+1<maze.length&&!maze[row+1][col].getVisited()&&!maze[row][col].getBottom()){
            neighbors[1]=new int[]{row+1,col,2};
        }
        //left
        if(col-1>=0&&!maze[row][col-1].getVisited()&&!maze[row][col-1].getRight()){
            neighbors[2]=new int[]{row,col-1,3};
        }
        //right
        if(col+1<maze[0].length&&!maze[row][col+1].getVisited()&&!maze[row][col].getRight()){
            neighbors[3]=new int[]{row,col+1,1};
        }
        //gets correct return array size by counting all non-null elements in neighbors array
        int size=0;
        for(int i=0;i<neighbors.length;i++){
            if(neighbors[i]!=null){
                size++;
            }
        }
        int index=0;
        int[][] allNeighbors= new int[size][];
        for(int i=0;i<neighbors.length;i++){
            //makes sure the element isn't null and it's not visited
            if(neighbors[i]!=null&&!(maze[neighbors[i][0]][neighbors[i][1]].getVisited())){
                allNeighbors[index]=neighbors[i];
                index++;
            }
        }
        return allNeighbors;
    }
        
    

    public Cell[][] getMaze(){
        return this.maze;
    }

    public static void main(String[] args){
        /* Any testing can be put in this main function */
        MyMaze test=new MyMaze(10,10).makeMaze(10,10);
        MyMaze test1=new MyMaze(3,3);
        test.printMaze(true);
        System.out.println();
        test.solveMaze();
    }
}