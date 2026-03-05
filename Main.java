
public class Main {
    public static int modeSelected = 1;
    public static int robotPlaced = 0; 
    public static int batteryPlaced = 0; 
    public static int[][] robotWorld; 
    public static GridWrap RoboGrid; 
    public static RobotGrid r; 


    public static int robotRow, robotCol, batteryRow, batteryCol; 
    public static int rows;
    public static int cols;

    public static void copyGrid(){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(RoboGrid.robotWorld[i][j]<=1){
                    robotWorld[i][j] = RoboGrid.robotWorld[i][j];
                }
                else{
                    robotWorld[i][j] = 0;
                }
            }
        }
    }

    public static void copyObj(){
        RoboGrid.robotWorld = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(robotWorld[i][j]<=1){
                    RoboGrid.robotWorld[i][j] = robotWorld[i][j];
                }
                else{
                    RoboGrid.robotWorld[i][j] = 0;
                }
            }
        }
    }
 
    public static void main(String[] args) {
        rows = 15;
        cols = 15;
        
        robotWorld = new int[rows][cols];
        Main.RoboGrid = new GridWrap();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                robotWorld[i][j] =0;
            }
        }
        new RobotGrid(rows, cols);
        printMatrix();
    }

    public static void printMatrix() {
        System.out.println(" * * * * * * * * ");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(robotWorld[i][j] + " ");
            }
            System.out.println(" ");
        }
        System.out.println(" * * * * * * * * ");
    }
}