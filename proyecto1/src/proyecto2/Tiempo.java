package proyecto2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;

class Tiempo extends JLabel implements Runnable {

    public int tiempo = 0;

    public Tiempo() {
        setPreferredSize(new Dimension(760, 30));
        setText("0:00");
        setFont(new Font("Ubuntu", Font.BOLD, 30));
        setForeground(Color.BLACK);
        setVisible(true);
        setAlignmentX(JLabel.HEIGHT);
    }

    @Override
    public void run() {
        setForeground(Color.BLACK);
        try {
            int minutes = 0;
            while (true) {
                clase2 obj = new clase2();
                tiempo++;
                if (tiempo >= 60) {
                    tiempo = 0;
                    minutes++;
                    obj.tiempoclae2++;
                }
                int seconds = tiempo;
                setText(String.format("%d:%02d", minutes, seconds));

                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
