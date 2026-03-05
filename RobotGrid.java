import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList; 
import java.util.List; 
import java.util.PriorityQueue; 
import java.util.Comparator; 
import javax.swing.ImageIcon; 
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame; 
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

public class RobotGrid extends JFrame { 
    private static int rows; 
    private static int cols; 
    private final int margin = 20; 
    private final int boxSize = 40; 
    JButton[] buttons;
    MyListener[] listeners;
    static ImageIcon trav;
    static ImageIcon rob;
    static ImageIcon brick;
    static ImageIcon road;


    static List<int[]> path;

    private void updateGrid(){
        for(int y=0;y<rows;y++){
            for(int x=0;x<cols;x++){
                if(Main.robotWorld[y][x]==0)
                    this.buttons[y*cols+x].setIcon(road);
                else
                    this.buttons[y*cols+x].setIcon(brick);
            }
        }
    }
    private void renderGrid(){
        for(int y=0;y<rows;y++){
            for(int x=0;x<cols;x++){
                //buttons[y*cols+x] = new JButton("("+(y+1)+","+(x+1)+")");
                if(Main.robotWorld[y][x]==0)
                    this.buttons[y*cols+x] = new JButton(new ImageIcon(getClass().getResource("road.png")));
                else
                    this.buttons[y*cols+x] = new JButton(new ImageIcon(getClass().getResource("brick.png")));
                this.listeners[y*cols+x] = new MyListener(y,x,this);
                this.listeners[y*cols+x].setParentButton(this.buttons[y*cols+x]);
                this.buttons[y*cols+x].addActionListener(this.listeners[y*cols+x]);
                this.buttons[y*cols+x].setBounds(this.margin+(x*this.boxSize), this.margin+50+(y*this.boxSize), this.boxSize, this.boxSize);
                this.add(this.buttons[y*cols+x]);
            }
        }
    }

    public void findAndPrintPath() {
        System.out.println("Entered function");
        path = findPath(Main.robotRow, Main.robotCol, Main.batteryRow, Main.batteryCol); 
        System.out.println("Path Calculated...printing");

        if (path != null) { 
            for (int[] p : path) { 
                Main.robotWorld[p[0]][p[1]] += 10; 
            }
            Main.printMatrix(); 
        } else { 
            //System.out.println("Path not found!!"); // Debugging output
            JOptionPane.showMessageDialog(RobotGrid.this, "Path Not Found!!!", "ERROR!!", JOptionPane.ERROR_MESSAGE);

        }
    }

    // A* pathfinding method
    public List<int[]> findPath(int startX, int startY, int endX, int endY) {
        int[][] robotWorld = Main.robotWorld; // Get the robot world grid
        int[][] cost = new int[robotWorld.length][robotWorld[0].length]; // Cost grid
        boolean[][] visited = new boolean[robotWorld.length][robotWorld[0].length]; // Visited cells grid

        // Initialize cost array to large values
        for (int i = 0; i < robotWorld.length; i++) {
            for (int j = 0; j < robotWorld[0].length; j++) {
                cost[i][j] = 1000;
            }
        }
        cost[startX][startY] = 0; 

        // Priority queue for A* algorithm
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[2])); 
        pq.add(new int[]{startX, startY, 0}); 
        
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; 
        int[][][] parent = new int[robotWorld.length][robotWorld[0].length][2]; 

        while (!pq.isEmpty()) { 
            int[] current = pq.poll(); 
            int x = current[0]; 
            int y = current[1]; 
            int g = current[2]; 

            if (visited[x][y]) continue;
            visited[x][y] = true; 

           
            if (x == endX && y == endY) { 
                List<int[]> path = new ArrayList<>(); 
                while (!(x == startX && y == startY)) { 
                    path.add(new int[]{x, y});
                    int[] p = parent[x][y]; 
                    x = p[0];
                    y = p[1]; 
                }
                path.add(new int[]{startX, startY}); 
                java.util.Collections.reverse(path);
                System.out.println("End position reached!");
                return path; 
            }

            for (int[] dir : directions) { 
                int newX = x + dir[0]; 
                int newY = y + dir[1];
                if (newX >= 0 && newX < robotWorld.length && newY >= 0 && newY < robotWorld[0].length &&
                    (robotWorld[newX][newY] == 0 || robotWorld[newX][newY] == 3)) { 
                    int newG = g + 1; 
                    int h = Math.abs(newX - endX) + Math.abs(newY - endY); // Calculate the heuristic 'h' value (Manhattan distance)
                    int f = newG + h; 
                    if (newG < cost[newX][newY]) { 
                        cost[newX][newY] = newG; 
                        pq.add(new int[]{newX, newY, f}); 
                        parent[newX][newY][0] = x;
                        parent[newX][newY][1] = y; 
                    }
                }
            }
        }

        System.out.println("No valid moves found, path not found!");
        return null;
    }

    public RobotGrid(int r, int c) { 
        rows = r;
        cols = c; 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.setTitle("Grid maker"); 
        this.setLayout(null); 
        this.setResizable(false);
        int n = rows * cols; 
        this.buttons = new JButton[n]; 
        this.listeners = new MyListener[n]; 
        trav = new ImageIcon(getClass().getResource("trav.png"));
        rob = new ImageIcon(getClass().getResource("robot.png"));
        brick = new ImageIcon(getClass().getResource("brick.png"));
        road = new ImageIcon(getClass().getResource("road.png"));
    
        Main.r = RobotGrid.this;

        JComboBox<String> selector = new JComboBox<String>();
        selector.addItem("Brick"); 
        selector.addItem("Robot");
        selector.addItem("Battery");
        selector.setBounds(20, 20, 150, 30);
        this.add(selector); 
        selector.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                Main.modeSelected = selector.getSelectedIndex() + 1; 
            }
        });

        JButton findPath = new JButton("Find path");
        findPath.setBounds(400, 20, 150, 30); 
        findPath.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                findAndPrintPath(); 
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int itr = 0;
                        int oldx = -1;
                        int oldy = -1;
                        while(itr<path.size()){
                            // TODO Auto-generated method stub
                            System.out.println("Move: "+path.get(itr)[0]+" "+path.get(itr)[1]);
                            if((oldx>=0)&&(oldy>=0)){
                                buttons[oldy * cols + oldx].setIcon(trav);
                            }
                            oldx = path.get(itr)[1];
                            oldy = path.get(itr)[0];
                            if((oldx>=0)&&(oldy>=0)){
                                buttons[oldy * cols + oldx].setIcon(rob);
                            }
                            itr++;
                            try {
                                Thread.sleep(400);
                            } catch (Exception e) {
                                // TODO: handle exception
                                e.printStackTrace();
                            }
                        }
                        System.out.println("Path traced");
                        JOptionPane.showMessageDialog(RobotGrid.this, "Destination reached", "Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                }).start();
                
            }
        });
        JButton loadGrid = new JButton("Load Grid");
        JButton saveGrid = new JButton("Save Grid");
        loadGrid.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView());
                int r = j.showOpenDialog(null);
                if (r == JFileChooser.APPROVE_OPTION) {
                    String fpath = j.getSelectedFile().getAbsolutePath();
                    try {
                        FileInputStream fileIn = new FileInputStream(fpath);
                        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                        try{
                            Main.RoboGrid = (GridWrap)objectIn.readObject();
                            ///copy Main.roboGrid to Main.robotworld
                            System.out.println("The data has been read from the file");
                            Main.copyGrid();
                            Main.printMatrix();
                            objectIn.close();
                            RobotGrid.this.updateGrid();
                        }catch(Exception ex){
                            System.out.println("Cannot initiate...exiting!");
                            ex.printStackTrace();
                            System.exit(0);
                        }
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        saveGrid.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView());
                int r = j.showSaveDialog(null);
                if (r == JFileChooser.APPROVE_OPTION) {
                    String fpath = j.getSelectedFile().getAbsolutePath();
                    try {
                        FileOutputStream fileOut = new FileOutputStream(fpath + ".grid");
                        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                        ///copy Robot.robotWorld to  Main.robogrid
                        Main.copyObj();
                        objectOut.writeObject(Main.RoboGrid);
                        objectOut.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        renderGrid();
        loadGrid.setBounds(180, 20, 100, 30);
        saveGrid.setBounds(290, 20, 100, 30);
        this.add(loadGrid);
        this.add(saveGrid);

        this.add(findPath); 
        this.setSize((2 * margin) + (cols * boxSize), (2 * margin) + (rows * boxSize) + 80); 
        this.setVisible(true); 
    }
}
