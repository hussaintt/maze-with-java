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

public class BFS //implements Runnable
{   
    private Queue<Cell> q_Explored;
    private Queue<Cell> q_Front;    
    private int cells_Co;
    private ArrayList<Cell> cells_list;
    private int index_st;
    private int index_fin;
    private Cell current_Cell;
    private int cellWidth;
    private int cols ;
    private int rows ;
    private double delay_bfs = .01f;
    private double delay_path = .1f; 
    private Thread t;
    private LayoutOnly layoutOnly;
    private double bfs_time;
    /*public void run()
    {
        solve();
    }*/
    public BFS(ArrayList<Cell> cells_list, int start, int end, LayoutOnly layoutOnly)
    {
        this.layoutOnly = layoutOnly;
        this.cells_list = cells_list;
        this.cells_Co = cells_list.size();
        rows = (int)Math.sqrt(cells_list.size());
        cols = rows;
        index_st=start;
        index_fin=end;
        /*t = new Thread(this, "as");
        t.start();*/
        solve();
        
    }
    void solve(){

        
    
        cells_list.get(index_st).changeColor("yellow");
        cells_list.get(index_fin).changeColor("blue");

        
        q_Front= new LinkedList<Cell>();
        q_Explored = new LinkedList<Cell>();
        current_Cell =  cells_list.get(index_st);
        if (current_Cell == cells_list.get(index_fin)){
            System.out.println("goal");
        }
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        long start = System.currentTimeMillis();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(layoutOnly.getSolvSpeed()), new EventHandler<ActionEvent>() {
            
            int ind = index_st;
            @Override
            public void handle(ActionEvent event) { 
                int i=0;
                for(i=0;i<4;i++){
                if(!current_Cell.wall_exist(i)){
                    switch (i){
                            case 0:
                                if (!q_Explored.contains(cells_list.get(ind-rows))&&!q_Front.contains(cells_list.get(ind-cols))){
                                q_Front.add(cells_list.get(ind-cols));
                                cells_list.get(ind-cols).setParent(current_Cell);}
                                break;
                            case 1:
                                if (!q_Explored.contains(cells_list.get(ind-1))&&!q_Front.contains(cells_list.get(ind-1))){
                                q_Front.add(cells_list.get(ind-1));
                                cells_list.get(ind-1).setParent(current_Cell);}
                                break;
                            case 2:
                                if (!q_Explored.contains(cells_list.get(ind+rows))&&!q_Front.contains(cells_list.get(ind+cols))){
                                q_Front.add(cells_list.get(ind+cols));
                                cells_list.get(ind+cols).setParent(current_Cell);}
                                break;
                            case 3:
                                if (!q_Explored.contains(cells_list.get(ind+1))&&!q_Front.contains(cells_list.get(ind+1))){
                                q_Front.add(cells_list.get(ind+1));
                                cells_list.get(ind+1).setParent(current_Cell);}
                                break;
               
                    }}}
                //evry cell visited make it false
                q_Explored.add(current_Cell);
                if(current_Cell.get_index_List() != index_st)         
                    current_Cell.changeColor("red");
                if(!q_Front.isEmpty()){
                    current_Cell = q_Front.remove();//out from queue
                    cells_Co--;
                    if(cells_Co <= 0)
                    {
                        timeline.stop();
                        System.out.println("stop");
                    }
                }       
               if (current_Cell == cells_list.get(index_fin)){
                    bfs_time = System.currentTimeMillis() - start;
                    bfs_time -= q_Explored.size() * layoutOnly.getSolvSpeed();
                    layoutOnly.setBfs(Double.toString(bfs_time), Double.toString(q_Explored.size()));
                    System.out.println("goalBFS");
                    q_Front = null;
                    timeline.stop();
                    path();
                }
            ind = current_Cell.get_index_List();
            }
        }));
            
            timeline.play(); 
        
    }
    void path(){
        current_Cell =  cells_list.get(index_fin);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(layoutOnly.getSolvSpeed()), new EventHandler<ActionEvent>() {
            
            int ind = index_fin;
            @Override
            public void handle(ActionEvent event) { 
                int i=0;
                for(i=0;i<4;i++){
                if(!current_Cell.wall_exist(i)){
                    switch (i){
                            case 0:
                                if (q_Explored.contains(cells_list.get(ind-cols))&& current_Cell._getParent() ==  cells_list.get(ind-cols)){
                                current_Cell.changeColor("green");
                                ind = ind-cols;
                                }
                                break;
                            case 1:
                                if (q_Explored.contains(cells_list.get(ind-1))&& current_Cell._getParent() ==  cells_list.get(ind-1)){
                                current_Cell.changeColor("green");
                                ind = ind-1;
                                }
                                break;
                            case 2:
                                if (q_Explored.contains(cells_list.get(ind+cols))&& current_Cell._getParent() ==  cells_list.get(ind+cols)){
                                current_Cell.changeColor("green");
                                ind = ind+cols;
                                }
                                break;
                            case 3:
                                if (q_Explored.contains(cells_list.get(ind+1))&& current_Cell._getParent() ==  cells_list.get(ind+1)){
                                current_Cell.changeColor("green");
                                ind = ind+1;
                                }
                                break;
               
                    }}}
                //evry cell visited make it false
                q_Explored.remove(current_Cell);
                current_Cell = cells_list.get(ind);
                       
               if (current_Cell == cells_list.get(index_st)){
                    System.out.println("found path");
                    timeline.stop();       
                    cells_list.get(index_st).changeColor("yellow");
                    cells_list.get(index_fin).changeColor("blue");
                    q_Explored = null;
                }
            }
        }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play(); 
    }
}