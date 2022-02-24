import javax.swing.*;
import javax.swing.plaf.basic.DefaultMenuLayout;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

class BallRunnable implements Runnable, GameConstants{
    private BallComponent ballComponent;
    private JLabel scoreLabel;
    private int level, ballQnt;
    private MousePlayer mousePlayerListener;
    private int goal;

    public BallRunnable(final BallComponent ballComponent, JLabel scoreLabel, int level, int ballQnt) {

        this.ballComponent = ballComponent;
        this.scoreLabel = scoreLabel;
        this.level=level;
        this.ballQnt=ballQnt;
        this.goal=2;
    }

    class MousePlayer extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            Random random = new Random();
            Ball ball = new Ball(e.getX(),
                    e.getY(),
                    0,
                    0,
                    baseradius,
                    new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)),
                    1,
                    1);
            ballComponent.addBall(ball);
            ballComponent.startClick=true;
            ballComponent.removeMouseListener(mousePlayerListener);
            ballComponent.removeMouseMotionListener(mousePlayerListener);
            ballComponent.setCursor(Cursor.getDefaultCursor());
        }}
    public void run(){
        while(true){
            try{
                mousePlayerListener = new MousePlayer();
                ballComponent.addMouseListener(mousePlayerListener);
                ballComponent.addMouseMotionListener(mousePlayerListener);


                ballComponent.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));


                int countInWork=1;

                for (int i=0;i<ballQnt; i++){
                    Random randomX = new Random();
                    Random randomY = new Random();
                    Ball ball = new Ball(randomX.nextInt(default_width),
                            randomY.nextInt(default_height),
                            randomX.nextInt(2)+1,
                            randomY.nextInt(2)+1,
                            baseradius,
                            new Color(randomX.nextInt(255),randomX.nextInt(255),randomX.nextInt(255)),
                            0,
                            0);
                    ballComponent.addBall(ball);
                }

                while (countInWork!=0){
                    countInWork=0;
                    if(!ballComponent.startClick) {
                        EventQueue.invokeLater(new Runnable() {
                                                   public void run() {
                                                       // TODO Auto-generated method stub
                                                       scoreLabel.setText("Goal "+ goal+" balls out of "+ ballQnt);
                                                   }
                                               }
                        );
                        countInWork=1;
                    }
                    for(Ball ball: ballComponent.listBall){
                        if((ball.inAction()==1 || ball.inAction()==2)) countInWork++;
                        ball.moveBall(ballComponent);
                        ballComponent.repaint();
                        if(ballComponent.startClick){
                            EventQueue.invokeLater(new Runnable() {
                                public void run() {
                                    scoreLabel.setText("Level: "+ level+", you achieved "+ballComponent.score+" out of "+ballQnt);
                                }});
                        }}
                    Thread.sleep(DefaultMenuLayout);
                }
            } catch (InterruptedException ex){
                ex.printStackTrace();
            }
            ballComponent.listBall.clear();
            ballComponent.repaint();}}}
