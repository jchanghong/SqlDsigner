/**
 *
 */
package mysqls.sql.mytest;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

/**
 * @author jiang
 *
 */
public class MBean {
	private String name;
	private VetoableChangeSupport vetoSupport = new VetoableChangeSupport(this);
	private PropertyChangeSupport ChangeSupport = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		ChangeSupport.addPropertyChangeListener(propertyChangeListener);
	}

	public void addVetoableChangeListener(VetoableChangeListener listener) {
		vetoSupport.addVetoableChangeListener(listener);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		ChangeSupport.removePropertyChangeListener(propertyChangeListener);
	}

	public void removeVetoableChangeListener(VetoableChangeListener listener) {
		vetoSupport.removeVetoableChangeListener(listener);
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		String old = this.name;
		this.name = name;
		ChangeSupport.firePropertyChange("name", old, name);
	}

}
