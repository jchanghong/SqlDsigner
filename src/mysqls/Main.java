package mysqls;

import javax.swing.*;

public class Main extends JFrame {


    public Main() {

        setSize(400, 400);
//        setContentPane(new TESTPAINT());

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

    }

    public static void main(String[] args) {
        // Instantiate GUI on the EDT.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    String laf = UIManager.getSystemLookAndFeelClassName();
                    UIManager.setLookAndFeel(laf);
                } catch (Exception e) { /* Never happens */ }
                new Main().setVisible(true);
            }
        });
    }

}