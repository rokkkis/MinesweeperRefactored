import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GridInterface extends JPanel {

    protected Grid grid;

    public GridInterface(int width, int height, int mines) {
        grid = new Grid(width, height, mines);
        addMouseListener(new MouseInput(grid));

        new Timer(5, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;

        grid.draw(g2);
    }
}
