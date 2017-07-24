package mysqls;

import mysqls.ui_frame.MainFrame;

import javax.swing.*;

/**
 * 启动类，主要功能不在这里。
 */
public final class UMLEditor {

    private UMLEditor() {
    }
    public static void main(String[] pArgs) {

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            setLookAndFeel();
            frame.setVisible(true);// 使界面可视。
            frame.setIcon();
        });
    }
    private static void setLookAndFeel()// 设置界面感官，美化界面。
    {
        try {
            System.setProperty("apple.laf.useScreenMenuBar", "true");// 设置指定键对值"apple.laf.useScreenMenuBar"的系统属性"true"
        } catch (SecurityException ex) {
            // well, we tried...
        }
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException
                | ClassNotFoundException e) {
            // Nothing: We revert to the default LAF
        }
    }

}