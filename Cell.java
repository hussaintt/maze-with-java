package maze;



import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class Cell extends Pane implements java.io.Serializable
{
    //private GridPane layout;
    private Cell parent;
    private _Line wallRight;
    private _Line wallLeft;
    private _Line wallTop;
    private _Line wallbottom;
    private int  index_x;
    private int  index_y;
    private boolean visited;
    private _Line[] walls;
    private boolean[] wallExist = {true, true, true, true};
    //private Pane pane;
    private int indexList;
    public Cell( int x, int y, int w, int h, int lineWidth)
    {   changeColor("white"); 
        setup(x, y, w, h, lineWidth);   
    }

    public void set_index_list(int indexList)
    {
        this.indexList = indexList;
    }
    
    public int get_index_List()
    {
        return this.indexList;
    }

    public void setup( int x, int y, int w, int h, int lineWidth)
    {
    

        index_x = x;
        index_y = y;
        
        wallTop = new _Line (0f, 0f, 0f + w, 0f);
        wallLeft = new _Line (0f, 0f, 0f, 0f + h);
        wallbottom = new _Line (0f, 0f + h, 0f + w, 0f + h);
        wallRight = new _Line (0f + w , 0f, 0f + w, 0f + h);
        wallTop.setStroke(Color.BLACK);
        wallLeft.setStroke(Color.BLACK);
        wallbottom.setStroke(Color.BLACK);
        wallRight.setStroke(Color.BLACK);
        wallTop.setStrokeWidth(lineWidth);
        wallLeft.setStrokeWidth(lineWidth);
        wallbottom.setStrokeWidth(lineWidth);
        wallRight.setStrokeWidth(lineWidth);
        
        if(wall_exist(0))
         this.getChildren().add(wallTop);
        if(wall_exist(1))
          this.getChildren().add(wallLeft);
        if(wall_exist(2))
         this.getChildren().add(wallbottom);
        if(wall_exist(3))
         this.getChildren().add(wallRight);        
        //Layout.add(_pane, y, x);
        walls = new _Line[]{wallTop, wallLeft, wallbottom, wallRight};
    }

    public void setVisited(boolean visited)
    {
        this.visited = visited;
        if(visited)
        {
            changeColor("white");
        }
    }

    public int[] getindex()/////edit//////////
    {
        int ind [] = {index_x, index_y};
        return ind;
    }

    public Boolean isVisited()
    {
        return visited;
    }

    public void deleteLine(int index)
    {
        this.getChildren().remove(walls[index]);
        wallExist[index] = false;
    }

    boolean  wall_exist(int index)
    {
        return wallExist[index];
    }
    public void changeColor(String color)
    {
        this.setStyle("-fx-background-color: "+color+";");
    }
    public void setParent(Cell parent){
        this.parent = parent;
    }
    public Cell _getParent()
    {
        return this.parent;
    }

   public int getX(){return index_x;}
   public int getY(){return index_y;}
   public void setX(int index){index_x=index;}
   public void setY(int index){index_y=index;}

}