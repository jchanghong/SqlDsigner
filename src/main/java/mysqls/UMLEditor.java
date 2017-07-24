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
            frame.setVisible(true);// 使界面可视。
            frame.setIcon();
        });
    }
}