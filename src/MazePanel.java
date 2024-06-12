import javax.swing.*;
import java.awt.*;

class MazePanel extends JPanel {
    private char[][] labirinto;
    private final int CELL_SIZE = 30;

    public MazePanel(char[][] labirinto) {
        this.labirinto = labirinto;
    }

    public void setLabirinto(char[][] labirinto) {
        this.labirinto = labirinto;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < labirinto.length; i++) {
            for (int j = 0; j < labirinto[i].length; j++) {
                if (labirinto[i][j] == 'X') {
                    g.setColor(Color.BLACK);
                } else if (labirinto[i][j] == 'S') {
                    g.setColor(Color.GREEN);
                } else if (labirinto[i][j] == 'E') {
                    g.setColor(Color.RED);
                } else if (labirinto[i][j] == '*') {
                    g.setColor(Color.YELLOW);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }
}
