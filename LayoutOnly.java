package maze;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.*; 

public class LayoutOnly extends Application {
    private boolean initiated = false;
    private Maze maze;
    private BFS bfs;
    private GBFS gbfs;
    private int cell_size = 20;
    private double generationSpeed = 0.005;
    private double solveSpeed = .01;
    private TextField cellSize; 
    private TextField genSp; 
    private TextField solveSp; 
    private DeepCopy deepCopy;
    private ArrayList<Cell> maze_copy;
    private Label TimeGpfs = new Label("0");
    private Label SpaceGpfs = new Label("0");
    private Label TimeBfs = new Label("0");
    private Label SpaceBfs = new Label("0");
   
    
    //private Double generationSpeed = 0.005;
    private int index_st;
    private int index_fin;
    private  GridPane mazePane_1;
    private GridPane mazePane_2;
    @Override
    public void start(Stage primaryStage) {
       
       GridPane paneRight =new GridPane();
       paneRight.setAlignment(Pos.BASELINE_RIGHT);
       paneRight.setPadding(new Insets(15.5,15.5,13.5, 5));
       paneRight.setVgap(25);
       //labels for the right side
       Label edit = new Label("Editor");
       Label genSpL = new Label("generation speed");
       Label cellSizeL = new Label("Cell Size");
       Label solveSpL = new Label("solve speed");
       Label split = new Label("==============");
       
       Label Time = new Label("Time");
       Label Space = new Label("Space");
       Label Gpfs = new Label("GPFS");
       Label Bfs = new Label("BFS");

       
      
       
       
       

       //text fields for the right side
        cellSize =new TextField();
        genSp =new TextField();
        solveSp =new TextField();
        //adding to pane
       paneRight.add(edit, 0, 0);
       paneRight.add(split,0,1);
       paneRight.add(cellSizeL, 0, 2);
       paneRight.add(cellSize, 0, 3);
       paneRight.add(genSpL, 0, 4);
       paneRight.add(genSp, 0, 5);
       paneRight.add(solveSpL, 0, 6);
       paneRight.add(solveSp, 0, 7);
       //replace this with your maze
        mazePane_1 = new GridPane();
        mazePane_2 = new GridPane();
    //Pane down 2

       GridPane paneDown2 = new GridPane();
       paneDown2.setAlignment(Pos.BOTTOM_RIGHT);
       paneDown2.setPadding(new Insets(15.5,15.5,13.5, 5));
       paneDown2.setHgap(20);
       paneDown2.setVgap(20);

        paneDown2.add(Time, 1, 1);
        paneDown2.add(Space, 1, 2);
        paneDown2.add(Gpfs, 2, 0);
        paneDown2.add(Bfs, 3, 0);
        
        paneDown2.add(TimeGpfs, 2, 1);
        paneDown2.add(SpaceGpfs, 2, 2);
        
        paneDown2.add(TimeBfs, 3, 1);
        paneDown2.add(SpaceBfs, 3, 2);  

//configration
       GridPane paneDown =new GridPane();
       paneDown.setAlignment(Pos.BOTTOM_LEFT);
       paneDown.setPadding(new Insets(15.5,15.5,13.5,14.5));
       paneDown.setHgap(20);
       
    
//initialize buttons
        Button startMaze = new Button("Start Maze");
        //Button doubleMaze = new Button("Double Maze");
        Button startBfs = new Button("Start BFS");
        Button startGpfs = new Button("Start GBFS");
        Button end = new Button("Exit");


        paneDown.add(startMaze, 1, 0);
        paneDown.add(startBfs, 2, 0);
        paneDown.add(startGpfs, 3, 0);
        //paneDown.add(doubleMaze, 4, 0);
       


        //editing layout like css
        Font font1 = new Font("SansSerif",30);
        Font font2 = new Font("SansSerif",20);
        split.setFont(font2);
        edit.setFont(font1);
        cellSizeL.setFont(font2);
        genSpL.setFont(font2);
        solveSpL.setFont(font2);
        cellSize.setFont(font2);
        genSp.setFont(font2);
        solveSp.setFont(font2);
        startMaze.setFont(font2);
        startBfs.setFont(font2);
        startGpfs.setFont(font2);
       //doubleMaze.setFont(font2);
        Time.setFont(font2);
        TimeGpfs.setFont(font2);
        Space.setFont(font2);
        SpaceGpfs.setFont(font2);
        SpaceBfs.setFont(font2);
        TimeBfs.setFont(font2);
        Bfs.setFont(font2);       
        Gpfs.setFont(font2);
////////////////

       
                //all actions is here
         startMaze.setOnAction(new EventHandler<ActionEvent>() {
              @Override
            public void handle(ActionEvent event) {
                String val = cellSize.getText();
                boolean valid = isInt(cellSize, val);
                if(valid){
                    cell_size = Integer.parseInt(val);
                }
                mazePane_1.getChildren().clear();
                mazePane_2.getChildren().clear();
                initiated = false;
                maze = new Maze(mazePane_1, cell_size, LayoutOnly.this);
               
            }
        });

          startBfs.setOnAction(new EventHandler<ActionEvent>() {
              @Override
            public void handle(ActionEvent event) {

                if(maze != null && maze.isMazeFinsihed())
                {
                    if(!initiated)
                        intials();
                    bfs = new BFS(maze.getCell_list(), index_st, index_fin, LayoutOnly.this);
                }
            }
        });

        startGpfs.setOnAction(new EventHandler<ActionEvent>() {
              @Override
            public void handle(ActionEvent event) {
                if(maze != null && maze.isMazeFinsihed())
                {
                    if(!initiated)
                        intials();
                    gbfs = new GBFS(maze_copy, index_st, index_fin, LayoutOnly.this);
                }
            }
        });

            end.setOnAction(new EventHandler<ActionEvent>() {
              @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!4");
            }
        });



        //adding panes to borderpane
        BorderPane totalDown =new BorderPane();
        totalDown.setLeft(paneDown);
        totalDown.setRight(paneDown2);
        //adding panes to borderpane
        BorderPane total =new BorderPane();
        GridPane maze = new GridPane();
        maze.add(mazePane_1, 0, 0);
        maze.add(mazePane_2 , 1, 0);
        total.setRight(paneRight);
        total.setCenter(maze);
        total.setBottom(totalDown);

        Scene scene = new Scene(total, 1500, 650);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setBfs(String time, String space)
    {
        TimeBfs.setText(time);
        SpaceBfs.setText(space);
    }
    public void setGBfs(String time, String space)
    {
        TimeGpfs.setText(time);
        SpaceGpfs.setText(space);
    }
    
    public void  deepCopy(Maze _maze)
    {
        if(mazePane_2.getChildren().size() <= 0){
            maze_copy = new ArrayList<Cell>();
            for(int i = 0; i < _maze.getCell_list().size(); ++i)
            {
                deepCopy = new DeepCopy();
                maze_copy.add((Cell)UnoptimizedDeepCopy.copy(_maze.getCell_list().get(i)));
                maze_copy.get(i).setup(maze_copy.get(i).getX(), maze_copy.get(i).getY(), cell_size, cell_size, 1);
                mazePane_2.add(maze_copy.get(i), maze_copy.get(i).getY(), maze_copy.get(i).getX());
            }
        }    
    }
    public double getGenSpeed()
    {
        String val = genSp.getText();
        boolean valid = isInt(genSp, val);
        if(valid)
        {
            generationSpeed = Double.parseDouble(val);
            System.out.println(generationSpeed);
        }
        return generationSpeed;
    } 

    public double getSolvSpeed()
    {
        String val = solveSp.getText();
        boolean valid = isInt(solveSp, val);
        if(valid)
            solveSpeed = Double.parseDouble(val);
        return solveSpeed;
    }

    boolean isInt(TextField textF, String value)
    {   
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;    
        }
    }
    void intials(){
        initiated = true;
        index_st = (int)(Math.random() * maze.getCell_list().size());
        index_fin = (int)(Math.random() * maze.getCell_list().size());
        int diff = java.lang.Math.abs(index_st - index_fin);
        while(diff <= (int)((maze.getCell_list().size()) * 0.75)){
            index_st = (int)(Math.random() * maze.getCell_list().size());
            index_fin = (int)(Math.random() * maze.getCell_list().size());
            diff = java.lang.Math.abs(index_st - index_fin);
            //System.out.println("in"+diff);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
