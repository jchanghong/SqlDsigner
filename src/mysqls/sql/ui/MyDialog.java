/**
 *
 */
package mysqls.sql.ui;

import javax.swing.*;
import java.awt.*;

/**
 * @author jiang 自定义对话框。内容自己添加
 */
public class MyDialog extends JDialog {
    /**
     * tile 标题
     */
    public MyDialog(String title) {
        // TODO Auto-generated constructor stub
        super();
        setVisible(false);
        setModal(true);// 模式对话框
        setTitle(title);
        setBounds(300, 300, 300, 600);

    }

    public void show(JPanel jPanel) {

        add(jPanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

}
