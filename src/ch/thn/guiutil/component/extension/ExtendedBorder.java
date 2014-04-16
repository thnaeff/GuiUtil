/**
 * 
 */
package ch.thn.guiutil.component.extension;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

/**
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class ExtendedBorder implements ExtensionBorder, Border {

	private Insets insets = new Insets(0, 0, 0, 0);
	
	private Border border = null;
	
	private CompoundBorder compoundBorder = null;
	
	private JComponent component = null;
	
	private int[] thickness = new int[BorderExtension.ORIENTATIONS_MAX + 1];
	
	
	
	/**
	 * 
	 * @param component
	 */
	public ExtendedBorder(JComponent component) {
		this.component = component;
	}
	
	/**
	 * 
	 * @return
	 */
	protected int[] getThickness() {
		return thickness;
	}
	
	/**
	 * 
	 * @param thickness
	 */
	protected void setThickness(int[] thickness) {
		this.thickness = thickness;
	}
	
	/**
	 * Sets the border to be drawn
	 * 
	 * @param border
	 */
	protected void showOuterBorder(boolean show) {
		
		if (!show) {
			compoundBorder = null;
			return;
		}
		
		Border outer = null;
		
		if (component != null && component.getParent() != null) {
			outer = BorderFactory.createMatteBorder(
					thickness[BorderExtension.NORTH], 
					thickness[BorderExtension.WEST], 
					thickness[BorderExtension.SOUTH], 
					thickness[BorderExtension.EAST], 
					component.getParent().getBackground());
		} else {
			outer = BorderFactory.createMatteBorder(
					thickness[BorderExtension.NORTH], 
					thickness[BorderExtension.WEST], 
					thickness[BorderExtension.SOUTH], 
					thickness[BorderExtension.EAST], 
					Color.green);
		}
		
		compoundBorder = new CompoundBorder(outer, border);
	}
	
	/**
	 * Sets the border to be drawn
	 * 
	 * @param border
	 */
	protected void setBorder(Border border) {
		this.border = border;
	}
	
	/**
	 * Sets the border insets
	 * 
	 * @param insets
	 */
	protected void setInsets(Insets insets) {
		this.insets = insets;
	}
	
	/**
	 * 
	 * @return
	 */
	protected Insets getInsets() {
		return insets;
	}
	
	/**
	 * 
	 * @return
	 */
	protected Insets getOriginalInsets() {
		if (compoundBorder != null) {
			return compoundBorder.getBorderInsets(component);
		} else {
			return border.getBorderInsets(component);
		}
	}
	
	@Override
	public Border getBorder() {
		return border;
	}
	
	/**
	 * Returns the border insets which have been set with {@link #setInsets(Insets)} 
	 * plus the original border insets.
	 * 
	 * @param c Not used
	 */
	@Override
	public Insets getBorderInsets(Component c) {
		Insets combinedInsets = null;
		
		if (compoundBorder != null) {
			combinedInsets = compoundBorder.getBorderInsets(c);
		} else {
			combinedInsets = border.getBorderInsets(c);
		}
		
		combinedInsets.top += insets.top;
		combinedInsets.right += insets.right;
		combinedInsets.bottom += insets.bottom;
		combinedInsets.left += insets.left;
		
		return combinedInsets;
	}

	@Override
	public boolean isBorderOpaque() {
		return false;
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width,
			int height) {
		
		if (compoundBorder != null) {
			compoundBorder.paintBorder(c, g, x, y, width, height);
		} else {
			border.paintBorder(c, g, x, y, width, height);
		}
		
	}

}
