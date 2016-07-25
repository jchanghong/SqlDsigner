package mysqls;

import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import mysqls.diagrams.ClassDiagramGraph;
import mysqls.framework.EditorFrame;

/**
 * 启动类
 */
public final class UMLEditor {
	private static final int JAVA_MAJOR_VERSION = 7;
	private static final int JAVA_MINOR_VERSION = 0;

	private UMLEditor() {
	}

	/**
	 * @param pArgs
	 *            Each argument is a file to open upon launch. Can be empty.
	 */
	public static void main(String[] pArgs) {
		UMLEditor.checkVersion();
		try {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		} catch (SecurityException ex) {
			// well, we tried...
		}
		final String[] arguments = pArgs;

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				UMLEditor.setLookAndFeel();
				EditorFrame frame = new EditorFrame(UMLEditor.class);
				frame.addGraphType("class_diagram", ClassDiagramGraph.class);
				frame.setVisible(true);
				frame.readArgs(arguments);
				frame.addWelcomeTab();
				frame.setIcon();
			}
		});
	}

	private static void setLookAndFeel() {
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
	private static void checkVersion() {
		String version = UMLEditor.obtainJavaVersion();
		if (version == null || !UMLEditor.isOKJVMVersion(version)) {
			ResourceBundle resources = ResourceBundle.getBundle("uestc.uml.sql.framework.EditorStrings");
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
		String version = System.getProperty("java.version");
		if (version == null) {
			version = System.getProperty("java.runtime.version");
		}
		return version;
	}

	/**
	 * @return True is the JVM version is higher than the versions specified as
	 *         constants.
	 */
	static boolean isOKJVMVersion(String pVersion) {
		assert pVersion != null;
		String[] components = pVersion.split("\\.|_");
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
				} else if (systemMinor < UMLEditor.JAVA_MINOR_VERSION) {
					lReturn = false;
				} else // minor equals
				{
					lReturn = true;
				}
			}
		} catch (NumberFormatException e) {
			lReturn = false;
		}
		return lReturn;
	}
}