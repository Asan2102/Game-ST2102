import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class BallComponent extends JPanel implements GameConstants {
    List<Ball> listBall = new CopyOnWriteArrayList<>();
    boolean startClick;
    public int score = 0;
    public int totalScore = 0;

    public void addBall(Ball b) {
        listBall.add(b);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (Ball ball : listBall) {
            g2d.setColor(ball.getColor());
            g2d.fill(ball.getShape());
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(default_width, default_height);
    }
}
