import javax.swing.*;
import java.awt.*;

public class BallGame implements GameConstants {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame ballFrame = new BallGameFrame();
                ballFrame.setVisible(true);
            }});
    }}
