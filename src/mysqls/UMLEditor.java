package mysqls;

import mysqls.ui_frame.MainFrame;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import java.util.ResourceBundle;

/**
 * 启动类，主要功能不在这里。
 */
public final class UMLEditor {
    private static final int JAVA_MAJOR_VERSION = 7;
    private static final int JAVA_MINOR_VERSION = 0;

    private UMLEditor() {
    }

    /**
     * @param pArgs Each argument is a file to open upon launch. Can be empty.
     */
    public static void main(String[] pArgs) {
        UMLEditor.checkVersion();// 版本环境检查。
        try {
            System.setProperty("apple.laf.useScreenMenuBar", "true");// 设置指定键对值"apple.laf.useScreenMenuBar"的系统属性"true"
        } catch (SecurityException ex) {
            // well, we tried...
        }
        final String[] arguments = pArgs;

        SwingUtilities.invokeLater(new Runnable()// 为了避免线程锁死问题，建议你使用invokeLater在事件分发线程中为所有新应用程序创建GUI
        {
            @Override
            public void run() {
                UMLEditor.setLookAndFeel();// 设置界面感官。
                MainFrame frame = new MainFrame(UMLEditor.class);
//				frame.addGraphType("class_diagram", ClassDiagramGraph.class);
                frame.setVisible(true);// 使界面可视。
//				frame.readArgs(arguments);// 读取命令。
//				frame.addWelcomeTab();
                frame.setIcon();
            }
        });
    }

    private static void setLookAndFeel()// 设置界面感官，美化界面。
    {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
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

    /**
     * Checks if the current VM has at least the given version, and exits the
     * program with an error dialog if not.
     */
    private static void checkVersion()// 检查环境。
    {
        String version = UMLEditor.obtainJavaVersion();
        if (version == null || !UMLEditor.isOKJVMVersion(version)) {
            ResourceBundle resources = ResourceBundle.getBundle("uestc.uml.sql.framework.EditorStrings");// 就是读取资源属性文件（properties），然后根据.properties文件的名称信息（本地化信息），匹配当前系统的国别语言信息（也可以程序指定），然后获取相应的properties文件的内容。
            String minor = "";
            int minorVersion = UMLEditor.JAVA_MINOR_VERSION;
            if (minorVersion > 0) {
                minor = "." + UMLEditor.JAVA_MINOR_VERSION;
            }
            JOptionPane.showMessageDialog(null,
                    resources.getString("error.version") + "1." + UMLEditor.JAVA_MAJOR_VERSION + minor);
            System.exit(1);
        }
    }

    static String obtainJavaVersion() {
        String version = System.getProperty("java.version");// 获得系统属性（java运行时版本环境）。
        if (version == null) {
            version = System.getProperty("java.runtime.version");
        }
        return version;
    }

    /**
     * @return True is the JVM version is higher than the versions specified as
     * constants.
     */
    static boolean isOKJVMVersion(String pVersion) // 检测是否符合JVM环境。
    {
        assert pVersion != null;
        String[] components = pVersion.split("\\.|_");// 将字符串pVsion以.\_分割为子字符串，然后将结果作为字符串数组返回。
        boolean lReturn = true;

        try {
            int systemMajor = Integer.parseInt(String.valueOf(components[1]));
            int systemMinor = Integer.parseInt(String.valueOf(components[2]));
            if (systemMajor > UMLEditor.JAVA_MAJOR_VERSION) {
                lReturn = true;
            } else if (systemMajor < UMLEditor.JAVA_MAJOR_VERSION) {
                lReturn = false;
            } else // major Equals
            {
                if (systemMinor > UMLEditor.JAVA_MINOR_VERSION) {
                    lReturn = true;
                } else lReturn = systemMinor >= UMLEditor.JAVA_MINOR_VERSION;
            }
        } catch (NumberFormatException e) {
            lReturn = false;
        }
        return lReturn;
    }
}