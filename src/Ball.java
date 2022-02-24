import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Timer;
import java.util.TimerTask;

public class Ball implements GameConstants {

    private int inAction;
    private int x;
    private int y;
    private int dx;
    private int dy;
    private int radius;
    private int dRadius;
    private Color color;
    private static int count;
    public final int id = count++;
    private static int score;
    private Timer gameTimer;
    private TimerTask gameTimerTask;


    Ball(int x, int y, int dx, int dy, int radius, Color color, int inAction, int dRadius) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.radius = radius;
        this.color = color;
        this.inAction = inAction;
        this.dRadius = dRadius;
        gameTimer = new Timer();
    }

    public Ellipse2D getShape() {
        return new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2);
    }

    public void moveBall(BallComponent ballComponent) {
        x += dx;
        y += dy;
        radius += dRadius;
        if (x <= 0 + radius) {
            x = radius;
            dx = -dx;
        }
        if (x >= default_width - radius) {
            x = default_width - radius;
            dx = -dx;
        }
        if (y <= 0 + radius) {
            y = radius;
            dy = -dy;
        }
        if (y >= default_height - radius) {
            y = default_height - radius;
            dy = -dy;
        }
        for (Ball ballVer : ballComponent.listBall) {

            if (inAction == 0)
                if ((Math.sqrt(Math.pow(x - ballVer.x, 2) + Math.pow(y - ballVer.y, 2))) <= radius + ballVer.radius &&
                        id != ballVer.id &&
                        (ballVer.inAction == 1 || ballVer.inAction == 2)) {
                    ballComponent.score++;
                    ballComponent.totalScore++;
                    dx = dy = 0;
                    inAction = 1;
                    ballComponent.setBackground(ballComponent.getBackground().brighter());
                }

            if (inAction == 1) {
                dRadius = 1;
                if (radius >= maxradius) {
                    inAction = 2;
                    dRadius = 0;
                    gameTimerTask = new gameTimerTask(this);
                    gameTimer.schedule(gameTimerTask, lifetime);
                }
            }
            if (inAction == 2 && radius <= 0) {
                ballComponent.listBall.remove(this);
            }
        }
    }
    class gameTimerTask extends TimerTask{

        private Ball ballTimer;

        public gameTimerTask(Ball ball) {
            // TODO Auto-generated constructor stub
            this.ballTimer = ball;
        }
        public void run() {
            // TODO Auto-generated method stub
            ballTimer.dRadius=-1;
        }
    }}
