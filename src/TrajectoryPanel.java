import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class TrajectoryPanel extends JPanel {
    private Mision mision;

    public TrajectoryPanel(Mision mision) {
        this.mision = mision;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (mision != null) {
            g.setColor(Color.BLUE);
            Random rand = new Random();
            int x = 50;
            int y = getHeight() / 2;
            for (int i = 0; i < 10; i++) {
                int nextX = x + 50;
                int nextY = y + rand.nextInt(100) - 50;
                g.drawLine(x, y, nextX, nextY);
                x = nextX;
                y = nextY;
            }
        }
    }
}

