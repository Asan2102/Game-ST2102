public interface GameConstants {
    public final int DEFAULT_WIDTH = 600;
    public final int DEFAULT_HEIGHT = 300;
    public final int DELAY = 8;
    public final int BASERADIUS=5;
    public final int LIFETIME=1300;
    public final int MAXRADIUS=25;
    public final int STARTQNTBALLS=10;
}
public class Ball implements GameConstants {

    private int inAction;
    private int x;
    private int y;
    private int dx;
    private  int dy;
    private  int radius;
    private  int dRadius;
    private Color color;
    private static int count;
    public final int id=count++;
    private static int score;
    private Timer gameTimer;
    private TimerTask gameTimerTask;


    Ball(int x, int y, int dx, int dy, int radius, Color color, int inAction, int dRadius){
        this.x=x;
        this.y=y;
        this.dx=dx;
        this.dy=dy;
        this.radius=radius;
        this.color=color;
        this.inAction=inAction;
        this.dRadius=dRadius;
        gameTimer = new Timer();
    }


    public Ellipse2D getShape(){
        return new Ellipse2D.Double(x-radius, y-radius, radius*2, radius*2);
    }


    public void moveBall(BallComponent ballComponent){
        x+=dx;
        y+=dy;
        radius+=dRadius;
        if(x<=0+radius){
            x=radius;
            dx=-dx;
        }
        if (x>=DEFAULT_WIDTH-radius){
            x=DEFAULT_WIDTH-radius;
            dx=-dx;
        }
        if(y<=0+radius){
            y=radius;
            dy=-dy;
        }
        if (y>=DEFAULT_HEIGHT-radius){
            y=DEFAULT_HEIGHT-radius;
            dy=-dy;
        }
        for(Ball ballVer: ballComponent.listBall){


            if(inAction==0)
                if((Math.sqrt(Math.pow(x-ballVer.x,2)+Math.pow(y-ballVer.y,2)))<=radius+ballVer.radius &&
                        id!=ballVer.id &&
                        (ballVer.inAction==1 || ballVer.inAction==2)) {
                    ballComponent.score++;
                    ballComponent.totalScore++;
                    dx=dy=0;
                    inAction=1;
                    ballComponent.setBackground(ballComponent.getBackground().brighter());
                }

            if(inAction==1){
                dRadius=1;
                if (radius>=MAXRADIUS){
                    inAction=2;
                    dRadius=0;
                    gameTimerTask = new gameTimerTask(this);
                    gameTimer.schedule(gameTimerTask, LIFETIME);
                }
            }

            if(inAction==2 && radius<=0){
                ballComponent.listBall.remove(this);
            }}}


    class gameTimerTask extends TimerTask{

        private Ball ballTimer;

        public gameTimerTask(Ball ball) {
            this.ballTimer = ball;
        }
        public void run() {
            ballTimer.dRadius=-1;
        }
    }
}

public class BallComponent extends JPanel implements GameConstants {
    List<Ball> listBall =  new CopyOnWriteArrayList<>();
    boolean startClick;
    public int score=0;
    public int totalScore=0;

    public void addBall(Ball b){
        listBall.add(b);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        for(Ball ball: listBall){
            g2d.setColor(ball.getColor());
            g2d.fill(ball.getShape());
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }}

public class BallGame implements GameConstants {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame ballFrame = new BallGameFrame();
                ballFrame.setVisible(true);
            }});
    }}

class BallGameFrame extends JFrame implements GameConstants{
    private int level=1; //Первый уровень
    private int ballQnt;
    private BallComponent ballComponent;
    private MousePlayer mousePlayerListener;

    //конструктор
    public BallGameFrame() {
        ballQnt=STARTQNTBALLS;
        setTitle("BallGame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ballComponent = new BallComponent();
        ballComponent.setBackground(Color.DARK_GRAY);
        mousePlayerListener = new MousePlayer();
        add(ballComponent, BorderLayout.CENTER);
        final JPanel buttonPanel = new JPanel();
        final JButton startButton = new JButton("Начать игру.");
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
        Runnable r = new BallRunnable(ballComponent, scoreLabel, level, ballQnt);
        Thread t = new Thread(r);
        t.start();
    }

    class MousePlayer extends MouseAdapter{
        public void mouseClicked(MouseEvent e) {
            Random random = new Random();

            Ball ball = new Ball(e.getX(),
                    e.getY(),
                    0,
                    0,
                    BASERADIUS,
                    new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)),
                    1,
                    1);
            ballComponent.startClick=true;
            ballComponent.addBall(ball);

            ballComponent.removeMouseListener(mousePlayerListener);
            ballComponent.removeMouseMotionListener(mousePlayerListener);
            ballComponent.setCursor(Cursor.getDefaultCursor());
        }}}