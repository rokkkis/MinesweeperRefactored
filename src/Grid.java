import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;


public class Grid {


    protected Cell[][] cells;


    protected int xCells = 9, yCells = 9;

    protected int numMines = 10;

    public Grid(int x, int y, int mines) {
        this.xCells = x;
        this.yCells = y;
        this.numMines = mines;
        setup();
    }

    private void setup() {
        this.cells = new Cell[xCells][yCells];
        for (int i = 0; i < xCells; i++)
            for (int j = 0; j < yCells; j++)
                cells[i][j] = new Cell(i, j);
        setupMines();
        calculateMines();
    }

    private void setupMines() {
        if (numMines > (xCells * yCells)) {
            JOptionPane.showMessageDialog(null, "You cant have more mines than you have cells.");
            System.exit(-1);
        }

        int placedMines = 0;
        while (placedMines < numMines) {
            int x = (int) (Math.random() * xCells);
            int y = (int) (Math.random() * yCells);

            if (cells[x][y].getType() != Cell.Type.MINE) {
                cells[x][y].setType(Cell.Type.MINE);
                placedMines++;
            }
        }
    }

    public void calculateMines() {
        for (int i = 0; i < xCells; i++)
            for (int j = 0; j < yCells; j++)
                cells[i][j].surroundingBombs = surroundingMines(cells[i][j]);
    }


    public void markMine(int x, int y) {
        final Cell cell = cells[x][y];
        final Cell.Type type = cell.getType();

        if (cell.getState() == Cell.State.OPEN)
            return;

        if (type == Cell.Type.FLAGGED)
            cell.setType(Cell.Type.EMPTY);
        else if (type == Cell.Type.FLAGGED_MINE)
            cell.setType(Cell.Type.MINE);


        else if (type == Cell.Type.MINE)
            cell.setType(Cell.Type.FLAGGED_MINE);
        else
            cell.setType(Cell.Type.FLAGGED);
    }

    public void openCell(int x, int y) {
        final Cell cell = cells[x][y];


        if (cell.getState() == Cell.State.OPEN) {
            forceOpenCells(x, y);
        }


        if (cell.getType() == Cell.Type.FLAGGED ||
                cell.getType() == Cell.Type.FLAGGED_MINE)
            return;

        cell.setState(Cell.State.OPEN);
        if (cell.getType() == Cell.Type.MINE) {
            revealMines();
            JOptionPane.showMessageDialog(null, "You opened a mine. Good game.");
            System.exit(1);
            return;
        }

        if (cell.surroundingBombs > 0) {
            return;
        }

        List<Cell> surroundingCells = getSurroundingCells(cell);
        for (Cell c : surroundingCells) {
            if (c.getState() == Cell.State.CLOSED)
                openCell(c.x, c.y);
        }
    }


    private void revealMines() {
        for (int x = 0; x < xCells; x++)
            for (int y = 0; y < yCells; y++) {
                final Cell.Type type = cells[x][y].getType();
                if (type == Cell.Type.MINE || type == Cell.Type.FLAGGED_MINE)
                    cells[x][y].setState(Cell.State.OPEN);
            }
    }

    public void forceOpenCells(int x, int y) {
        final Cell cell = cells[x][y];

        List<Cell> surroundingCells = getSurroundingCells(cell);

        for (Cell c : surroundingCells) {
            if (c.getState() == Cell.State.CLOSED)
                openCell(c.x, c.y);
        }
    }

    public void checkGameOver() {
        int flaggedCount = countFlags(true);
        if (flaggedCount == numMines) {
            JOptionPane.showMessageDialog(null, "Well done. You win.");
            setup();
        }
    }

    private int countFlags(boolean minesOnly) {
        int flaggedCount = 0;
        for (int x = 0; x < xCells; x++)
            for (int y = 0; y < yCells; y++) {
                final Cell.Type type = cells[x][y].getType();
                if (!minesOnly && type == Cell.Type.FLAGGED)
                    flaggedCount++;
                if (type == Cell.Type.FLAGGED_MINE)
                    flaggedCount++;
            }
        return flaggedCount;
    }

    private List<Cell> getSurroundingCells(Cell cell) {
        List<Cell> surrounding = new LinkedList<Cell>();
        final int x = cell.x;
        final int y = cell.y;

        if (cell.x > 0) {
            surrounding.add(cells[x - 1][y]);
            if (cell.y > 0)
                surrounding.add(cells[x - 1][y - 1]);
            if (cell.y < yCells - 1)
                surrounding.add(cells[x - 1][y + 1]);
        }
        if (cell.x < xCells - 1) {
            surrounding.add(cells[x + 1][y]);
            if (cell.y < yCells - 1)
                surrounding.add(cells[x + 1][y + 1]);
            if (cell.y > 0)
                surrounding.add(cells[x + 1][y - 1]);
        }
        if (cell.y > 0)
            surrounding.add(cells[x][y - 1]);
        if (cell.y < yCells - 1)
            surrounding.add(cells[x][y + 1]);

        return surrounding;
    }

    private int surroundingMines(Cell cell) {
        List<Cell> surrounding = getSurroundingCells(cell);

        int mineCount = 0;
        for (Cell c : surrounding)
            if (c.getType() == Cell.Type.MINE)
                mineCount++;
        cell.surroundingBombs = mineCount;

        return mineCount;
    }


    public void draw(Graphics2D g) {
        for (int x = 0; x < xCells; x++)
            for (int y = 0; y < yCells; y++)
                cells[x][y].draw(g);
    }
}
