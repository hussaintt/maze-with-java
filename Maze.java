package maze;


import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.*; 
import java.util.*; 
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Maze {
    boolean mazeFinished = false; 
    Double delay = .01;   
    Cell current_Cell;
    int cellWidth;
    int cellHight;
    int sceneHight = 400;
    int sceneWidth = 400;
    int cols;
    int rows;
    int lineWidth = 1; 
    int cells_Count;
    private Maze  maze = this;
    private Cell cells[][];
    ArrayList<Cell> cells_list = new ArrayList<Cell>();
    Stack st  = new Stack();
    LayoutOnly layoutOnly;
    GridPane mazePane;
    public Maze(GridPane layout, int cellWidth, LayoutOnly layoutOnly) { 
        mazePane = layout;
        this.layoutOnly = layoutOnly;
        delay = layoutOnly.getGenSpeed();
        setup(cellWidth);           
        for(int x = 0; x < rows; ++x)
        {   
            for(int y = 0; y < cols; ++y)
            {
                Cell cell = new Cell(x, y,  cellWidth,  cellWidth, lineWidth);
                layout.add(cell, y, x);
                cells[x][y] = cell;
                cells_list.add(cell);
                cell.set_index_list(cells_list.size() - 1);
            }
        }       
        buildMaze();
    }

    private int[] setup(int cellWidth)
    {   this.cellWidth = cellWidth;
        this.cellHight = cellWidth;
        cols = (int)(sceneHight / cellWidth);
        rows = (int)(sceneWidth / cellWidth);
        cells_Count = rows * cols;
        cells =  new Cell[rows][cols]; 
        return new int[]{rows, cols};

    }
    void buildMaze() 
    {
        
        int index = (int)(Math.random() * cells_Count);
        current_Cell = cells_list.get(index);
        current_Cell.setVisited(true);
       --cells_Count;
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(layoutOnly.getGenSpeed()), new EventHandler<ActionEvent>() {
            @Override
            
            public void handle(ActionEvent event) { 
                Cell chosenCell = getNeighbours(current_Cell);
                if(chosenCell != null)
                {
                    st.push(current_Cell);
                    removeWall(current_Cell, chosenCell);
                    chosenCell.setVisited(true);
                    current_Cell = chosenCell;
                    cells_Count--;
                   
                   if(cells_Count <= 0)
                    {
                        mazeFinished = true;
                        layoutOnly.deepCopy(Maze.this);
                        timeline.stop();
                        st.clear();
                        return;
                    }
                }
                else
                {
                    if(!st.isEmpty()){
                        Cell  current = (Cell)st.pop();
                        current_Cell = current;
                        
                    }
                    
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    public boolean isMazeFinsihed()
    {
        return mazeFinished;
    }
    void removeWall(Cell cur, Cell chosen)
    {
        if(cur.getindex()[1] == chosen.getindex()[1])
        {
            int x = chosen.getindex()[0] - cur.getindex()[0];
            if(x > 0)
            {
                /*System.out.print("Bottom ");
                System.out.println(" X1 = "+ cur.getindex()[0] + " y1 = "+ cur.getindex()[1] + " X2 = "+ 
                chosen.getindex()[0] + " y2 = "+ chosen.getindex()[1]);*/
                cur.deleteLine(2);
                chosen.deleteLine(0);
            }
            else
            {
                /*System.out.print("Top ");
                System.out.println(" X1 = "+ cur.getindex()[0] + " y1 = "+ cur.getindex()[1] + " X2 = "+ 
                chosen.getindex()[0] + " y2 = "+ chosen.getindex()[1]);*/
                cur.deleteLine(0);
                chosen.deleteLine(2);
            }
        }
        if(cur.getindex()[0] == chosen.getindex()[0])
        {
            int y = chosen.getindex()[1] - cur.getindex()[1];
            if(y > 0)
            {
                /*System.out.print("Right ");
                System.out.println(" X1 = "+ cur.getindex()[0] + " y1 = "+ cur.getindex()[1] + " X2 = "+ 
                chosen.getindex()[0] + " y2 = "+ chosen.getindex()[1]);*/
                cur.deleteLine(3);
                chosen.deleteLine(1);
            }
            else
            {
                /*System.out.print("Left ");
                System.out.println("X1 = "+ cur.getindex()[0] + " y1 = "+ cur.getindex()[1] + " X2 = "+ 
                chosen.getindex()[0] + " y2 = "+ chosen.getindex()[1]);*/
                cur.deleteLine(1);
                chosen.deleteLine(3);
            }
        }
    }
    
    Cell getNeighbours(Cell curCell)
    {
        ArrayList<Cell> neighbours = new ArrayList<Cell>();
        int x = curCell.getindex()[0];
        int y = curCell.getindex()[1];
        Cell chosenCell = null;
        if(x > 0)
        {
            Cell cell = cells[x - 1][y];
            if(!cell.isVisited())
            {
                neighbours.add(cell);
            }
        }
        if(y > 0)
        {
            Cell cell = cells[x][y - 1];
            if(!cell.isVisited())
            {
                neighbours.add(cell);
            }
        }
        if(x < rows - 1)
        {
            Cell cell = cells[x + 1][y];
            if(!cell.isVisited())
            {
                neighbours.add(cell);
            }
        }
        if(y < cols - 1)
        {
            Cell cell = cells[x][y + 1];
            if(!cell.isVisited())
            {
                neighbours.add(cell);
            }
        }
        if(neighbours.size() > 0){
            int index = (int)(Math.random() * neighbours.size());
            chosenCell = neighbours.get(index);
        }
        return chosenCell;
    }
    public ArrayList<Cell> getCell_list()
    {
        return cells_list;
    }
    
    
    
    
}
