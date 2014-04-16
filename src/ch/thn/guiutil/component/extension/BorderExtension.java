/**
 * 
 */
package ch.thn.guiutil.component.extension;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

/**
 * This class draws an additional border around the given component. The additional 
 * border has the thickness set with {@link #setThickness(int, int, int, int)} and 
 * it has the same color like the background component, making the border invisible.<br>
 * <br>
 * The following "picture" shows it on the example of a text field. This is about how a 
 * {@link JTextField} is set up (the visible border outer bounds with "-" and "|", and the 
 * actual invisible text field bounds with "="):
 * <pre>
 * |---Text field border---|
 * | ===================== |
 * | = Text field        = |
 * | ===================== |
 * |-----------------------|
 * </pre>
 * 
 * The {@link BorderExtension} then creates a custom {@link CompoundBorder} (wrapped 
 * in the class {@link ExtendedBorder}) with the current border as inner border 
 * and with an additional border as outer border. The outer border has the thickness 
 * of the values set with {@link #setThickness(int, int, int, int)}. The following 
 * shows how the result of a {@link BorderExtension} could look like:
 *  * <pre>
 * |-------Outer border----------|
 * |                             |
 * |                             |
 * |   |---Text field border---| |
 * |   | ===================== | |
 * |   | = Text field        = | |
 * |   | ===================== | |
 * |   |-----------------------| |
 * |-----------------------------|
 * </pre>
 * Since the outer border color is always set to the same color of the parent 
 * component, the outer border is invisible. This gives a padding around the text 
 * field which could even be used for drawing (as used for {@link BorderImage}).<br>
 * <br>
 * ====== USAGE ======<br>
 * <br>
 * To use the {@link BorderExtension} class the following steps are needed:<br>
 * <ul>
 * 	<li>In your class, create an instance of {@link BorderExtension}</li>
 *	<li>Use the {@link #setThickness(int, int, int, int)} to define the thickness 
 *of the border around the component.</li>
 * </ul>
 * 
 * 
 * 
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class BorderExtension implements AncestorListener, PropertyChangeListener {
	
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	protected static final int ORIENTATIONS_MAX = 3;
	
	
	private JComponent extendedComponent = null;
	
	private ExtendedBorder extendedBorder = null;
	
	private int[] thickness = new int[BorderExtension.ORIENTATIONS_MAX + 1];
	
	/**
	 * 
	 * @param component
	 */
	public BorderExtension(JComponent component) {
		this.extendedComponent = component;
		
		extendedBorder = new ExtendedBorder(component);
		
		extendedComponent.addAncestorListener(this);
		extendedComponent.addPropertyChangeListener(this);
		
		updateBorder();
	}
	
	/**
	 * 
	 * 
	 * @param top
	 * @param right
	 * @param bottom
	 * @param left
	 */
	public void setThickness(int top, int right, int bottom, int left) {
		thickness[BorderExtension.NORTH] = top;
		thickness[BorderExtension.EAST] = right;
		thickness[BorderExtension.SOUTH] = bottom;
		thickness[BorderExtension.WEST] = left;
		
		updateBorder();
	}
	
	/**
	 * Returns the actual border without modifications
	 * 
	 * @return
	 */
	public Border getBorder() {
		Border b = extendedComponent.getBorder();
		if (b instanceof ExtensionBorder) {
			return ((ExtensionBorder)b).getBorder();
		} else {
			return b;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	protected ExtendedBorder getExtendedBorder() {
		return extendedBorder;
	}
	
	/**
	 * 
	 */
	private void updateBorder() {
		Border b = extendedComponent.getBorder();
		
		if (b != null && b instanceof ExtendedBorder) {
			//An extended border has already been set from somewhere else. Make sure 
			//it has all the values we need and use it from now on.
			
			ExtendedBorder existing = (ExtendedBorder)b;
			existing.showOuterBorder(true);
			existing.setThickness(thickness);
			
			extendedBorder = existing;
		} else {
			//Extended border is not set yet
			extendedBorder.setBorder(b);
			extendedBorder.setThickness(thickness);
			extendedBorder.showOuterBorder(true);
			extendedComponent.setBorder(extendedBorder);
		}
		
		
	}
	
	
	
	@Override
	public void ancestorAdded(AncestorEvent event) {		
		updateBorder();
	}

	@Override
	public void ancestorMoved(AncestorEvent event) {
	}

	@Override
	public void ancestorRemoved(AncestorEvent event) {
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		if (evt.getPropertyName().equals("border")) {
			updateBorder();
		}
		
	}
	

}
