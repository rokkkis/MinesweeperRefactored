import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MouseInput extends MouseAdapter {

    protected Grid grid;

    private static final int OPEN_CELL_BUTTON = 1;

    private static final int MARK_MINE_BUTTON = 3;

    public MouseInput(Grid grid) {
        this.grid = grid;
    }

    public void mousePressed(MouseEvent e) {
        final int s = 30; // cell size
        final int x = e.getX() / s;
        final int y = e.getY() / s;
        final int button = e.getButton();


        if (x < 0 || y < 0 || x > grid.xCells || y > grid.yCells)
            return;

        try {
            if (button == OPEN_CELL_BUTTON)
                grid.openCell(x, y);

            if (button == MARK_MINE_BUTTON)
                grid.markMine(x, y);

            grid.checkGameOver();
        } catch (Exception ignored) {
        }
    }
}
