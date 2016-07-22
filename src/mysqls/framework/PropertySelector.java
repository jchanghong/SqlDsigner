
package mysqls.framework;

import java.beans.PropertyEditorSupport;

/**
 * A helper class for showing names of objects in a property sheet that allows
 * the user to pick one of a finite set of named values.
 */
public class PropertySelector extends PropertyEditorSupport {
	public String[] aNames;
	public Object[] aValues;

	/**
	 * Constructs a selector that correlates names and objects.
	 *
	 * @param pTags
	 *            the strings to display in a combo box
	 * @param pValues
	 *            the corresponding object values
	 */
	public PropertySelector(String[] pTags, Object[] pValues) {
		aNames = pTags;
		aValues = pValues;
	}

	@Override
	public String[] getTags() {
		return aNames;
	}

	@Override
	public String getAsText() {
		for (int i = 0; i < aValues.length; i++) {
			if (getValue().equals(aValues[i])) {
				return aNames[i];
			}
		}
		return null;
	}

	@Override
	public void setAsText(String pString) {
		for (int i = 0; i < aNames.length; i++) {
			if (pString.equals(aNames[i])) {
				setValue(aValues[i]);
			}
		}
	}

}
