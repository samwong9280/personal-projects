//written by wong0613
public class Boat
{
    private int size;
    private char orientation;
    private int indexRow;
    private int indexCol;
    private boolean isSunk;
    public static void main(String [] args){
        Boat test1=new Boat(2);
        Boat test2=new Boat(2);
        Boat test3=new Boat(2);
    }
    
    public Boat(int s)
    {
       size= s;
       isSunk=false;
       //randomly chooses between vertical and horizontal orientation
       double orient=Math.floor(Math.random()*2);
       if(orient==0){
           orientation='v';
        }
       else{
           orientation='h';
        }
     }
    //mutator methods
    public int getSize(){
        return size;
    }
    
    public char getOrientation(){
        return orientation;
    }
    
    public int getIndexRow(){
        return indexRow;
    }
    
    public int getIndexCol(){
        return indexCol;
    }
    
    public void setIndexRow(int i){
        indexRow=i;
    }
    
    public void setIndexCol(int j){
        indexCol=j;
    }
    
    public void setState(boolean b){
        isSunk=b;
    }
    
    public boolean getState(){
        return isSunk;
    }
   
  
}
