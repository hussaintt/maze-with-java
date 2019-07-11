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

public class GBFS 
{   
    private Queue<Cell> q_Explored;
    private int cells_Co;
    private ArrayList<Cell> cells_list;
    private int index_st = 10;
    private int index_fin = 80;
    private double gbfs_time;
    private Cell current_Cell;
    private Cell EndCell;
    private int cellWidth;
    private Cell StCell;
    private int rndXStart=0;
    private int rndYStart=0;
    private int rndXEnd=50;
    private int rndYEnd=50;
    private int cols ;
    private int rows ;
    private double delay_bfs = .1f;
    private double delay_path = .1f; 
    private LayoutOnly layoutOnly;
    
    /*public void run()
    {
        solve();
    }*/
    public GBFS(ArrayList<Cell> cells_list, int start , int end, LayoutOnly layoutOnly)
    {
        this.layoutOnly = layoutOnly;
        this.cells_list = cells_list;
        this.cells_Co = cells_list.size();
        rows = (int)Math.sqrt(cells_list.size());
        cols = rows;
        index_st = start;
        index_fin = end;
        /*Thread t = new Thread(this, "asd");
        t.start();*/
        solve();
    }
    void solve(){
      
        cells_list.get(index_st).changeColor("yellow");
        cells_list.get(index_fin).changeColor("blue");
        
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
                                if (!q_Explored.contains(cells_list.get(ind-rows))&&!pq.contains(cells_list.get(ind-cols))){
                                pq.add(cells_list.get(ind-cols));
                                cells_list.get(ind-cols).setParent(current_Cell);}
                                break;
                            case 1:
                                if (!q_Explored.contains(cells_list.get(ind-1))&&!pq.contains(cells_list.get(ind-1))){
                                pq.add(cells_list.get(ind-1));
                                cells_list.get(ind-1).setParent(current_Cell);}
                                break;
                            case 2:
                                if (!q_Explored.contains(cells_list.get(ind+rows))&&!pq.contains(cells_list.get(ind+cols))){
                                pq.add(cells_list.get(ind+cols));
                                cells_list.get(ind+cols).setParent(current_Cell);}
                                break;
                            case 3:
                                if (!q_Explored.contains(cells_list.get(ind+1))&&!pq.contains(cells_list.get(ind+1))){
                                pq.add(cells_list.get(ind+1));
                                cells_list.get(ind+1).setParent(current_Cell);}
                                break;
               
                    }}}
                //evry cell visited make it false
                q_Explored.add(current_Cell);
                if(current_Cell.get_index_List() != index_st)         
                    current_Cell.changeColor("red");
                if(!pq.isEmpty()){
                    current_Cell = pq.remove();
                    
                    cells_Co--;
                    if(cells_Co <= 0)
                    {
                        timeline.stop();
                        System.out.println("stop");
                    }
                }       
               if (current_Cell == cells_list.get(index_fin)){
                    System.out.println("goalGbFS");
                    timeline.stop();
                    gbfs_time = System.currentTimeMillis() - start;
                    gbfs_time -= q_Explored.size() * layoutOnly.getSolvSpeed();
                    layoutOnly.setGBfs(Double.toString(gbfs_time), Double.toString(q_Explored.size()));
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
                    q_Explored.clear();
                }
            }
        }));
            
            timeline.play(); 
    }
public void getInitalStart(){
     rndXStart = (int)(Math.random() * (rows/4));
     rndYStart = (int)(Math.random() * (rows/4));

}
     public void getInitalEnd(){
         rndXEnd=(int)(0.75*rows)+(int)(Math.random() * (rows/4));
         rndYEnd=(int)(0.75*rows)+(int)(Math.random() * (rows/4));

     }
    public int getH(int x, int y){
    int H =Math.abs(x-cells_list.get(index_fin).getX())+Math.abs(y-cells_list.get(index_fin).getY());
    return H;
    }
public void Rnd(){
    getInitalStart();
    getInitalEnd(); 
    EndCell.setX(rndXEnd);
    EndCell.setX(rndYEnd);
    StCell.setX(rndXStart);
    StCell.setY(rndYStart);
    StCell.changeColor("green");
    EndCell.changeColor("blue");
}
void intials(){
    index_st = (int)(Math.random() * cells_list.size());
    index_fin = (int)(Math.random() * cells_list.size());
    cells_list.get(index_st).changeColor("yellow");
    cells_list.get(index_fin).changeColor("blue");
}     

PriorityQueue<Cell> pq = new PriorityQueue<Cell>(400, new Comparator<Cell>() {
    @Override
    public int compare(Cell n1, Cell n2) {
        // compare n1 and n2
    return  getH(n1.getX(),n1.getY())-getH(n2.getX(),n2.getY());
    
    }
});

}