import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

class BallGameFrame extends JFrame implements GameConstants{
    private int level=1;
    private int ballQnt;
    private BallComponent ballComponent;
    private MousePlayer mousePlayerListener;


    public BallGameFrame() {
        ballQnt=numberofballs;
        setTitle("BallGame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ballComponent = new BallComponent();
        ballComponent.setBackground(Color.DARK_GRAY);
        mousePlayerListener = new MousePlayer();
        add(ballComponent, BorderLayout.CENTER);
        final JPanel buttonPanel = new JPanel();
        final JButton startButton = new JButton("Start game.");
        buttonPanel.add(startButton);
        final JLabel scoreLabel = new JLabel();
        buttonPanel.add(scoreLabel);
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ballComponent.addMouseListener(mousePlayerListener);
                ballComponent.addMouseMotionListener(mousePlayerListener);
                startButton.setVisible(false);
                ballComponent.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                startGame(scoreLabel, ballQnt);
            }});
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
    }
    public void startGame(JLabel scoreLabel, int ballQnt){
        Runnable r = new BallRunnable (ballComponent, scoreLabel, level, ballQnt);
        Thread t = new Thread(r);
        t.start();
    }
    class MousePlayer extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            Random random = new Random();

            Ball ball = new Ball(e.getX(),
                    e.getY(),
                    0,
                    0,
                    baseradius,
                    new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)),
                    1,
                    1);
            ballComponent.startClick=true;
            ballComponent.addBall(ball);
            ballComponent.removeMouseListener(mousePlayerListener);
            ballComponent.removeMouseMotionListener(mousePlayerListener);
            ballComponent.setCursor(Cursor.getDefaultCursor());
        }}}
