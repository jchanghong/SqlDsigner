package mysqls.ui_frame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 长宏 on 2016/10/1 0001.
 * 中间
 */
public class MainPanel extends JPanel{
    JPanel left=null;
    JPanel center=null;

    public MainPanel(JPanel left, JPanel center) {
        this.left = left;
        this.center = center;
        setBackground(Color.yellow);
        setOpaque(true);
        GridBagLayout gridBagLayout=new GridBagLayout();
        setLayout(gridBagLayout);


        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill=GridBagConstraints.BOTH;
        constraints.weighty=1;
        constraints.gridx=0;
        constraints.gridwidth=2;
//        constraints.weightx=0.5;
        add(left, constraints);


        constraints.gridx=GridBagConstraints.RELATIVE;
        constraints.gridwidth=GridBagConstraints.RELATIVE;
        constraints.gridwidth=4;
        constraints.weightx=1;
        add(center, constraints);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("dd");
        frame.setLayout(new BorderLayout());

        BootPanel bootPanel=BootPanel.getInstance();
        MainleftPanel mainleftPanel=MainleftPanel.getInstance();
        MainCenterPanel centerPanel=new MainCenterPanel();
        ToolPanel toolPanel = new ToolPanel(centerPanel);
        MainPanel mainPanel = new MainPanel(mainleftPanel, centerPanel);
        frame.add(bootPanel, BorderLayout.SOUTH);
        frame.add(toolPanel, BorderLayout.NORTH);
        frame.add(mainPanel);
        frame.pack();
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

}
