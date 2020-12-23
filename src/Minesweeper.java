import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Minesweeper {

    protected JFrame frame;

    public Minesweeper() {
        frame = new JFrame("Minesweeper");
        frame.setSize(400, 300);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                    System.exit(1);
            }
        });
        int width = 10;
        int height = 10;
        int mines = 5;
        settings(width, height, mines);
        frame.setVisible(true);
    }

    public void settings(int width, int height, int mines) {
        final int s = 30; // cell size
        int centerx = 25;
        int centery = 40;
        frame.setSize(width * s + centerx, height * s + centery);
        frame.setContentPane(new GridInterface(width, height, mines));
    }

    public static void main(String[] args) {
        new Minesweeper();
    }
}
