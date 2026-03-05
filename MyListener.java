import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.*;

class MyListener implements ActionListener {
    private JFrame root; 
    private JButton parentButton; 
    private ImageIcon brick; 
    private ImageIcon road; 
    private ImageIcon robot; 
    private ImageIcon battery;
    private Boolean clicked = false; 
    private Boolean robotIn = false;
    private Boolean batteryIn = false; 
    private int row;
    private int col; 

    public MyListener(int row, int col, JFrame root) {
        this.root = root; 
        this.row = row; 
        this.col = col; 
        this.brick = new ImageIcon(getClass().getResource("brick.png")); 
        this.road = new ImageIcon(getClass().getResource("road.png"));
        this.robot = new ImageIcon(getClass().getResource("robot.png"));
        this.battery = new ImageIcon(getClass().getResource("battery.png")); 
    }

    public void setParentButton(JButton pb) {
        this.parentButton = pb;
    }

    public void actionPerformed(ActionEvent e) {
        if (!clicked) { 
            if (Main.modeSelected == 1) {
                this.parentButton.setIcon(this.brick);
                clicked = true;
                Main.robotWorld[row][col] = 1; 
            } else if (Main.modeSelected == 2) {
                if (Main.robotPlaced < 1) {
                    this.parentButton.setIcon(this.robot);
                    clicked = true; 
                    robotIn = true;
                    Main.robotPlaced = 1;
                    Main.robotWorld[row][col] = 2;
                    Main.robotRow = row; 
                    Main.robotCol = col;
                } else {
                    JOptionPane.showMessageDialog(parentButton, "You cannot add more robots!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (Main.modeSelected == 3) { 
                if (Main.batteryPlaced < 1) { 
                    this.parentButton.setIcon(this.battery); 
                    clicked = true;
                    batteryIn = true; 
                    Main.batteryPlaced = 1; 
                    Main.robotWorld[row][col] = 3;
                    Main.batteryRow = row;
                    Main.batteryCol = col; 

                } else {
                   JOptionPane.showMessageDialog(parentButton, "You cannot add more batteries!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else { 
            this.parentButton.setIcon(this.road); 
            clicked = false; 
            Main.robotWorld[row][col] = 0;
            if (robotIn) {
                robotIn = false; 
                Main.robotPlaced = 0; 
            }
            if (batteryIn) {
                batteryIn = false;
                Main.batteryPlaced = 0;
            }
        }
        Main.printMatrix(); 
    }
}
