import java.awt.*;


public class Cell {

    public enum Type {
        MINE, EMPTY, FLAGGED, FLAGGED_MINE
    }

    public enum State {
        OPEN, CLOSED
    }

    protected int surroundingBombs = 0;

    protected int x, y;

    protected State state;

    protected Type type;

    protected static final Font FONT = new Font("serif", Font.PLAIN, 30);

    protected static final Color BACKGROUND = new Color(0x3333cc);

    protected static final Color OPENED = new Color(0x33cc33);

    private static final Color[] CELL_COLOURS = {
            new Color(0xffffff),
            new Color(0x0000FE), // 1
            new Color(0x186900), // 2
            new Color(0xAE0107), // 3
            new Color(0x000177), // 4
            new Color(0x8D0107), // 5
            new Color(0x007A7C), // 6
            new Color(0x902E90), // 7
            new Color(0x000000), // 8
    };

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.type = Type.EMPTY;
        this.state = State.CLOSED;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }


    public void draw(Graphics2D g) {
        final int s = 30;
        int centerx = 8;
        int centery = 4;
        g.setColor(BACKGROUND);
        g.fillRect(x * s, y * s, s, s);
        g.setFont(FONT);

        if (state == State.OPEN) {
            g.setColor(OPENED);
            g.fillRect(x * s, y * s, s, s);
            if (type == Type.MINE) {
                g.setColor(Color.BLACK);
                g.fillRect((x * s), (y * s) , s , s);

            } else if (surroundingBombs > 0) {
                g.setColor(CELL_COLOURS[surroundingBombs]);
                g.drawString(String.valueOf(surroundingBombs), (x * s) + centerx,
                        ((y * s) + s) - centery);
            }
        }
        if (type == Type.FLAGGED || type == Type.FLAGGED_MINE) {
            g.setColor(Color.RED);
            g.fillRect((x * s), (y * s), s, s);
        }

        g.setColor(Color.GRAY);
        g.drawRect(x * s, y * s, s, s);
    }
}
