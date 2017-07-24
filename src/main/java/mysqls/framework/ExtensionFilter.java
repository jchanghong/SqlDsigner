package mysqls.framework;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * A file filter that accepts all files with a given set of extensions.
 */
public class ExtensionFilter extends FileFilter {
    private String aDescription;
    private String aExtension;

    /**
     * Constructs an extension file filter.
     *
     * @param pDescription the description (e.g. "Woozle files")
     * @param pExtension   the single accepted extension that corresponds to this type of
     *                     file.
     * @pre pDescription != null
     * @pre pExtensions != null
     * @pre pExtensions != ""
     */
    public ExtensionFilter(String pDescription, String pExtension) {
        assert pDescription != null;
        assert pExtension != null;
        assert pExtension.length() > 0;
        aDescription = pDescription;
        aExtension = pExtension;
    }

    @Override
    public boolean accept(File pFile) {
        if (pFile.isDirectory()) {
            return true;
        }

        String fileName = pFile.getName().toLowerCase();
        return fileName.endsWith(aExtension.toLowerCase());
    }

    @Override
    public String getDescription() {
        return aDescription;
    }

    /**
     * @return The extension for this filter.
     */
    public String getExtension() {
        return aExtension;
    }
}
